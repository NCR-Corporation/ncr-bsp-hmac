import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SendPost{
    private static final String HMAC_SHA512 = "HmacSHA512";

     /**
     * @param sharedKey  A user's Shared Key
     * @param secretKey A user's Secret Key
     * @param date An unformated date string
     * @param httpMethod GET/POST/PUT
     * @param requestUrl The API url requesting against
     * @param contentType Optional
     * @param nepApplicationKey Optional
     * @param nepCorrelationId Optional
     * @param nepOrganization A user's organization
     * @param nepServiceVersion Optional
     * @return sharedKey:hmac
     * @throws NoSuchAlgorithmException
     * @throws MalformedURLException
     * @throws InvalidKeyException
     */
    public static String generateHmac(
        String sharedKey, 
        String secretKey, 
        String date, 
        String httpMethod, 
        String requestUrl, 
        String contentType, 
        String nepApplicationKey, 
        String nepCorrelationId,
        String nepOrganization,
        String nepServiceVersion) throws NoSuchAlgorithmException, MalformedURLException, InvalidKeyException 
    {
        URL url = new URL(requestUrl);

        String path = url.getPath();

        if(url.getQuery() != null && !url.getQuery().isEmpty()){
            path += "?" + url.getQuery();
        }

        String isoDate = date + ".000Z";

        String oneTimeSecret = secretKey + isoDate;
        String toSign = httpMethod + "\n" + path;
        
        if(contentType != null && !contentType.isEmpty()){
            toSign += "\n" + contentType;
        }
        if(nepOrganization != null && !nepOrganization.isEmpty()){
            toSign += "\n" + nepOrganization;
        }
        if(nepApplicationKey != null && !nepApplicationKey.isEmpty()){
            toSign += "\n" + nepApplicationKey;
        }
        if(nepCorrelationId != null && !nepCorrelationId.isEmpty()){
            toSign += "\n" + nepCorrelationId;
        }
        if(nepServiceVersion != null && !nepServiceVersion.isEmpty()){
            toSign += "\n" + nepServiceVersion;
        }
        Mac mac = Mac.getInstance(HMAC_SHA512);
        SecretKey keySpec = new SecretKeySpec(oneTimeSecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA512);
        mac.init(keySpec);
        byte[] rawHmac = mac.doFinal(toSign.getBytes(StandardCharsets.UTF_8));
        
        return sharedKey + ":" + Base64.getEncoder().encodeToString(rawHmac);
    }

    /**
     * The method to build the POST request
     * @param secretKey  A user's Secret Key
     * @param sharedKey A user's Shared Key
     * @param nepOrganization A user's organization
     * @throws NoSuchAlgorithmException
     * @throws MalformedURLException
     * @throws IOException
     * @throws ProtocolException
     * @throws InvalidKeyException
     */
    public static void callPost(String secretKey, String sharedKey, String nepOrganization) throws NoSuchAlgorithmException, MalformedURLException, IOException, ProtocolException, InvalidKeyException{
        String url = "https://api.ncr.com/security/authorization";
        String httpMethod = "POST";
        String contentType = "application/json";
        String dataJSON = "{\"sort\":[{\"column\":\"siteName\",\"direction\":\"asc\"}]}";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();

        String hmacAccesskey = generateHmac(sharedKey, secretKey, format.format(date), httpMethod, url, contentType, "", "", nepOrganization, "");

        URL encodedUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) encodedUrl.openConnection();

        format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String gmtDate = format.format(date);

        connection.setRequestMethod(httpMethod);
        connection.setRequestProperty("Date", gmtDate + " GMT");
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Authorization", "AccessKey " + hmacAccesskey);
        connection.setRequestProperty("nep-organization", nepOrganization);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        try(OutputStream os = connection.getOutputStream()){
            byte[] input = dataJSON.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int status = connection.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();

        while((inputLine = in.readLine()) != null){
            content.append(inputLine);
        }

        System.out.println("{'status': " + status + " },\n{'data': " + prettyPrintJSON(content.toString()));

        in.close();
        connection.disconnect();
    }

    
        /**
     * A simple implementation to pretty-print JSON file.
     *
     * @param unformattedJsonString
     * @return
     */
    public static String prettyPrintJSON(String unformattedJsonString) {
        StringBuilder prettyJSONBuilder = new StringBuilder();
        int indentLevel = 0;
        boolean inQuote = false;
        for(char charFromUnformattedJson : unformattedJsonString.toCharArray()) {
        switch(charFromUnformattedJson) {
            case '"':
            // switch the quoting status
            inQuote = !inQuote;
            prettyJSONBuilder.append(charFromUnformattedJson);
            break;
            case ' ':
            // For space: ignore the space if it is not being quoted.
            if(inQuote) {
                prettyJSONBuilder.append(charFromUnformattedJson);
            }
            break;
            case '{':
            case '[':
            // Starting a new block: increase the indent level
            prettyJSONBuilder.append(charFromUnformattedJson);
            indentLevel++;
            appendIndentedNewLine(indentLevel, prettyJSONBuilder);
            break;
            case '}':
            case ']':
            // Ending a new block; decrese the indent level
            indentLevel--;
            appendIndentedNewLine(indentLevel, prettyJSONBuilder);
            prettyJSONBuilder.append(charFromUnformattedJson);
            break;
            case ',':
            // Ending a json item; create a new line after
            prettyJSONBuilder.append(charFromUnformattedJson);
            if(!inQuote) {
                appendIndentedNewLine(indentLevel, prettyJSONBuilder);
            }
            break;
            default:
            prettyJSONBuilder.append(charFromUnformattedJson);
        }
        }
        return prettyJSONBuilder.toString();
    }

    /**
     * Print a new line with indention at the beginning of the new line.
     * @param indentLevel
     * @param stringBuilder
     */
    private static void appendIndentedNewLine(int indentLevel, StringBuilder stringBuilder) {
        stringBuilder.append("\n");
        for(int i = 0; i < indentLevel; i++) {
        // Assuming indention using 2 spaces
        stringBuilder.append("  ");
        }
    }

    /**
     * Main
     * @param args
     */
    public static void main(String[] args){
        try{
            SendPost.callPost("INSERT_SECRET", "INSERT_SHARED", "INSERT_ORGANIZATION");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}