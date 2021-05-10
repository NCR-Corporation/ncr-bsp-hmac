import java.net.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SendGet extends HmacGenerator{
    /**
     * Main
     * @param args
     */
    public static void main(String[] args){
        try{
            SendGet.callGet("INSERT_SECRET", "INSERT_SHARED", "INSERT_ORGANIZATION");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * The method to build the GET request
     * @param secretKey  A user's Secret Key
     * @param sharedKey A user's Shared Key
     * @param nepOrganization A user's organization
     * @throws NoSuchAlgorithmException
     * @throws MalformedURLException
     * @throws IOException
     * @throws ProtocolException
     * @throws InvalidKeyException
     */
    public static void callGet(String secretKey, String sharedKey, String nepOrganization) throws NoSuchAlgorithmException, MalformedURLException, IOException, ProtocolException, InvalidKeyException{
        String url = "https://gateway-staging.ncrcloud.com/site/sites/find-nearby/88.05,46.25?radius=10000";
        String httpMethod = "GET";
        String contentType = "application/json";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        
        HmacGenerator hmacGenerator = new HmacGenerator();
        String hmacAccesskey = hmacGenerator.generateHmac(sharedKey, secretKey, format.format(date), httpMethod, url, contentType, "", "", nepOrganization, "");

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
        
        int status = connection.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        
        while((inputLine = in.readLine()) != null){
            content.append(inputLine);
        }

        System.out.println("{'status': " + status + ", 'data': " + content + "}" );
        
        in.close();
        connection.disconnect();
    }
}