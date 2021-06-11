package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"time"
	"os"

	"github.com/NCR-Corporation/ncr-bsp-hmac/go/sign"
)

type searchCriteria struct {
	SortCriteria []*siteCriteriaSort `json:"sort,omitempty"`
}
type siteCriteriaSort struct {
	Column    string `json:"column,omitempty"`
	Direction string `json:"direction,omitempty"`
}

func post() int {
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
	// Replace the string with your organization.
	nepOrganization := "INSERT_ORGANIZATION";
	// Replace the empty string with your shared key
	sharedKey := "INSERT_SHARED_KEY"
	// Replace the empty string with your secret key
	secretKey := "INSERT_SECRET_KEY"

	// For tests, feel free to remove.
	if _, ok := os.LookupEnv("GITHUB_ACTION"); ok {
		nepOrganization = os.Getenv("ORGANIZATION");
		sharedKey = os.Getenv("SHARED_KEY");
		secretKey = os.Getenv("SECRET_KEY");
	}

	req.Header.Add("Date", time.Now().UTC().Format(http.TimeFormat))
	req.Header.Add("nep-organization", nepOrganization)
	req.Header.Add("Content-Type", "application/json")

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
	fmt.Printf("%s\n", b)
	return int(res.StatusCode)
}

func main() {
	post()
}
