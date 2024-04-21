package com.l3g1.apitraveller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.l3g1.apitraveller.model.*;
import com.l3g1.apitraveller.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;


import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/tripSuggestion")
public class TripSuggestionController {
    @Autowired
    private TripService tripService;
    // GET endpoint to retrieve trip suggestions based on survey criteria with the data from the database without using the AI
    @GetMapping("/getSuggest")
    public String getSuggestion(@RequestBody TripSurvey survey) {
        List<TripSuggestion> listTripSuggestion = tripService.getSuggestion(survey);
        StringBuilder resultBuilder = new StringBuilder();

        if(!listTripSuggestion.isEmpty()) {
            TripSuggestion suggest = listTripSuggestion.get(listTripSuggestion.size() - 1);

            resultBuilder.append("Country: ").append(suggest.getCountry().getCountryName()).append("\n");
            resultBuilder.append("Continent: ").append(suggest.getCountry().getContinent()).append("\n");
            resultBuilder.append("Description: ").append(suggest.getCountry().getDescription()).append("\n");
            resultBuilder.append("Departure date: ").append(suggest.getDepartureDate().toString()).append("\n");
            resultBuilder.append("Arrival date: ").append(suggest.getArrivalDate().toString()).append("\n\n");
            resultBuilder.append("Total Cost of Activities : ").append(suggest.getCost()).append("\n\n");

            for (TripSuggestion suggestion : listTripSuggestion) {
                resultBuilder.append("City: ").append(suggestion.getCity().getCityName()).append("\n");
                resultBuilder.append("Description: ").append(suggestion.getCity().getDescription()).append("\n");
                resultBuilder.append("Temperature: ").append(suggestion.getCity().getTemperature()).append("\n");
                resultBuilder.append("Transport: ").append(suggestion.getCity().getTransport()).append("\n\n");
                if (survey.isRoadTrip()) {
                    resultBuilder.append("Total Cost of Activities for this city : ").append(suggestion.getCostCity()).append("\n\n");
                }
                resultBuilder.append("Activity per day : ").append(suggestion.getDayActivity());
                resultBuilder.append("\n\n\n");
            }
            return resultBuilder.toString();
        }else {
            return "The database does not contain the necessary data for your query.";
        }
    }


    // GET endpoint to retrieve trip suggestions with AI based on survey criteria with additional processing
    @GetMapping("/getSuggestAI")
    @CrossOrigin
    public ObjectNode getSuggestionAI(
            @RequestParam String localisation,
            @RequestParam Climate climate,
            @RequestParam Landscape landscape,
            @RequestParam Temperature temperature,
            @RequestParam List<ActivityType> activityType,
            @RequestParam String startingDate,
            @RequestParam String endingDate,
            @RequestParam boolean roadTrip,
            @RequestParam int budget
    ) {
        TripSurvey survey = new TripSurvey(localisation,climate,landscape,temperature,activityType,startingDate,endingDate,roadTrip,budget);
        List<TripSuggestion> listTripSuggestion;
        try{
            listTripSuggestion = tripService.getSuggestionAI(survey);
        }catch (IllegalArgumentException e){
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("error", e.getMessage());
            return errorNode;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();

        if(!listTripSuggestion.isEmpty()) {
            TripSuggestion suggest = listTripSuggestion.get(listTripSuggestion.size() - 1);

            jsonNode.put("Country", suggest.getCountry().getCountryName());
            jsonNode.put("Continent", String.valueOf(suggest.getCountry().getContinent()));
            jsonNode.put("Description", suggest.getCountry().getDescription());
            jsonNode.put("Departure date", suggest.getDepartureDate().toString());
            jsonNode.put("Arrival date", suggest.getArrivalDate().toString());
            jsonNode.put("Total Cost of Activities", suggest.getCost());

            ArrayNode citiesArray = objectMapper.createArrayNode();
            for (TripSuggestion suggestion : listTripSuggestion) {
                ObjectNode cityNode = objectMapper.createObjectNode();
                cityNode.put("City", suggestion.getCity().getCityName());
                cityNode.put("Description", suggestion.getCity().getDescription());
                cityNode.put("Temperature", String.valueOf(suggestion.getCity().getTemperature()));
                cityNode.put("Transport", String.valueOf(suggestion.getCity().getTransport()));
                if (survey.isRoadTrip()) {
                    cityNode.put("Total Cost of Activities for this city", suggestion.getCostCity());
                }
                ArrayNode daysArray = objectMapper.createArrayNode();
                String[] days = suggestion.getDayActivity().split("Day-");
                for (int i = 1; i < days.length; i++) { // Start from 1 to skip the empty string before the first "Day"
                    String day = days[i].trim();
                    if (!day.isEmpty()) {
                        ObjectNode dayNode = objectMapper.createObjectNode();
                        ArrayNode activitiesArray = objectMapper.createArrayNode();
                        String[] activities = day.split("\n\n");

                        for (String activity : activities) {
                            String[] lines = activity.split("\n");
                            ObjectNode activityNode = objectMapper.createObjectNode();
                            for (String line : lines) {
                                String[] parts = line.split(":");
                                if (parts.length == 2) {
                                    String key = parts[0].trim();
                                    String value = parts[1].trim();
                                    activityNode.put(key, value);
                                }
                            }
                            activitiesArray.add(activityNode);
                        }
                        dayNode.set("activities", activitiesArray);
                        daysArray.add(dayNode);
                    }
                }
                cityNode.set("Days", daysArray);
                citiesArray.add(cityNode);
            }

            jsonNode.set("Cities", citiesArray);
            return jsonNode;
        } else {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("error", "The database does not contain the necessary data for your query.");
            return errorNode;

        }
    }

    // GET endpoint to retrieve suggestions with AI and with the usage of a cache
    @GetMapping("/getTripSuggestion")
    @CrossOrigin
    public ObjectNode getTripSuggestion(
            @RequestParam String localisation,
            @RequestParam Climate climate,
            @RequestParam Landscape landscape,
            @RequestParam Temperature temperature,
            @RequestParam List<ActivityType> activityType,
            @RequestParam String startingDate,
            @RequestParam String endingDate,
            @RequestParam boolean roadTrip,
            @RequestParam int budget
    ) {
        TripSurvey survey = new TripSurvey(localisation,climate,landscape,temperature,activityType,startingDate,endingDate,roadTrip,budget);
        List<TripSuggestion> listTripSuggestion;
        try{
            listTripSuggestion = tripService.getSuggestionAIWithCache(survey);
        }catch (IllegalArgumentException e){
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("error", e.getMessage());
            return errorNode;

        }
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();

        if(!listTripSuggestion.isEmpty()) {
            TripSuggestion suggest = listTripSuggestion.get(listTripSuggestion.size() - 1);

            jsonNode.put("Country", suggest.getCountry().getCountryName());
            jsonNode.put("Continent", String.valueOf(suggest.getCountry().getContinent()));
            jsonNode.put("Description", suggest.getCountry().getDescription());
            jsonNode.put("Departure date", suggest.getDepartureDate().toString());
            jsonNode.put("Arrival date", suggest.getArrivalDate().toString());
            jsonNode.put("Total Cost of Activities", suggest.getCost());

            ArrayNode citiesArray = objectMapper.createArrayNode();
            for (TripSuggestion suggestion : listTripSuggestion) {
                ObjectNode cityNode = objectMapper.createObjectNode();
                cityNode.put("City", suggestion.getCity().getCityName());
                cityNode.put("Description", suggestion.getCity().getDescription());
                cityNode.put("Temperature", String.valueOf(suggestion.getCity().getTemperature()));
                cityNode.put("Transport", String.valueOf(suggestion.getCity().getTransport()));
                if (survey.isRoadTrip()) {
                    cityNode.put("Total Cost of Activities for this city", suggestion.getCostCity());
                }
                ArrayNode daysArray = objectMapper.createArrayNode();
                String[] days = suggestion.getDayActivity().split("Day-");
                for (int i = 1; i < days.length; i++) { // Start from 1 to skip the empty string before the first "Day"
                    String day = days[i].trim();
                    if (!day.isEmpty()) {
                        ObjectNode dayNode = objectMapper.createObjectNode();
                        ArrayNode activitiesArray = objectMapper.createArrayNode();
                        String[] activities = day.split("\n\n");

                        for (String activity : activities) {
                            String[] lines = activity.split("\n");
                            ObjectNode activityNode = objectMapper.createObjectNode();
                            for (String line : lines) {
                                String[] parts = line.split(":");
                                if (parts.length == 2) {
                                    String key = parts[0].trim();
                                    String value = parts[1].trim();
                                    activityNode.put(key, value);
                                }
                            }
                            activitiesArray.add(activityNode);
                        }
                        dayNode.set("activities", activitiesArray);
                        daysArray.add(dayNode);
                    }
                }
                cityNode.set("Days", daysArray);
                citiesArray.add(cityNode);
            }

            jsonNode.set("Cities", citiesArray);
            return jsonNode;
        } else {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("error", "The database does not contain the necessary data for your query.");
            return errorNode;

        }
    }
}