package main

import (
	"testing"
)

func TestGet(t *testing.T){

    got := post()

    want := 200 

    if got != want {
        t.Errorf("got %q, wanted %q", got, want)
    }
}