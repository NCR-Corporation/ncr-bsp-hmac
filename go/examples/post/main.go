package main

import (
	"bytes"
	"encoding/json"
	"io/ioutil"
	"log"
	"net/http"
	"time"

	"github.com/NCR-Corporation/ncr-bsp-hmac/go/sign"
)

type searchCriteria struct {
	SortCriteria []*siteCriteriaSort `json:"sort,omitempty"`
}
type siteCriteriaSort struct {
	Column    string `json:"column,omitempty"`
	Direction string `json:"direction,omitempty"`
}

func main() {
	url := "https://api.ncr.com/security/authorization"
	criteria := &searchCriteria{
		SortCriteria: []*siteCriteriaSort{
			{Column: "siteName", Direction: "asc"},
		},
	}
	b, err := json.Marshal(criteria)
	if err != nil {
		log.Fatal(err)
	}
	req, _ := http.NewRequest("POST", url, bytes.NewReader(b))
	req.Header.Add("Date", time.Now().UTC().Format(http.TimeFormat))
	req.Header.Add("nep-organization", "")
	req.Header.Add("Content-Type", "application/json")

	// Replace the empty string with your shared key
	sharedKey := ""
	// Replace the empty string with your secret key
	secretKey := ""
	s, _ := sign.NewAccessKeyHTTPSigner(sharedKey, secretKey)
	req, err = s.Sign(req)
	if err != nil {
	}

	res, err := http.DefaultClient.Do(req)
	if err != nil {
		log.Fatal(err)
	}
	defer res.Body.Close()
	b, err = ioutil.ReadAll(res.Body)
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
