# HMAC JS Examples

The example helper function lives in `hmac.js` which containst the details on how to use HMAC to generate an `AccessKey`

### Getting Started

1. Run `npm install` to install the two dependencies - `node-fetch` and `crypto-js`
2. Inside of `index.js` update the `nepOrganization`, `secretKey`, and `sharedKey` with your values:

   ```js
      let secretKey = "INSERT_SECRET";
      let sharedKey = "INSERT_SHARED";
      let nepOrganization = "INSERT_ORGANIZATION";
   ```

3. To test:

   ```console
   $ node index.js
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