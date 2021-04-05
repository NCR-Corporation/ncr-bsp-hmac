package sign

import (
	"crypto/hmac"
	"crypto/sha512"
	"encoding/base64"
	"fmt"
	"net/http"
	"strings"
)

const (
	AccessKeyPrefix = "AccessKey"
	DateHeader      = "Date"
	DateTimeFormat  = "2006-01-02T15:04:05.000Z"
)

// accessKeyHTTPSigner implements the HTTPSigner interface
type accessKeyHTTPSigner struct {
	sharedKey string
	secretKey string
}

// NewAccessKeyHTTPSigner is a function that returns an implementation that conforms to the sign.Signer interface
func NewAccessKeyHTTPSigner(sharedKey, secretKey string) (HTTPSigner, error) {
	if len(sharedKey) == 0 || len(secretKey) == 0 {
		return nil, fmt.Errorf("sharedKey or secretKey cannot be an empty string")
	}
	return &accessKeyHTTPSigner{sharedKey, secretKey}, nil
}

func (s *accessKeyHTTPSigner) Sign(req *http.Request) (*http.Request, error) {
	auth := req.Header.Get("Authorization")
	// If the Authorization header already exists then don't do anything
	// TODO(sai): make sure this is what you want to do.
	if len(auth) > 0 {
		return req, nil
	}
	// 1. get a unique key
	uniqueKey, err := getUniqueKey(s.secretKey, req)
	if err != nil {
		return nil, fmt.Errorf("sign: error while generating new unique key for HMAC signing: %v", err)
	}
	// 2. get signable content
	signableContent := getSignableContent(req)

	// 3. HMAC content

	hmacContent, err := calculateHMAC(uniqueKey, signableContent)
	if err != nil {
		return nil, fmt.Errorf("sign: error while calculating hmac: %v", err)
	}

	accessKey := s.sharedKey + ":" + hmacContent

	req.Header.Add("Authorization", fmt.Sprintf("%s %s", AccessKeyPrefix, accessKey))

	return req, nil
}

func getUniqueKey(secretKey string, req *http.Request) (string, error) {
	date := req.Header.Get(DateHeader)
	if len(date) == 0 {
		return "", fmt.Errorf("sign: error while access header %s from the request", DateHeader)
	}
	parsedDate, err := http.ParseTime(date)
	if err != nil {
		return "", fmt.Errorf("sign: error while parsing date to RFC specification: %v", err)
	}
	return strings.TrimSpace(secretKey) + parsedDate.Format(DateTimeFormat), nil
}

func getSignableContent(req *http.Request) string {
	query := req.URL.RawQuery
	pathAndQuery := ""
	if len(query) > 0 {
		pathAndQuery = req.URL.EscapedPath() + "?" + query
	} else {
		pathAndQuery = req.URL.EscapedPath()
	}
	headers := req.Header
	contentType := strings.TrimSpace(headers.Get("Content-Type"))
	contentMD5 := headers.Get("Content-MD5")
	nepApplicationKey := headers.Get("nep-application-key")
	nepCorrelationId := headers.Get("nep-correlation-id")
	nepOrganization := headers.Get("nep-organization")
	nepServiceVersion := headers.Get("nep-service-version")

	signableContent := []string{
		req.Method,
		pathAndQuery,
		contentType,
		contentMD5,
		nepApplicationKey,
		nepCorrelationId,
		nepOrganization,
		nepServiceVersion,
	}

	signableContent = filterSignableContent(signableContent, func(c string) bool {
		return len(c) > 0
	})
	return strings.Join(signableContent, "\n")
}

func filterSignableContent(content []string, f func(string) bool) []string {
	cf := make([]string, 0)
	for _, c := range content {
		if f(c) {
			cf = append(cf, c)
		}
	}
	return cf
}

func calculateHMAC(uniqueKey, signableContent string) (string, error) {
	h := hmac.New(sha512.New, []byte(uniqueKey))
	_, err := h.Write([]byte(signableContent))
	if err != nil {
		return "", fmt.Errorf("sign: error while writing to HMAC writer: %v", err)
	}
	sum := h.Sum(nil)
	return base64.StdEncoding.EncodeToString(sum), nil
}
