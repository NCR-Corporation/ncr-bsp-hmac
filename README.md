# NCR Business Services Platform HMAC

### Table of Contents

1. [Introduction](#introduction)
2. [Advantages](#advantages)
3. [Implementation](#implementation)
4. [Examples](#examples)

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
2. Convert the native `date` to `ISO-8601` string format
3. Generate a unique key from the following concatenate string: `{secretKey + ':' + date}`
4. Generate a signature from the HTTP request data to use in HMAC calculation. The HTTP request data consists of the following parts:
   - HTTP method from the request (Required)
   - Relaite URI from the HTTP request, path, and query string (Required)
   - Content-Type header value (optional)
   - Content-MD5 header value (optional)
   - `nep-application-key` header value (optional)
   - `nep-correlation-id` header value (optional)
   - `nep-organization` header value (optional)
   - `nep-service-version` header value (optional)

<a name="examples"></a>

## Examples

In this repository we've provided a couple examples in different languages. For each language we provide:

- A helper function in `hmac.*`
- A GET request example in `example-get.*`
- A POST/PUT request example in `example-post.*`

To use these functions, you will need your Shared Key, Secret Key, and Organization.

| [JS Examples](https://github.com/NCR-Corporation/ncr-bsp-hmac/tree/main/js) | [Python Examples](https://github.com/NCR-Corporation/ncr-bsp-hmac/tree/main/python) |
| --------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- |

## Support

Feel free to open an issue to ask questions.
