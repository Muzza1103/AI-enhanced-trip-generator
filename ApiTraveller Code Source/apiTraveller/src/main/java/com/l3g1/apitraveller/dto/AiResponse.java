package com.l3g1.apitraveller.dto;

import java.util.List;

import com.l3g1.apitraveller.model.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiResponse {
    //Represents a response from an AI model.
    List<Choice> choices; // List of choices provided in the AI response

    // Inner class representing a choice
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    //Represents a choice within an AI response.
    public static class Choice {
        private int index; // Index of the choice
        private Message message; // Message associated with the choice
    }
}