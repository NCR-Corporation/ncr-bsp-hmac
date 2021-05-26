# NCR Business Services Platform HMAC

[![Python Tests](https://github.com/NCR-Corporation/ncr-bsp-hmac/actions/workflows/python-app.yml/badge.svg)](https://github.com/NCR-Corporation/ncr-bsp-hmac/actions/workflows/python-app.yml)

### Table of Contents

1. [Introduction](#introduction)
2. [Advantages](#advantages)
3. [Implementation](#implementation)
4. [Examples](#examples)
5. [Contributing](#contributing)

<a name="introduction"></a>

## Introduction

In order to use NCR Business Services Platform APIs in a production environment, hash-based message authentication code is required. Access Key authentication uses a hash-based message authentication code (HMAC) to uniquely sign a single HTTP request. This method is intended for non-interactive users (technical users) who are involved in software-to-software or system-to-system authentication scenarios.

Access keys are comprised of two parts: a **secret key** and a **shared key**.

Below is an example of access key authentication in an HTTP request:

```bash
GET /provisioning/user-profiles HTTP/1.1
Accept: application/json
Authorization: AccessKey e63ca6a9ca2e4db2bc13b741e7488437:Ysvt4LcqSnmIjvPbolVm2bS/zDXdqnYBtgtG+lWMlLI6uJp1MJiW34OVNtYrYA/B+6T/NDqhqFxbtlvuIFBliw==
Date: Wed, 26 Jun 2019 17:38:30 GMT
Host: gateway.ncrplatform.com
```

The scheme used in the `Authorization` header is `AccessKey <shared-key>:<hmac>`.

<a name="advantages"></a>

## Advantages

There are some advantages to HMAC authentication, such as:

- It allows maintenance of a single platform identity with multiple credentials expressed in the form of access keys.
- Individual access keys can be disabled or deleted without affecting other keys or the identity itself.
- The secret key never expires, which simplifies interaction with the platform as compared to using temporary access tokens.
- This method guarantees authenticity of the full request instead of a specific part of the request (such as the Authorization header).
- Actual user secrets are never transmitted over the wire as each request has a unique HMAC.

<a name="implementation"></a>

## Implementation

The credentials for `Authorization` request contain a user's shared key and HMAC generated from their shared key. The general algorithm for generating HMAC goes as follows:

1. Get the native `date` value from the request's HTTP headers (passed in)

   ```js
   // js example
   let date = new Date();
   ```

2. Convert the native `date` to `ISO-8601` string format

   ```js
   // js example
   let isoDate = date.toISOString().slice(0, 19) + ".000Z";
   ```

3. Generate a unique key from the following concatenate string: `{secretKey + ':' + date}`

   ```js
   // js example
   const uniqueKey = secretKey + isoDate;
   ```

4. Generate a signature from the HTTP request data to use in HMAC calculation. The HTTP request data consists of the following parts:

   - **[Required]** HTTP method from the request
   - **[Required]** Encode URI from the HTTP request, path, and query string
   - Content-Type header value (optional)
   - Content-MD5 header value (optional)
   - `nep-application-key` header value (optional)
   - `nep-correlation-id` header value (optional)
   - `nep-organization` header value (optional)
   - `nep-service-version` header value (optional)

   ```js
   // js example
   let uri = encodeURI(requestURL.replace(/^https?:\/\/[^/]+\//, "/"));
   let toSign = httpMethod + "\n" + uri;
   if (contentType) {
     toSign += "\n" + contentType.trim();
   }
   if (contentMD5) {
     toSign += "\n" + contentMD5.trim();
   }
   if (nepApplicationKey) {
     toSign += "\n" + nepApplicationKey.trim();
   }
   if (nepCorrelationID) {
     toSign += "\n" + nepCorrelationID.trim();
   }
   if (nepOrganization) {
     toSign += "\n" + nepOrganization.trim();
   }
   if (nepServiceVersion) {
     toSign += "\n" + nepServiceVersion.trim();
   }
   ```

5. Encrypt the generated signature with the unique key (from Step 3)

   ```js
   // js from crypto-js
   const key = hmacSHA512(toSign, oneTimeSecret);
   const hmacKey = Base64.stringify(key);
   // visit hmac.js for more details
   ```

<a name="examples"></a>

## Examples

In this repository we've provided a couple examples in different languages. For each language we provide:

- A helper function in `hmac.*` or `hmacHelper.*`
- A GET request example in `example-get.*`
- A POST/PUT request example in `example-post.*`

To use these functions, you will need your Shared Key, Secret Key, and Organization.

| [JS Examples](https://github.com/NCR-Corporation/ncr-bsp-hmac/tree/main/js) | [Python Examples](https://github.com/NCR-Corporation/ncr-bsp-hmac/tree/main/python) | [PowerShell Examples](https://github.com/NCR-Corporation/ncr-bsp-hmac/tree/main/powershell) | [Go Examples](https://github.com/NCR-Corporation/ncr-bsp-hmac/tree/main/go)
| [Java Examples](https://github.com/NCR-Corporation/ncr-bsp-hmac/tree/main/java) | [Dotnet Examples](https://github.com/NCR-Corporation/ncr-bsp-hmac/tree/main/dotnet) | ------------------------------------------------------------------------------------------- |

## Support

Feel free to open an issue to ask questions or if any issues are found.

## Contributing

Hey! Thanks for contributing to this repository to help it become even better. 

If you are adding a new language there are some requirements around how it is structure:

1. All code should be contained in the language specific folder (unless otherwise required as a dependency)
2. Every language specific folder should include:
   - A README.md with instructions on how to use the code
   - An example GET request
   - An example POST request
   
Visit the `/js` and `/python` folders for examples of these files - the example GET and POST request utilize the Sites API.

Lastly, be sure to update the main README.md in the [Examples](#examples) section to add a new table element for your new code. 
