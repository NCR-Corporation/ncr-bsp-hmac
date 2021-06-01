# HMAC Java Examples

The example helper function lives in the `generateHmac` method which contains the details on how to use HMAC to generate an `AccessKey`

### Getting Started

_This code uses Java 11.0.11_

1. Inside of `SendGet.java` and `SendPost.java` update the `nepOrganization`, `secretKey`, and `sharedKey` with your values:

   ```java
   secretKey = 'INSERT_SECRET'
   sharedKey = 'INSERT_SHARED'
   nepOrganization = 'INSERT_ORGANIZATION'
   ```

2. Once you have made your changes, be sure to save the files before you compile in the next step.

3. Compile the two request classes inside of the java directory:

   ```console
   $ javac SendGet.java
   // Compiles and creates the class for the GET API call
   ```

   ```console
   $ javac SendPost.java
   // Compiles and creates the class for the POST API call
   ```

4. To test:

   ```console
   $ java SendGet.java
   // GET Request to view the first 10 roles in BSP:
   //{ "status": OK }
   //{ "Data": {
   //    "lastPage": false,
   //    "pageNumber": 0,
   //    "totalPages": 190,
   //    "totalResults": 1899,
   //    "pageContent": [
   //       {
   //            "roleName": "ALOHA_AAL_SERVER_LICENSE_DOWNLOAD",
   //            "description": "User with this role is authorized to download ALX license from AAL Server API.",
   //            "restrictImplies": false
   //       },
   //       {
   //            "roleName": "ALOHA_AAL_SERVER_SITE_MANIFEST_VIEWER",
   //            "description": "User with this role is authorized to see site manifest for an enterprise unit.",
   //            "restrictImplies": false
   //       }
   //       ... Roles List ...
   ```

   ```console
   $ java SendPost.java
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
