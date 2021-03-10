package sign

import "net/http"

type HTTPSigner interface {
	Sign(req *http.Request) (*http.Request, error)
}
