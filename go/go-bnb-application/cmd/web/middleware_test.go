package main

import (
	"net/http"
	"testing"
)

func TestNoSurve(t *testing.T) {
	var myH myHandler

	h := NoSurf(&myH)

	switch h.(type) {
	case http.Handler:
		// do nothing
	default:
		t.Error("type is not http.Handler")
	}
}

func TestSessionLoad(t *testing.T) {
	var myH myHandler

	h := SessionLoad(&myH)

	switch h.(type) {
	case http.Handler:
		// do nothing
	default:
		t.Error("type is not http.Handler")
	}
}
