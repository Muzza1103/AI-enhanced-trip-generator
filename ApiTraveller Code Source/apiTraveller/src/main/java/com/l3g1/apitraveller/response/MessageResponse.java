package com.l3g1.apitraveller.response;

// Represents a response object for returning messages.
public class MessageResponse {
    private String message;

    // Constructs a MessageResponse object with the provided message.
    public MessageResponse(String message) {
        this.message = message;
    }

    // Returns the message.
    public String getMessage() {
        return message;
    }

    // Sets the message.
    public void setMessage(String message) {
        this.message = message;
    }
}
