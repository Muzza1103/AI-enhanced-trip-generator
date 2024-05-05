package com.l3g1.apitraveller.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.l3g1.apitraveller.model.*;
import com.l3g1.apitraveller.model.enumeration.*;
import com.l3g1.apitraveller.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tripSuggestion")
public class TripSuggestionController {
    @Autowired
    private TripService tripService;
    // GET endpoint to retrieve trip suggestions based on survey criteria with the data from the database without using the AI
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
        TripSurvey survey = new TripSurvey(localisation, climate, landscape, temperature, activityType, startingDate, endingDate, roadTrip, budget);
        List<TripSuggestion> listTripSuggestion;
        try {
            listTripSuggestion = tripService.getSuggestion(survey);
        } catch (IllegalArgumentException e) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("error", e.getMessage());
            return errorNode;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        if (!listTripSuggestion.isEmpty()) {
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
                cityNode.put("Temperature", String.valueOf(suggestion.getCity().getTemperatureList()));
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


    // GET endpoint to retrieve trip suggestions with AI based on survey criteria with additional processing
    @GetMapping("/getTripSuggestAI")
    @CrossOrigin
    public ObjectNode getTripSuggestionAI(
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
                cityNode.put("Temperature", String.valueOf(suggestion.getCity().getTemperatureList()));
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
            errorNode.put("error", "The API was unable to make a suggestion for this choice of criteria. Check that there is no contradictory element in the survey");
            return errorNode;
        }
    }


    // GET endpoint to retrieve suggestions with AI and with the usage of a cache. Create a new suggestion which has not been given yet at least in the last 30 minutes.
    @GetMapping("/getNewTripSuggestionAI")
    @CrossOrigin
    public ObjectNode getNewTripSuggestionAI(
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
                cityNode.put("Temperature", String.valueOf(suggestion.getCity().getTemperatureList()));
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
            errorNode.put("error", "The API was unable to make a new suggestion for this choice of criteria. It may be that no more different suggestions can be made for this set of criteria.");
            return errorNode;
        }
    }
}