## HMAC Go Examples
This go module provides an interface for developers to authorize their HTTP
requests with BSP HMAC Authentication.

The interface [`HTTPSigner`](sign/signer.go) contains one method
`Sign`, when invoked will use HMAC to sign the request and add it to the `Authorization` header of the request provided.
### Getting Started
There are two examples under the [examples](examples/) folder that demonstrate
a HTTP `GET` and `POST` request. To run these examples follow the steps below:

1. Have a working Go environment. You can check whether go is installed on your
machine by running
```bash
$ go version
go version go1.15.2 darwin/amd64
```
2. Update the example with your organization, shared and secret keys
```go
req.Header.Add("Date", time.Now().UTC().Format(http.TimeFormat))
req.Header.Add("nep-organization", "<your_organization>")
req.Header.Add("Content-Type", "application/json")

...

// Replace the empty string with your shared key
sharedKey := "<shared_key>"
// Replace the empty string with your secret key
secretKey := "<shared_key>"
```
3. Run the go command to see the output
```bash
$ go run go/examples/get/main.go
```
or
```bash
$ go run go/examples/post/main.go
```

### Other Usages
1. You can copy the code in the [`access_key_signer.go`](
    sign/access_key_signer.go) and use it in your own project.
2. You can import this project as a Go module by adding the following import
statement in your code.
```go
import "github.com/NCR-Corporation/ncr-bsp-hmac/go/sign"
```
Subsequently, when you run `go build` or `go run`, the compiler should be able
to automatically resolve the dependency and add it to your project.