package com.l3g1.apitraveller.controller;

import com.l3g1.apitraveller.model.*;
import com.l3g1.apitraveller.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getSuggestionAI(@RequestBody TripSurvey survey) {
        Instant start = Instant.now();
        List<TripSuggestion> listTripSuggestion;
        try{
            listTripSuggestion = tripService.getSuggestionAI(survey);
        }catch (IllegalArgumentException e){
            return e.getMessage();
        }
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

            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);
            long seconds = duration.toSeconds();
            resultBuilder.insert(0, "Execution time: " + seconds + " seconds\n\n");
            return resultBuilder.toString();
        }else {
            return "The database does not contain the necessary data for your query.";
        }
    }


}