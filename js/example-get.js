const hmac = require("./hmac");
const fetch = require("node-fetch");

async function exampleGet(secretKey, sharedKey, nepOrganization) {
  let date = new Date();

  let options = {
    date,
    secretKey,
    sharedKey,
    nepOrganization,
    requestURL:
      "https://api.ncr.com/security/roles?roleNamePattern=*&pageNumber=0&pageSize=10",
    httpMethod: "GET",
    contentType: "application/json",
  };

  // Utilizes the helper function in hmac.js
  const hmacAccessKey = hmac(options);

  var requestOptions = {
    method: options.httpMethod,
    headers: {
      "Content-Type": options.contentType,
      Authorization: `AccessKey ${hmacAccessKey}`,
      "nep-organization": options.nepOrganization,
      Date: date.toGMTString(),
    },
  };

  const response = await fetch(options.requestURL, requestOptions);
  const data = await response.json();
  let dataString = JSON.stringify(data, null, 4)

  return {
    status: response.status,
    dataString,
  };
}

module.exports = exampleGet;
