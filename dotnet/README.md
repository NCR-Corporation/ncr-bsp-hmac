# HMAC Dotnet Examples

The example helper function and the functionality of the request live in `Program.cs` which contains the method Main, the appropriate call, and the hmac helper, as well as the details on how to generate the 'AccessKey''

### Getting Started

_This code uses Dotnet 6_

1. Navigate to the directory of the call you would like to send via the command line.

2. Inside of `Program.cs` in the current directory, update the `nepOrganization`, `secretKey`, and `sharedKey` with your values:

   ```C#
   secretKey = 'INSERT_SECRET'
   sharedKey = 'INSERT_SHARED'
   nepOrganization = 'INSERT_ORGANIZATION'
   ```

3. To test, Navigate inside of the correct directory and:

   ```console
   $ dotnet run
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
   $ dotnet run
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
