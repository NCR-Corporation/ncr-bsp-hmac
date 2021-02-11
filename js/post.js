const generateHMAC = require("./generateHMAC");

let organization = "";
let secretKey = "";
let sharedKey = "";

let url = "";
let method = "PUT";
let contentType = "application/json";
let body = "";

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

const hmacAccessKey = generateHMAC(headers);

var requestOptions = {
  method: headers.method,
  headers: {
    "Content-Type": contentType,
    Authorization: `AccessKey ${sharedKey}:${hmacAccessKey}`,
    "nep-organization": organization,
    Date: date.toGMTString(),
  },
  body: JSON.stringify(body),
};

const response = await fetch(headers.url, requestOptions);

const status = res.status;

// PUT requests return 204 with no response.
if (status == 204) {
  console.log("Success");
} else {
  console.log(res.status);
}
