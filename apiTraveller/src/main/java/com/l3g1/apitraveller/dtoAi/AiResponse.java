package com.l3g1.apitraveller.dtoAi;

import java.util.List;

import com.l3g1.apitraveller.model.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiResponse {
    List<Choice> choices; // List of choices provided in the AI response

    // Inner class representing a choice
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choice {
        private int index; // Index of the choice
        private Message message; // Message associated with the choice
    }
}
