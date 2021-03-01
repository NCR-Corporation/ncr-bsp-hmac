# HMAC JS Examples

The example helper function lives in `hmac.js` which containst the details on how to use HMAC to generate an `AccessKey`

### Getting Started

1. Run `npm install` to install the two dependencies - `node-fetch` and `crypto-js`
2. Inside of `example-get.js` and `example-post.js` update the `nepOrganization`, `secretKey`, and `sharedKey` with your values:

```js
  let options = {
    date,
    secretKey: "INSERT HERE",
    sharedKey: "INSERT HERE",
    nepOrganization: "INSERT HERE",
    ...
  };
```

3. To test:

```console
$ node example-get.js
// node example-post.js
```
