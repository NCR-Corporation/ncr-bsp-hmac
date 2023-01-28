# NCR BSP HMAC Kotlin Examples
The `BspHmacGenerator` object contains a reference implementation of NCR BSP HMAC AccessKey signature generation in Kotlin.

This repo also contains examples of `GET` and `POST` requests using Java's [HttpClient](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html).

For production use you should consider an alternative like [OkHttp](https://square.github.io/okhttp/), [Fuel](https://github.com/kittinunf/fuel), [Ktor](https://ktor.io/) [HttpClient](https://api.ktor.io/ktor-client/ktor-client-core/io.ktor.client/-http-client), or [Spring](https://spring.io/) [WebClient](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html)

Depends on [Kotlin](https://blog.jetbrains.com/kotlin/) 1.8.0 and [JDK 19](https://jdk.java.net/19/).

## Command Line Usage

### Arguments

`--shared-key` BSP User Shared Key

`--secret-key` BSP User Secret Key

`--org` Target BSP Organization (typically your user's home org, defaults to `dev-ex`)

### Example

`./gradlew run --args="--shared-key INSERT_SHARED_KEY --secret-key INSERT_SECRET_KEY --org INSERT_ORGANIZATION"`
