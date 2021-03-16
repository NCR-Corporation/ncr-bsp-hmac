const hmac = require("./hmac");
const fetch = require("node-fetch");

// An example post request to find nearby sites from the Sites API
async function examplePost(secretKey, sharedKey, nepOrganization) {
  let date = new Date();

  let options = {
    date,
    secretKey,
    sharedKey,
    nepOrganization,
    requestURL:
      "https://gateway-staging.ncrcloud.com/site/sites/find-by-criteria?pageNumber=0&pageSize=200",
    httpMethod: "POST",
    contentType: "application/json",
  };

  const hmacAccessKey = hmac(options);

  let body = {
    sort: [
      {
        column: "siteName",
        direction: "asc",
      },
    ],
  };

  var requestOptions = {
    method: options.httpMethod,
    headers: {
      "Content-Type": options.contentType,
      Authorization: `AccessKey ${hmacAccessKey}`,
      "nep-organization": options.nepOrganization,
      Date: date.toGMTString(),
    },
    body: JSON.stringify(body),
  };

  const response = await fetch(options.requestURL, requestOptions);

  const status = response.status;

  // PUT requests return 204 with no response.
  if (status == 204) {
    console.log("Success");
  } else {
    let data = await response.json();
    return {
      status,
      data,
    };
  }
}

module.exports = examplePost;
