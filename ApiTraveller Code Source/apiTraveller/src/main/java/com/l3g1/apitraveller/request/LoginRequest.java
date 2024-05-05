package com.l3g1.apitraveller.request;

import javax.validation.constraints.NotBlank;
// Class representing a login request
public class LoginRequest {

    // Annotated with @NotBlank to ensure that the username is not blank
    @NotBlank
    private String username;

    // Annotated with @NotBlank to ensure that the password is not blank
    @NotBlank
    private String password;

    // Getter method for retrieving the username
    public String getUsername() {
        return username;
    }

    // Setter method for setting the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method for retrieving the password
    public String getPassword() {
        return password;
    }

    // Setter method for setting the password
    public void setPassword(String password) {
        this.password = password;
    }
}