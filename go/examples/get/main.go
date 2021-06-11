package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"strings"
	"time"
	"os"

	"github.com/NCR-Corporation/ncr-bsp-hmac/go/sign"
)

// $sharedKey = "2129ffe8f58743758056edc814d67f4e"
// $secretKey = "0a92503074b64a1faf1d3cf16b2d0638"
// $organization = "8133687b23e84b2ea8dd267d8a519e89"
func get() int {
	url := "https://api.ncr.com/security/roles?roleNamePattern=*&pageNumber=0&pageSize=10"
	req, _ := http.NewRequest("GET", url, strings.NewReader(""))

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
	fmt.Printf("%s\n", string(b))
	return int(res.StatusCode)
}

func main() {
	get()
}
