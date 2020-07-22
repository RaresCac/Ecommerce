package rares.web.ecommece.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CheckoutDTO {
    @NotBlank(message = "Must have a name")
    private String name;

    @NotBlank(message = "Must have a address")
    private String address;

    @NotBlank(message = "Must have a city")
    private String city;

    @NotBlank(message = "Must have a postcode")
    private String postcode;

    @NotBlank(message = "Must have a country")
    private String country;

    @NotBlank(message = "Must have a state")
    private String state;

    @NotBlank(message = "Must have an email")
    @Email(message = "Must be a well formed email address")
    private String email;

    public String getFullAddress(){
        return address + ", " + city + ", " + state + ", " + country + ", " + postcode;
    }

    public CheckoutDTO() {
    }

    public CheckoutDTO(@NotEmpty @NotNull String name, @NotEmpty @NotNull String address, @NotEmpty @NotNull String city, @NotEmpty @NotNull String postcode, @NotEmpty @NotNull String country, @NotEmpty @NotNull String state, @NotEmpty @NotNull String email) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
        this.state = state;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
