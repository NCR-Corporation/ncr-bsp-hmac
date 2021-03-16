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
   // GET Request to find sites nearby:
   // {status: 200, data: {'sites': [], 'totalResults': 0}}
   // ...
   ```