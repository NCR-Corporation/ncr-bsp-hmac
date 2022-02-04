# NCR BSP HMAC Dart Examples
Use the `BspHmacClient` to make HMAC AccessKey authorized requests to NCR BSP APIs.

Requires Dart SDK >= `2.15.1`

This repo contains an example `GET` and `POST` request.

## Command Line 

### Arguments

`--shared-key` BSP User Shared Key

`--secret-key` BSP User Secret Key

`--org` Target BSP Organization (typically your user's home org, defaults to `dev-ex`)

`--base-url` BSP Base URL (defaults to `https://api.ncr.com`)

### Example
`dart run ./bin/dart.dart --shared-key=my_shared_key --secret-key=my_secret_key --org=target_org`
