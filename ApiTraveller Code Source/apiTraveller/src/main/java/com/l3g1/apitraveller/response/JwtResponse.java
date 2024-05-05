package com.l3g1.apitraveller.response;

// Represents a response object for JWT (JSON Web Token) authentication.
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;

    // Constructs a JwtResponse object.
    public JwtResponse(String accessToken, Long id, String username, String email) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Returns the JWT access token.
    public String getAccessToken() {
        return token;
    }

    // Sets the JWT access token.
    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    // Sets the JWT access token.
    public String getTokenType() {
        return type;
    }

    // Sets the type of token.
    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    // Returns the user ID.
    public Long getId() {
        return id;
    }

    // Sets the user ID.
    public void setId(Long id) {
        this.id = id;
    }

    // Returns the user email.
    public String getEmail() {
        return email;
    }

    // Sets the user email.
    public void setEmail(String email) {
        this.email = email;
    }

    // Returns the username.
    public String getUsername() {
        return username;
    }

    // Sets the username.
    public void setUsername(String username) {
        this.username = username;
    }
}