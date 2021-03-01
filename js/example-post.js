const hmac = require("./hmac");
const fetch = require("node-fetch");

async function examplePost() {
  let nepOrganization = "";
  let secretKey = "";
  let sharedKey = "";

  let requestURL =
    "https://gateway-staging.ncrcloud.com/site/sites/find-by-criteria?pageNumber=0&pageSize=200";
  let httpMethod = "POST";
  let contentType = "application/json";
  let body = {
    sort: [
      {
        column: "siteName",
        direction: "asc",
      },
    ],
  };

  let date = new Date();

  let headers = {
    secretKey,
    sharedKey,
    requestURL,
    date,
    httpMethod,
    contentType,
    nepOrganization,
  };

  const hmacAccessKey = hmac(headers);

  var requestOptions = {
    method: headers.httpMethod,
    headers: {
      "Content-Type": headers.contentType,
      Authorization: `AccessKey ${hmacAccessKey}`,
      "nep-organization": headers.nepOrganization,
      Date: date.toGMTString(),
    },
    body: JSON.stringify(body),
  };

  const response = await fetch(headers.requestURL, requestOptions);

  const status = response.status;

  // PUT requests return 204 with no response.
  if (status == 204) {
    console.log("Success");
  } else {
    let json = await response.json();
    console.log(json);
  }
}

examplePost();
