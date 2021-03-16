const exampleGet = require("./example-get");
const examplePost = require("./example-post");

async function call() {
  let secretKey = "INSERT_SECRET";
  let sharedKey = "INSERT_SHARED";
  let nepOrganization = "INSERT_ORGANIZATION";

  let getResponse = await exampleGet(secretKey, sharedKey, nepOrganization);

  console.log(getResponse);

  let postReponse = await examplePost(secretKey, sharedKey, nepOrganization);

  console.log(postReponse);
}
call();
