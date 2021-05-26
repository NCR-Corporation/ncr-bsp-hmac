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
   //       },
   //       {
   //            "roleName": "ALOHA_AAL_SERVER_SITE_SOLUTION_SET_RESOLVER_VIEWER",
   //            "description": "User with this role is authorized to see solution set resolution for an enterprise unit.",
   //            "restrictImplies": false
   //       },
   //       {
   //            "roleName": "ALOHA_AAL_SERVER_SITE_STAGING_ADMINISTRATOR",
   //            "description": "User with this role is authorized to create and delete installation tokens within the given organization.",
   //            "restrictImplies": false
   //       },
   //       {
   //            "roleName": "ALOHA_AAL_SERVER_SITE_STAGING_VIEWER",
   //            "description": "User with this role is authorized retrieve the configuration data related to site staging.",
   //            "restrictImplies": false
   //       },
   //       {
   //            "roleName": "ALOHA_AAL_SERVER_SOLUTION_SET_ADMINISTRATOR",
   //            "description": "User with this role is authorized to administrate solution sets from AAL Server API.",
   //            "restrictImplies": false
   //       },
   //       {
   //            "roleName": "ALOHA_AAL_SERVER_SOLUTION_SET_VIEWER",
   //            "description": "User with this role is authorized to view solution sets from AAL Server API.",
   //            "restrictImplies": false
   //       },
   //       {
   //            "roleName": "ALOHA_AAL_SERVER_TECHNICAL_USER",
   //            "description": "Technical AAL Server user.",
   //            "restrictImplies": false
   //       },
   //       {
   //            "roleName": "ALOHA_AAL_SERVER_TECHNICAL_USER_ADMINISTRATOR",
   //            "description": "User with this role is authorized to create and retrieve credentials of site-level technical users.",
   //            "restrictImplies": false
   //       },
   //       {
   //            "roleName": "ALOHA_AAL_SERVER_UPGRADE_POOL_ADMINISTRATOR",
   //            "description": "User with this role is authorized to administrate upgrade pools from AAL Server API.",
   //             "restrictImplies": false
   //       }
   //    ]
   //  }
   //}

   // POST Request gain an access token
   //{ "status": OK }
   //{ "Data": 
   //   {
   //      "token": "{{YOUR_TOKEN}}}",
   //      "maxIdleTime": 900,
   //      "maxSessionTime": 900,
   //      "remainingTime": 900,
   //      "authorities": [
   //      "NEP_IDENTITY_VIEWER",
   //      "NEP_CONFIG_SETTINGS_ADMINISTRATOR",
   //      "DELIVERY_SCRUBBER",
   //      "R1_DC_APPLICATION_ROLE",
   //      "R1_DC_OFFER_USER",
   //      "R1_ORDER_SITE_ADMINISTRATOR",
   //      "DYNAMIC_TYPE_WRITER",
   //      "R1_ORDER_SITE_VIEWER",
   //      "TDM_ADMIN",
   //      "NEP_CONFIG_SETTINGS_VIEWER",
   //      "CDM_CUSTOMER_VIEWER",
   //      "DELIVERY_THRESHOLD_VIEWER",
   //      "R1_CATALOG_VIEWER",
   //      "NEP_APPLICATION_SPECTATOR",
   //      "PES_GET",
   //      "R1_TDM_UPLOAD",
   //      "R1_ORDER_LOCK_ADMIN",
   //      "SITE_READ",
   //      "NEP_ORGANIZATION_VIEWER",
   //      "CDM_PARTNER_CONSUMER_SEARCH",
   //      "DYNAMIC_TYPE_VIEWER",
   //      "R1_DC_PROVIDER",
   //      "NEP_STORAGE_OBJECT_MODIFIER",
   //      "R1_SUPPORT_CLIPPER",
   //      "PES_END_OF_DAY",
   //      "R1_CATALOG_APPLICATION",
   //      "TDM_UPLOAD",
   //      "R1_DC_OFFER_SUPERUSER",
   //      "PES_ERROR",
   //      "NEP_STORAGE_CONTAINER_VIEWER",
   //      "NEP_ENTERPRISE_SUPER_VIEWER",
   //      "NEP_MESSAGING_SUBSCRIPTION_VIEWER",
   //      "R1_ORDER_VIEWER",
   //      "SITE_IMPORT",
   //      "CDM_CUSTOMER_MODIFIER",
   //      "R1_ORDER",
   //      "PES_VOID",
   //      "DELIVERY_UPDATER",
   //      "R1_DC_APPLICATION",
   //      "NEP_DEPLOYMENT_VIEWER",
   //      "TDM_WRITE",
   //      "R1_DC_POS",
   //      "NEP_STORAGE_CONTAINER_ADMINISTRATOR",
   //      "IAS_SINGLE_ITEM_VIEWER",
   //      "IAS_MULTIPLE_ITEMS_VIEWER",
   //      "NEP_ENTERPRISE_SUPER_ADMINISTRATOR",
   //      "SITE_CREATE",
   //      "R1_DC_VIEWER",
   //      "R1_SUPPORT_USER",
   //      "CDM_CUSTOMER_SEARCH",
   //      "PES_FINALIZE",
   //      "CDM_PARTNER_CONSUMER_CREATE",
   //      "NEP_APIMANAGEMENT_VIEWER",
   //      "OCP_SITE_ADMINISTRATOR",
   //      "DYNAMIC_TYPE_ADMINISTRATOR",
   //      "IAS_MULTIPLE_ITEMS_ADMINISTRATOR",
   //      "NEP_ENTERPRISE_GRANT_VIEWER",
   //      "DELIVERY_REQUESTOR",
   //      "NEP_JOURNALING_VIEWER",
   //      "R1_ORDER_ADMINISTRATOR",
   //      "CDM_ADMINISTRATOR",
   //      "NEP_STORAGE_OBJECT_VIEWER",
   //      "TDM_READ",
   //      "NEP_EMAIL_SENDER",
   //      "R1_SUPPORT_ADMINISTRATOR",
   //      "NEP_ENTERPRISE_VIEWER",
   //      "NEP_MESSAGING_SUBSCRIBER",
   //      "R1_DC_ADMINISTRATOR",
   //      "DX_SANDBOX",
   //      "TDM_UPDATE",
   //      "R1_TDM_ADMIN",
   //      "R1_CATALOG_ADMINISTRATOR",
   //      "NEP_JOURNALING_WRITER",
   //      "CDM_CUSTOMER_DELETER",
   //      "PES_APP_ROLE",
   //      "NEP_ROLE_VIEWER",
   //      "SITE_ENTERPRISE_SETTING_READ",
   //      "NEP_IDENTITY_ADMINISTRATOR",
   //      "R1_TDM_VIEWER",
   //      "DELIVERY_VIEWER",
   //      "TDM_SCRUB",
   //      "SITE_MESSAGING_SUBSCRIBE",
   //      "SITE_UPDATE",
   //      "IAS_SINGLE_ITEM_ADMINISTRATOR"
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