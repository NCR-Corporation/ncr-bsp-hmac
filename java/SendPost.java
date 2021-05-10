import java.net.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SendPost extends HmacGenerator{
    public static void main(String[] args){
        try{
            SendPost.callPost("INSERT_SECRET", "INSERT_SHARED", "INSERT_ORGANIZATION");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void callPost(String secretKey, String sharedKey, String nepOrganization) throws NoSuchAlgorithmException, MalformedURLException, IOException, ProtocolException, InvalidKeyException{
        String url = "https://gateway-staging.ncrcloud.com/site/sites/find-by-criteria?pageNumber=0&pageSize=10";
        String httpMethod = "POST";
        String contentType = "application/json";
        String dataJSON = "{\"sort\":[{\"column\":\"siteName\",\"direction\":\"asc\"}]}";

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
 
        System.out.println("{'status': " + status + ", 'data': " + content + "}" );
 
        in.close();
        connection.disconnect();
    }
}