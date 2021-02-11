const hmac = require("./hmac");

let organization = "";
let secretKey = "";
let sharedKey = "";

let url = "";
let method = "GET";
let contentType = "application/json";

let date = new Date();

let headers = {
  url,
  date,
  method,
  contentType,
  organization,
  secretKey,
  contentMD5: contentMD5 ? contentMD5 : "",
};

// Utilizes the helper function in hmac.js
const hmacAccessKey = hmac(headers);

var requestOptions = {
  method: headers.method,
  headers: {
    "Content-Type": contentType,
    Authorization: `AccessKey ${sharedKey}:${hmacAccessKey}`,
    "nep-organization": organization,
    Date: date.toGMTString(),
  },
};

const response = await fetch(headers.url, requestOptions);

const data = await response.json();

console.log(data);
