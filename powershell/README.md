# HMAC PowerShell Examples

The example helper function lives in `hmac.psm1` which contains the HMAC `AccessKey` implementation.

### Getting Started

1. Inside of `Example-Get.ps1` and `Example-Post.ps1` update the `$organization`, `$secretKey`, and `$sharedKey` with your values:

   ```powershell
   $secretKey = 'INSERT SECRET KEY'
   $sharedKey = 'INSERT SHARED KEY'
   $organization = 'INSERT ORGANIZATION'
   ```

2. To test:

   ```console
   PS C:\ncr-bsp-hmac\powershell> .\Example-Get.ps1
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
   PS C:\ncr-bsp-hmac\powershell> .\Example-Post.ps1

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
