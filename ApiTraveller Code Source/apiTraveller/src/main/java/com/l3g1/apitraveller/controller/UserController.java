package com.l3g1.apitraveller.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.l3g1.apitraveller.jwt.AuthTokenFilter;
import com.l3g1.apitraveller.jwt.JwtUtils;
import com.l3g1.apitraveller.model.User;
import com.l3g1.apitraveller.repository.UserRepository;
import com.l3g1.apitraveller.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/registerSuggestion")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthTokenFilter authTokenFilter;

    @Autowired
    private JwtUtils jwtUtils;

    // PostMapping annotation to handle POST requests for putting destination suggestions
    @PostMapping("/putDestSuggest")
    // CrossOrigin annotation to allow cross-origin requests
    @CrossOrigin
    public ResponseEntity<?> putDestSuggest(@RequestBody ObjectNode listDestSuggestion, HttpServletRequest request) {
        String token = authTokenFilter.parseJwt(request);
        String userName = jwtUtils.getUserNameFromJwtToken(token);
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String listDestSuggestionString;
            try {
                listDestSuggestionString = objectMapper.writeValueAsString(listDestSuggestion);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            List<String> suggestions = user.getSuggestions();
            if (!suggestions.contains(listDestSuggestionString)) {
                user.addDestSuggestions(listDestSuggestionString);
                userRepository.save(user);
                return ResponseEntity.ok(new MessageResponse("The suggestion was successfully saved!"));
            } else {
                return ResponseEntity.ok(new MessageResponse("The suggestion was not saved, as it already exists."));
            }
        }
        return ResponseEntity.ok(new MessageResponse("The suggestion was not saved, verify the authorization!"));
    }

    // PostMapping annotation to handle POST requests for putting trip suggestions
    @PostMapping("/putTripSuggest")
    // CrossOrigin annotation to allow cross-origin requests
    @CrossOrigin
    public ResponseEntity<?> putTripSuggest(@RequestBody ObjectNode listTripSuggestion, HttpServletRequest request) {
        String token = authTokenFilter.parseJwt(request);
        String userName = jwtUtils.getUserNameFromJwtToken(token);
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String listTripSuggestionString;
            try {
                listTripSuggestionString = objectMapper.writeValueAsString(listTripSuggestion);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            List<String> tripSuggestions = user.getTripSuggestions();
            if (!tripSuggestions.contains(listTripSuggestionString)) {
                user.addTripSuggestions(listTripSuggestionString);
                userRepository.save(user);
                return ResponseEntity.ok(new MessageResponse("The suggestion was successfully saved!"));
            } else {
                return ResponseEntity.ok(new MessageResponse("The suggestion was not saved, as it already exists."));
            }
        }
        return ResponseEntity.ok(new MessageResponse("The suggestion was not saved, verify the authorization!"));
    }


    // GetMapping annotation to handle GET requests for getting destination suggestion history
    @GetMapping("/getSuggestionHistory")
    // CrossOrigin annotation to allow cross-origin requests
    @CrossOrigin
    public List<ObjectNode> getSuggestionHistory(HttpServletRequest request) {
        String token = authTokenFilter.parseJwt(request);
        String userName = jwtUtils.getUserNameFromJwtToken(token);
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        List<String> listDestSuggestion = optionalUser.map(User::getSuggestions).orElse(null);

        if (listDestSuggestion != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<ObjectNode> objectNodes = new ArrayList<>();

            for (String suggestion : listDestSuggestion) {
                try {
                    JsonNode jsonNode = objectMapper.readTree(suggestion);
                    if (jsonNode.isObject()) {
                        ObjectNode objectNode = (ObjectNode) jsonNode;
                        objectNodes.add(objectNode);
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

            return objectNodes;
        } else {
            return Collections.emptyList();
        }
    }

    // GetMapping annotation to handle GET requests for getting trip suggestion history
    @GetMapping("/getTripSuggestionHistory")
    // CrossOrigin annotation to allow cross-origin requests
    @CrossOrigin
    public List<ObjectNode> getTripSuggestionHistory(HttpServletRequest request) {
        String token = authTokenFilter.parseJwt(request);
        String userName = jwtUtils.getUserNameFromJwtToken(token);
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        List<String> listTripSuggestion = optionalUser.map(User::getTripSuggestions).orElse(null);

        if (listTripSuggestion != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<ObjectNode> objectNodes = new ArrayList<>();

            for (String suggestion : listTripSuggestion) {
                try {
                    JsonNode jsonNode = objectMapper.readTree(suggestion);
                    if (jsonNode.isObject()) {
                        ObjectNode objectNode = (ObjectNode) jsonNode;
                        objectNodes.add(objectNode);
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

            return objectNodes;
        } else {
            return Collections.emptyList();
        }
    }

    // DeleteMapping annotation to handle DELETE requests for deleting a destination suggestion from history
    @DeleteMapping("/deleteSuggestionInHistory/{num_Suggestion}")
    // CrossOrigin annotation to allow cross-origin requests
    @CrossOrigin
    public ResponseEntity<String> deleteSuggestionInHistory(@PathVariable int num_Suggestion, HttpServletRequest request) {
        try {
            String token = authTokenFilter.parseJwt(request);
            String userName = jwtUtils.getUserNameFromJwtToken(token);
            Optional<User> optionalUser = userRepository.findByUsername(userName);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<String> listSuggestion = user.getSuggestions();

                if (listSuggestion != null && num_Suggestion >= 0 && num_Suggestion < listSuggestion.size()) {
                    listSuggestion.remove(num_Suggestion);
                    user.setSuggestions(listSuggestion);
                    userRepository.save(user);
                    return ResponseEntity.ok("The suggestion was deleted from the history");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid suggestion number");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete suggestion: " + e.getMessage());
        }
    }

    // DeleteMapping annotation to handle DELETE requests for deleting a trip suggestion from history
    @DeleteMapping("/deleteTripSuggestionInHistory/{num_Suggestion}")
    // CrossOrigin annotation to allow cross-origin requests
    @CrossOrigin
    public ResponseEntity<String> deleteTripSuggestionInHistory(@PathVariable int num_Suggestion, HttpServletRequest request) {
        try {
            String token = authTokenFilter.parseJwt(request);
            String userName = jwtUtils.getUserNameFromJwtToken(token);
            Optional<User> optionalUser = userRepository.findByUsername(userName);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<String> listTripSuggestion = user.getTripSuggestions();

                if (listTripSuggestion != null && num_Suggestion >= 0 && num_Suggestion < listTripSuggestion.size()) {
                    listTripSuggestion.remove(num_Suggestion);
                    user.setTripSuggestions(listTripSuggestion);
                    userRepository.save(user);
                    return ResponseEntity.ok("The suggestion was deleted from the history");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid suggestion number");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete suggestion: " + e.getMessage());
        }
    }

}
