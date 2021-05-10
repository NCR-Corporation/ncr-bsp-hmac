import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * A helper class that generates an HMAC token created with
 * the parameters being passed into the call.
 */

public class HmacGenerator {
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
        
        return sharedKey + ":" + Base64.getEncoder().encodeToString(rawHmac);
    }
}