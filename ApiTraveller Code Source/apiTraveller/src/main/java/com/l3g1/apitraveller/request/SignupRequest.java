package com.l3g1.apitraveller.request;

import javax.validation.constraints.*;
// Class representing a signup request
public class SignupRequest {

    // Annotated with @NotBlank to ensure that the username is not blank
    // Annotated with @Size to specify the allowed size range for the username
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    // Annotated with @NotBlank to ensure that the email is not blank
    // Annotated with @Size to specify the maximum size for the email
    // Annotated with @Email to ensure that the email format is valid
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    // Annotated with @NotBlank to ensure that the password is not blank
    // Annotated with @Size to specify the allowed size range for the password
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    // Getter method for retrieving the username
    public String getUsername() {
        return username;
    }

    // Setter method for setting the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method for retrieving the email
    public String getEmail() {
        return email;
    }

    // Setter method for setting the email
    public void setEmail(String email) {
        this.email = email;
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