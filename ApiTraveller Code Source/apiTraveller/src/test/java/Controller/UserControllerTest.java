package Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.l3g1.apitraveller.controller.UserController;

import com.l3g1.apitraveller.jwt.AuthTokenFilter;
import com.l3g1.apitraveller.jwt.JwtUtils;
import com.l3g1.apitraveller.model.User;
import com.l3g1.apitraveller.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import javax.servlet.http.HttpServletRequest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    // Test methode for  UserController

    HttpServletRequest request = mock(HttpServletRequest.class);
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserController userController =Mockito.mock(UserController.class);
    AuthTokenFilter authTokenFilter =Mockito.mock(AuthTokenFilter.class);
    JwtUtils jwtUtils = Mockito.mock(JwtUtils.class);

    @DisplayName("putDestSuggest")
    @Test
    public void putDestSuggest(){

    when(authTokenFilter.parseJwt(request)).thenReturn("mockedToken");
    when(jwtUtils.getUserNameFromJwtToken("mockedToken")).thenReturn("mockedUser");


    User user = new User();
    when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));


    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode listDestSuggestion = objectMapper.createObjectNode();
        listDestSuggestion.put("key", "value");


        userController.putDestSuggest(listDestSuggestion, request);
    }

    @DisplayName("putTripSuggest")
    @Test
    public void putTripSuggest(){

        when(authTokenFilter.parseJwt(request)).thenReturn("mockedToken");
        when(jwtUtils.getUserNameFromJwtToken("mockedToken")).thenReturn("mockedUser");


        User user = new User();
        when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));


        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode listDestSuggestion = objectMapper.createObjectNode();
        listDestSuggestion.put("key", "value");


        userController.putTripSuggest(listDestSuggestion, request);

    }
    @DisplayName("getSuggestionHistory")
    @Test
    public void getSuggestionHistory(){
        when(authTokenFilter.parseJwt(request)).thenReturn("mockedToken");
        when(jwtUtils.getUserNameFromJwtToken("mockedToken")).thenReturn("mockedUser");

        User user = new User();
        when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode listDestSuggestion = objectMapper.createObjectNode();
        listDestSuggestion.put("key", "value");
        List<ObjectNode> testResult =new ArrayList<>();
        testResult.add(listDestSuggestion);
        when(userController.getSuggestionHistory(request)).thenReturn(testResult);
        List<ObjectNode>suggestionHistory = userController.getSuggestionHistory(request);
        assertEquals(testResult,suggestionHistory);

    }

    @DisplayName("getTripSuggestionHistory")
    @Test
    public void getTripSuggestionHistory(){
        when(authTokenFilter.parseJwt(request)).thenReturn("mockedToken");
        when(jwtUtils.getUserNameFromJwtToken("mockedToken")).thenReturn("mockedUser");

        User user = new User();
        when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode listDestSuggestion = objectMapper.createObjectNode();
        listDestSuggestion.put("key", "value");
        List<ObjectNode> testResult =new ArrayList<>();
        testResult.add(listDestSuggestion);
        when(userController.getTripSuggestionHistory(request)).thenReturn(testResult);
        List<ObjectNode>suggestionHistory = userController.getTripSuggestionHistory(request);
        assertEquals(testResult,suggestionHistory);

    }

    @DisplayName("deleteSuggestionInHistory_Succes")
    @Test
    public void deleteSuggestionInHistorySuccess(){
        when(authTokenFilter.parseJwt(request)).thenReturn("mockedToken");
        when(jwtUtils.getUserNameFromJwtToken("mockedToken")).thenReturn("mockedUser");
        int suggestion = 1;

        User user = new User();
        when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));
        ResponseEntity<String> response = new ResponseEntity<>("The suggestion was deleted from the history", HttpStatus.OK);
        when(userController.deleteSuggestionInHistory(suggestion,request)).thenReturn(response);
        ResponseEntity<String>resultTest = userController.deleteSuggestionInHistory(suggestion,request);
        assertEquals(HttpStatus.OK, resultTest .getStatusCode());
        assertEquals("The suggestion was deleted from the history", resultTest.getBody());
    }
    @DisplayName("deleteSuggestionInHistory_deleteSuggestionInHistoryFaillure")
    @Test
    public void deleteSuggestionInHistoryFaillureInvalidsuggestionNumber(){
        when(authTokenFilter.parseJwt(request)).thenReturn("mockedToken");
        when(jwtUtils.getUserNameFromJwtToken("mockedToken")).thenReturn("mockedUser");
        int suggestion = 1;

        User user = new User();
        when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));
        ResponseEntity<String> response = new ResponseEntity<>("Invalid suggestion number", HttpStatus.BAD_REQUEST);
        when(userController.deleteSuggestionInHistory(suggestion,request)).thenReturn(response);
        ResponseEntity<String>resultTest = userController.deleteSuggestionInHistory(suggestion,request);
        assertEquals(HttpStatus.BAD_REQUEST, resultTest .getStatusCode());
        assertEquals("Invalid suggestion number", resultTest.getBody());
    }

     @DisplayName("deleteSuggestionInHistory_User not found")
     @Test
     public void deleteSuggestionInHistoryFaillureInvalidUserNotFound(){
         when(authTokenFilter.parseJwt(request)).thenReturn("mockedToken");
         when(jwtUtils.getUserNameFromJwtToken("mockedToken")).thenReturn("mockedUser");
         int suggestion = 1;

         User user = new User();
         when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));
         ResponseEntity<String> response = new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
         when(userController.deleteSuggestionInHistory(suggestion,request)).thenReturn(response);
         ResponseEntity<String>resultTest = userController.deleteSuggestionInHistory(suggestion,request);
         assertEquals(HttpStatus.BAD_REQUEST, resultTest .getStatusCode());
         assertEquals("User not found", resultTest.getBody());

     }
     @DisplayName("deleteSuggestionInHistory_Failed to delete suggestion")
     @Test
     public void deleteSuggestionInHistoryFaillureInvalidSuggestion(){

            when(authTokenFilter.parseJwt(request)).thenReturn("mockedToken");
            when(jwtUtils.getUserNameFromJwtToken("mockedToken")).thenReturn("mockedUser");
            int suggestion = 1;
            User user = new User();
            when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));
            ResponseEntity<String> response = new ResponseEntity<>("Failed to delete suggestion", HttpStatus.BAD_REQUEST);
            when(userController.deleteSuggestionInHistory(suggestion,request)).thenReturn(response);
            ResponseEntity<String>resultTest = userController.deleteSuggestionInHistory(suggestion,request);
            assertEquals(HttpStatus.BAD_REQUEST, resultTest .getStatusCode());
            assertEquals("Failed to delete suggestion", resultTest.getBody());


        }


    @DisplayName("deleteTripSuggestionInHistory_Succes")
    @Test
    public void deleteTripSuggestionInHistorySuccess(){
        when(authTokenFilter.parseJwt(request)).thenReturn("mockedToken");
        when(jwtUtils.getUserNameFromJwtToken("mockedToken")).thenReturn("mockedUser");
        int suggestion = 1;

        User user = new User();
        when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));
        ResponseEntity<String> response = new ResponseEntity<>("The suggestion was deleted from the history", HttpStatus.OK);
        when(userController.deleteTripSuggestionInHistory(suggestion,request)).thenReturn(response);
        ResponseEntity<String>resultTest = userController.deleteTripSuggestionInHistory(suggestion,request);
        assertEquals(HttpStatus.OK, resultTest .getStatusCode());
        assertEquals("The suggestion was deleted from the history", resultTest.getBody());
    }
    @DisplayName("deleteTripSuggestionInHistory_deleteSuggestionInHistoryFaillure")
    @Test
    public void deleteTripSuggestionInHistoryFaillureInvalidsuggestionNumber(){
        when(authTokenFilter.parseJwt(request)).thenReturn("mockedToken");
        when(jwtUtils.getUserNameFromJwtToken("mockedToken")).thenReturn("mockedUser");
        int suggestion = 1;

        User user = new User();
        when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));
        ResponseEntity<String> response = new ResponseEntity<>("Invalid suggestion number", HttpStatus.BAD_REQUEST);
        when(userController.deleteTripSuggestionInHistory(suggestion,request)).thenReturn(response);
        ResponseEntity<String>resultTest = userController.deleteTripSuggestionInHistory(suggestion,request);
        assertEquals(HttpStatus.BAD_REQUEST, resultTest .getStatusCode());
        assertEquals("Invalid suggestion number", resultTest.getBody());
    }

    @DisplayName("deleteTripSuggestionInHistory_User not found")
    @Test
    public void deleteTripSuggestionInHistoryFaillureInvalidUserNotFound(){
        when(authTokenFilter.parseJwt(request)).thenReturn("mockedToken");
        when(jwtUtils.getUserNameFromJwtToken("mockedToken")).thenReturn("mockedUser");
        int suggestion = 1;

        User user = new User();
        when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));
        ResponseEntity<String> response = new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        when(userController.deleteTripSuggestionInHistory(suggestion,request)).thenReturn(response);
        ResponseEntity<String>resultTest = userController.deleteTripSuggestionInHistory(suggestion,request);
        assertEquals(HttpStatus.BAD_REQUEST, resultTest .getStatusCode());
        assertEquals("User not found", resultTest.getBody());

    }
    @DisplayName("deleteTripSuggestionInHistory_Failed to delete suggestion")
    @Test
    public void deleteTripSuggestionInHistoryFaillureInvalidSuggestion(){

        when(authTokenFilter.parseJwt(request)).thenReturn("mockedToken");
        when(jwtUtils.getUserNameFromJwtToken("mockedToken")).thenReturn("mockedUser");
        int suggestion = 1;
        User user = new User();
        when(userRepository.findByUsername("mockedUser")).thenReturn(Optional.of(user));
        ResponseEntity<String> response = new ResponseEntity<>("Failed to delete suggestion", HttpStatus.BAD_REQUEST);
        when(userController.deleteTripSuggestionInHistory(suggestion,request)).thenReturn(response);
        ResponseEntity<String>resultTest = userController.deleteTripSuggestionInHistory(suggestion,request);
        assertEquals(HttpStatus.BAD_REQUEST, resultTest .getStatusCode());
        assertEquals("Failed to delete suggestion", resultTest.getBody());


    }






}

