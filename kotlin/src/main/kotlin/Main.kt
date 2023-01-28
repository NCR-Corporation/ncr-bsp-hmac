import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import java.net.URI
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.UUID

/**
 * NCR BSP HMAC Kotlin main entry point.
 *
 * @param args Command line arguments.
 */
fun main(args: Array<String>) {
    val parser = ArgParser("bsp-hmac")

    val sharedKey by parser.option(ArgType.String, fullName = "shared-key", description = "BSP Shared Key")
        .default("SHARED_KEY")
    val secretKey by parser.option(ArgType.String, fullName = "secret-key", description = "BSP Secret Key")
        .default("SECRET_KEY")
    val org by parser.option(ArgType.String, description = "BSP Organization").default("dev-ex")
    parser.parse(args)

    send(sharedKey, secretKey, "https://api.ncr.com/security/role-grants/user-grants/self/effective-roles", "GET", org)
    send(sharedKey, secretKey, "https://api.ncr.com/security/authentication/login", "POST", org)
}

/**
 * Send HTTP request using HttpClient and HttpRequest.
 *
 * @param sharedKey BSP shared key.
 * @param secretKey BSP secret key.
 * @param urlString HTTP request URL.
 * @param method HTTP request method.
 * @param org BSP organization.
 */
fun send(sharedKey: String, secretKey: String, urlString: String, method: String, org: String) {
    val httpClient = HttpClient.newHttpClient();
    val now = Instant.now().truncatedTo(ChronoUnit.SECONDS)
    val url = URL(urlString)
    val nepCorrelationId = "ncr-bsp-hmac-kotlin-" + UUID.randomUUID().toString()
    val nepApplicationKey = UUID.randomUUID().toString()

    val hmac = BspHmacGenerator.generateHmac(
        sharedKey = sharedKey,
        secretKey = secretKey,
        now = now,
        httpMethod = method,
        url = url,
        nepOrganization = org,
        nepCorrelationId = nepCorrelationId,
        nepApplicationKey = nepApplicationKey
    )

    val request = HttpRequest
        .newBuilder(URI.create(urlString))
        .method(method, HttpRequest.BodyPublishers.noBody())
        .header("Authorization", "AccessKey $hmac")
        .header("Date", DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneOffset.UTC).format(now))
        .header("Accept", BspHmacGenerator.JSON)
        .header("Content-Type", BspHmacGenerator.JSON)
        .header("nep-organization", org)
        .header("nep-correlation-id", nepCorrelationId)
        .header("nep-application-key", nepApplicationKey)
        .build();

    val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

    println("Sent $method request to URL : $url; Response Code : ${response.statusCode()}")

    println(response.body())
}
