# HMAC Java Examples

The example helper function lives in `HmacGenerator.java` which contains the details on how to use HMAC to generate an `AccessKey`

### Getting Started

_This code uses Java 11.0.11_

1. All files are inside of `src/main/java`.
1. Inside of `SendGet.java` and `SendPost.java` update the `nepOrganization`, `secretKey`, and `sharedKey` with your values:

   ```java
   SendGet.callGet("INSERT_SECRET", "INSERT_SHARED", "INSERT_ORGANIZATION");
   // or
   SendPost.callPost("INSERT_SECRET", "INSERT_SHARED", "INSERT_ORGANIZATION");
   ```

1. Once you have made your changes, be sure to save the files before you compile in the next step.

1. Compile the two request classes inside of the java directory:

   ```console
   $ cd java/src/main/java
   ```

   ```console
   $ javac SendGet.java SendPost.java
   // Compiles and creates the class for the GET and POST API call
   ```

1. To test:

   ```console
   $ java -cp . SendGet
   // GET Request to view the first 10 roles in BSP:
   // {'status': 200 },
   // {'data': {
   //   "content":[
   //      {
   //         "roleName":"ROLE_NAME"
   //      },
   //      {
   //         "roleName":"ROLE_NAME"
   //      },
   //      ...More Roles...
   //   ]
   // }}
   ```

   ```console
   $ java -cp . SendPost
   // POST Request gain an access token
   //{ "status": OK }
   //{'status': 200 },
   //{'data': {
   //"token":"[REDACTED]",
   //"maxIdleTime":900,
   //"maxSessionTime":900,
   //"remainingTime":900,
   //"authorities":[
   //  "NEP_IDENTITY_DELETE",
   //  "DELIVERY_SCRUBBER",
   //  "R1_ORDER_SITE_ADMINISTRATOR",
   //  "R1_ORDER_SITE_VIEWER",
   //  "TDM_ADMIN",
   //  "NEP_CONFIG_SETTINGS_VIEWER",
   //}
   ```
