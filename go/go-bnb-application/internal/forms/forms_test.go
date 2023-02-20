package forms

import (
	"net/http/httptest"
	"net/url"
	"testing"
)

func TestForm_New(t *testing.T) {
	var data url.Values
	New(data)
}

func TestForm_Valid(t *testing.T) {
	r := httptest.NewRequest("POST", "/some-url", nil)
	form := New(r.PostForm)

	isValid := form.Valid()
	if !isValid {
		t.Error("got invalid when form should be valid")
	}

	form.Errors.Add("field", "Invalid field")
	isValid = form.Valid()
	if isValid {
		t.Error("got valid when form should be invalid")
	}
}

func TestForm_Has(t *testing.T) {
	postedData := url.Values{}
	form := New(postedData)

	hasValue := form.Has("value")
	if hasValue {
		t.Error("form should not have value")
	}

	postedData.Add("value", "something")

	form = New(postedData)
	hasValue = form.Has("value")
	if !hasValue {
		t.Error("form should have value")
	}
}

func TestForm_Required(t *testing.T) {
	postedData := url.Values{}
	form := New(postedData)

	form.Required("a", "b", "c")
	if form.Valid() {
		t.Error("form shows valid when required field are missing")
	}

	postedData.Add("a", "a")
	postedData.Add("b", "b")
	postedData.Add("c", "c")

	form = New(postedData)
	form.Required("a", "b", "c")
	if !form.Valid() {
		t.Error("form shows that it does not have required fields when it does")
	}
}

func TestForm_MinLength(t *testing.T) {
	postedData := url.Values{}
	form := New(postedData)

	form.MinLength("missingField", 10)

	if form.Valid() {
		t.Error("Form shows min length for non-existent field")
	}

	isError := form.Errors.Get("missingField")
	if isError == "" {
		t.Error("should have an error but did not get one")
	}

	postedData.Add("some_field", "some value")

	form = New(postedData)
	form.MinLength("some_field", 100)
	if form.Valid() {
		t.Error("Form shows min length is met when data is shorter")
	}

	postedData = url.Values{}
	postedData.Add("another", "12345")
	form = New(postedData)

	form.MinLength("another", 5)
	if !form.Valid() {
		t.Error("Form shows min length does not match when it does")
	}

	isError = form.Errors.Get("another")
	if isError != "" {
		t.Error("should not have an error but got one")
	}
}

func TestForm_ValidateEmail(t *testing.T) {
	postedData := url.Values{}
	form := New(postedData)

	form.ValidateEmail("x")
	if form.Valid() {
		t.Error("Form shows valid email for non-existent field")
	}

	postedData.Add("email", "me@here.com")
	form = New(postedData)

	form.ValidateEmail("email")
	if !form.Valid() {
		t.Error("Form shows invalid email but email is valid")
	}

	postedData = url.Values{}
	postedData.Add("email", "xxx.com")
	form = New(postedData)

	form.ValidateEmail("email")
	if form.Valid() {
		t.Error("Form shows valid email but email is invalid")
	}
}
