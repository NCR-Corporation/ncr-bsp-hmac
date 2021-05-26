# HMAC Python Examples

The example helper function lives in `hmacHelper.py` which containst the details on how to use HMAC to generate an `AccessKey`

### Getting Started

_This code uses Python 3_

1. Make sure `requests` is installed with `pip3 install requests`
2. Inside of `example-get.py` and `example-post.py` update the `nepOrganization`, `secretKey`, and `sharedKey` with your values:

   ```py
   secretKey = 'INSERT SECRET'
   sharedKey = 'INSERT SHARED'
   nepOrganization = 'INSERT ORGANIZATION'
   ```

3. To test:

   ```console
   $ python3 exampleGet.py
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
   $ python3 examplePost.py
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