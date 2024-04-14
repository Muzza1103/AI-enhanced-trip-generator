package com.l3g1.apitraveller.dtoAi;

import java.util.ArrayList;
import java.util.List;

import com.l3g1.apitraveller.model.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AiRequest {

    private String model; // The AI model to be used for processing the request
    private List<Message> messages; // List of messages included in the AI request

    // Default constructor
    public AiRequest() {
        this.messages = new ArrayList<>(); // Initialize messages list
    }

    // Constructor with parameters for chatGPT Model 3.5
    public AiRequest(String model, String prompt) {
        this.model = model; // Set the AI model
        this.messages = new ArrayList<>(); // Initialize messages list
        this.messages.add(new Message("user", prompt)); // Add a new user message to the list
    }

}
