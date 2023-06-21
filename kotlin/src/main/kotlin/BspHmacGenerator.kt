import java.net.URL
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * NCR BSP HMAC Generator in Kotlin.
 */
object BspHmacGenerator {
    private const val HMAC_SHA512 = "HmacSHA512"
    const val JSON = "application/json"

    /**
     * Generate HMAC signature.
     *
     * @param sharedKey BSP shared key.
     * @param secretKey BSP secret key.
     * @param now Current date time in UTC 00:00 (aka GMT, Zulu).
     * @param httpMethod HTTP request method.
     * @param url HTTP request URL.
     * @param contentType HTTP request content mime time (optional).
     * @param md5Hash MD5 hash of HTTP request body (optional).
     * @param nepApplicationKey BSP application key (optional) (deprecated).
     * @param nepCorrelationId BSP correlation ID (optional).
     * @param nepServiceVersion BSP service version (optional) (deprecated).
     * @param nepOrganization BSP organization (optional).
     * @return Base64 encoded string of SHA512 HMAC signature.
     */
    fun generateHmac(
        sharedKey: String,
        secretKey: String,
        now: Instant,
        httpMethod: String,
        url: URL,
        contentType: String? = null,
        md5Hash: String? = null,
        nepApplicationKey: String? = null,
        nepCorrelationId: String? = null,
        nepServiceVersion: String? = null,
        nepOrganization: String? = null
    ): String {
        var path = url.path

        val query = url.query
        if (!query.isNullOrBlank()) {
            path += "?$query"
        }

        val datePattern = "yyyy-MM-dd'T'HH:mm:ss'.000Z'"
        val formatter = DateTimeFormatter.ofPattern(datePattern).withZone(ZoneOffset.UTC)
        val isoDate = formatter.format(now)

        val oneTimeSecret = secretKey + isoDate

        var toSign = httpMethod
        toSign += "\n" + path
        toSign += "\n" + JSON

        if (!contentType.isNullOrBlank()) {
            toSign += "\n" + contentType
        }

        if (!md5Hash.isNullOrBlank()) {
            toSign += "\n" + md5Hash
        }

        if (!nepApplicationKey.isNullOrBlank()) {
            toSign += "\n" + nepApplicationKey
        }

        if (!nepCorrelationId.isNullOrBlank()) {
            toSign += "\n" + nepCorrelationId
        }

        if (!nepOrganization.isNullOrBlank()) {
            toSign += "\n" + nepOrganization
        }

        if (!nepServiceVersion.isNullOrBlank()) {
            toSign += "\n" + nepServiceVersion
        }

        val mac = Mac.getInstance(HMAC_SHA512)
        val oneTimeSecretUtf8Bytes = oneTimeSecret.toByteArray()
        val keySpec = SecretKeySpec(oneTimeSecretUtf8Bytes, HMAC_SHA512)
        mac.init(keySpec)

        val rawHmac = mac.doFinal(toSign.toByteArray())

        val base64 = Base64.getEncoder().encodeToString(rawHmac)

        return "$sharedKey:$base64"
    }
}
