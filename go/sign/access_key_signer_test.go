package sign

import (
	"fmt"
	"io/ioutil"
	"net/http"
	"strings"
	"testing"
	"time"
)

func TestAccessKeyHTTPSigner_Sign(t *testing.T) {
	url := "https://gateway-staging.ncrcloud.com/site/sites/find-nearby/88.05,46.25?radius=10000"
	req, _ := http.NewRequest("GET", url, strings.NewReader(""))
	req.Header.Add("Date", time.Now().UTC().Format(http.TimeFormat))
	req.Header.Add("nep-organization", "")
	req.Header.Add("Content-Type", "application/json")

	// Replace the empty string with your shared key
	sharedKey := ""
	// Replace the empty string with your secret key
	secretKey := ""
	s, _ := NewAccessKeyHTTPSigner(sharedKey, secretKey)
	req, err := s.Sign(req)
	if err != nil {
		t.Fatal(err)
	}

	res, err := http.DefaultClient.Do(req)
	if err != nil {
		t.Fatal(err)
	}
	defer res.Body.Close()
	b, err := ioutil.ReadAll(res.Body)
	if err != nil {
		t.Fatal(err)
	}
	fmt.Printf("%s\n", b)
}
