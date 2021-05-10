import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HmacGenerator {
    private static final String HMAC_SHA512 = "HmacSHA512";

    public String generateHmac(
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
        String path = url.getPath() + "?" + url.getQuery();

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
        
        System.out.println(sharedKey + ":" + Base64.getEncoder().encodeToString(rawHmac));
        return sharedKey + ":" + Base64.getEncoder().encodeToString(rawHmac);
    }
}