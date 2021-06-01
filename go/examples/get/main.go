package main

import (
	"bytes"
	"encoding/json"
	"io/ioutil"
	"log"
	"net/http"
	"strings"
	"time"

	"github.com/NCR-Corporation/ncr-bsp-hmac/go/sign"
)

func main() {
	url := "https://api.ncr.com/security/roles?roleNamePattern=*&pageNumber=0&pageSize=10"
	req, _ := http.NewRequest("GET", url, strings.NewReader(""))
	req.Header.Add("Date", time.Now().UTC().Format(http.TimeFormat))
	req.Header.Add("nep-organization", "")
	req.Header.Add("Content-Type", "application/json")

	// Replace the empty string with your shared key
	sharedKey := ""
	// Replace the empty string with your secret key
	secretKey := ""
	s, _ := sign.NewAccessKeyHTTPSigner(sharedKey, secretKey)
	req, err := s.Sign(req)
	if err != nil {
	}

	res, err := http.DefaultClient.Do(req)
	if err != nil {
		log.Fatal(err)
	}
	defer res.Body.Close()
	b, err := ioutil.ReadAll(res.Body)
	if err != nil {
		log.Fatal(err)
	}

	var prettyJson bytes.Buffer
	error := json.Indent(&prettyJson, b, "", "\t")
	if error != nil {
		log.Println("JSON parse error: ", error)

		return
	}

	log.Println(string(prettyJson.Bytes()))
}
