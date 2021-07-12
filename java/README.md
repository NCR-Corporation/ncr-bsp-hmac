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
   $ javac SendGet.java
   // Compiles and creates the class for the GET API call
   ```

   ```console
   $ javac SendPost.java
   // Compiles and creates the class for the POST API call
   ```

1. To test:

   ```console
   $ java SendGet
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
   $ java SendPost
   // POST Request gain an access token
   //{ "status": OK }
   //{ "Data":
   //   {
   //      "token": "{{YOUR_TOKEN}}}",
   //      "maxIdleTime": 900,
   //      "maxSessionTime": 900,
   //      "remainingTime": 900,
   //      "authorities": [
   //          "NEP_IDENTITY_VIEWER",
   //            ... Roles List ...
   //          "SITE_UPDATE"
   //   ],
   //   "consentScopes": [],
   //   "credentialExpired": false,
   //   "organizationName": "{{YOUR_ORGANIZATION_NAME}}",
   //   "username": "{{YOUR_ORGANIZATION}}",
   //   "authenticationMethods": [
   //      "access-key"
   //   ],
   //   "exchangesCompleted": 0,
   //   "customClaims": [],
   //   "singleUse": false
   //   }
   //}
   ```
