const exampleGet = require("./example-get");
const examplePost = require("./example-post");

test("exampleGet returns 200", async () => {
  let response = await exampleGet(
    process.env.SECRET_KEY,
    process.env.SHARED_KEY,
    process.env.ORGANIZATION
  );
  expect(response.status).toBe(200);
});

test("examplePost returns 200", async () => {
  let response = await examplePost(
    process.env.SECRET_KEY,
    process.env.SHARED_KEY,
    process.env.ORGANIZATION
  );
  expect(response.status).toBe(200);
});
