package com.l3g1.apitraveller.controller;

import com.l3g1.apitraveller.model.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.l3g1.apitraveller.service.DestinationSuggestionService;


@RestController
@RequestMapping("/suggestion")
public class DestinationSuggestionController {

	@Autowired
	private DestinationSuggestionService DestinationSuggestionService;

	// GET endpoint to retrieve suggestions based on survey criteria with the data from the database without using the AI
	@GetMapping("/getSuggest")
	public String getSuggestion(@RequestBody Survey survey) {
		Iterable<Suggestion> suggestions = new ArrayList<>();
		try {
			suggestions = DestinationSuggestionService.getSuggestion(survey);
		}catch (IllegalArgumentException e){
			return e.getMessage();
		}
		StringBuilder resultBuilder = new StringBuilder();

		boolean isEmpty = true;

		// Iterate over suggestions and build result string
		for (Suggestion suggestion : suggestions) {
			isEmpty = false;
			// Append country details
			resultBuilder.append("Country: ").append(suggestion.getCountry().getCountryName()).append("\n");
			resultBuilder.append("Description: ").append(suggestion.getCountry().getDescription()).append("\n");
			resultBuilder.append("List of the Climates:");
			List<Climate> climateList = suggestion.getCountry().getClimateList();
			for (Climate climate : climateList) {
				resultBuilder.append(" ").append(climate);
			}
			resultBuilder.append("\n\n");

			// Append city details
			resultBuilder.append("City: ").append(suggestion.getCity().getCityName()).append("\n");
			resultBuilder.append("Description: ").append(suggestion.getCity().getDescription()).append("\n");
			resultBuilder.append("Climate: ").append(suggestion.getCity().getClimate()).append("\n");
			resultBuilder.append("Temperature: ").append(suggestion.getCity().getTemperature()).append("\n");
			resultBuilder.append("Transport: ").append(suggestion.getCity().getTransport()).append("\n\n");

			// Append activities details
			resultBuilder.append("Activities:\n");
			for (int i = 0; i < suggestion.getActivityList().size(); i++) {
				resultBuilder.append("  Activity ").append(i + 1).append(":\n");
				resultBuilder.append("    Name: ").append(suggestion.getActivityList().get(i).getActivityName()).append("\n");
				resultBuilder.append("    Type of the activity: ").append(suggestion.getActivityList().get(i).getActivityType()).append("\n");
				resultBuilder.append("    Description: ").append(suggestion.getActivityList().get(i).getDescription()).append("\n");
				resultBuilder.append("    Price: ").append(suggestion.getActivityList().get(i).getPrice()).append("\n\n");
			}
			resultBuilder.append("\n\n\n");
		}

		if (isEmpty) {
			return "The database does not contain the necessary data for your query.";
		}else{
			return resultBuilder.toString();
		}
	}

	// GET endpoint to retrieve suggestions with AI based on survey criteria with additional processing
	@GetMapping("/getSuggestAI")
	public String getSuggestionIA(@RequestBody Survey survey) {
		Instant start = Instant.now();
		Iterable<Suggestion> suggestions = new ArrayList<>();

		try{
			suggestions = DestinationSuggestionService.getSuggestionAI(survey);
		}catch (IllegalArgumentException e){
			return e.getMessage();
		}

		StringBuilder resultBuilder = new StringBuilder();

		// Process suggestions based on survey criteria
		if (survey.getLocalisation().equals("ALL")||Continent.isValidValue(survey.getLocalisation().toUpperCase())) {
			// Process all countries
			for (Suggestion suggestion : suggestions) {
				// Append country details
				resultBuilder.append("Country: ").append(suggestion.getCountry().getCountryName()).append("\n");
				resultBuilder.append("Continent: ").append(suggestion.getCountry().getContinent()).append("\n");
				resultBuilder.append("Description: ").append(suggestion.getCountry().getDescription()).append("\n");
				resultBuilder.append("List of the Climates:");
				List<Climate> climateList = suggestion.getCountry().getClimateList();
				for (Climate climate : climateList) {
					resultBuilder.append(" ").append(climate);
				}
				resultBuilder.append("\n\n");

				// Append city details
				resultBuilder.append("City: ").append(suggestion.getCity().getCityName()).append("\n");
				resultBuilder.append("Description: ").append(suggestion.getCity().getDescription()).append("\n");
				resultBuilder.append("Climate: ").append(suggestion.getCity().getClimate()).append("\n");
				resultBuilder.append("Temperature: ").append(suggestion.getCity().getTemperature()).append("\n");
				resultBuilder.append("Transport: ").append(suggestion.getCity().getTransport()).append("\n\n");

				// Append activities details
				resultBuilder.append("Activities:\n");
				for (int i = 0; i < suggestion.getActivityList().size(); i++) {
					resultBuilder.append("  Activity ").append(i + 1).append(":\n");
					resultBuilder.append("    Name: ").append(suggestion.getActivityList().get(i).getActivityName()).append("\n");
					resultBuilder.append("    Type of the activity: ").append(suggestion.getActivityList().get(i).getActivityType()).append("\n");
					resultBuilder.append("    Description: ").append(suggestion.getActivityList().get(i).getDescription()).append("\n");
					resultBuilder.append("    Price: ").append(suggestion.getActivityList().get(i).getPrice()).append("\n\n");
				}
				resultBuilder.append("\n\n\n");
			}
		} else {
			// Process specific country
			Suggestion suggest = suggestions.iterator().next();
			resultBuilder.append("Country: ").append(suggest.getCountry().getCountryName()).append("\n");
			resultBuilder.append("Continent: ").append(suggest.getCountry().getContinent()).append("\n");
			resultBuilder.append("Description: ").append(suggest.getCountry().getDescription()).append("\n");
			resultBuilder.append("List of the Climates:");
			List<Climate> climateList = suggest.getCountry().getClimateList();
			for (Climate climate : climateList) {
				resultBuilder.append(" ").append(climate);
			}
			resultBuilder.append("\n\n\n");

			// Process cities and activities for the specific country
			for (Suggestion suggestion : suggestions) {
				resultBuilder.append("City: ").append(suggestion.getCity().getCityName()).append("\n");
				resultBuilder.append("Description: ").append(suggestion.getCity().getDescription()).append("\n");
				resultBuilder.append("Climate: ").append(suggestion.getCity().getClimate()).append("\n");
				resultBuilder.append("Temperature: ").append(suggestion.getCity().getTemperature()).append("\n");
				resultBuilder.append("Transport: ").append(suggestion.getCity().getTransport()).append("\n\n");

				// Append activities details
				resultBuilder.append("Activities:\n");
				for (int i = 0; i < suggestion.getActivityList().size(); i++) {
					resultBuilder.append("  Activity ").append(i + 1).append(":\n");
					resultBuilder.append("    Name: ").append(suggestion.getActivityList().get(i).getActivityName()).append("\n");
					resultBuilder.append("    Type of the activity: ").append(suggestion.getActivityList().get(i).getActivityType()).append("\n");
					resultBuilder.append("    Description: ").append(suggestion.getActivityList().get(i).getDescription()).append("\n");
					resultBuilder.append("    Price: ").append(suggestion.getActivityList().get(i).getPrice()).append("\n\n");
				}
				resultBuilder.append("\n\n");
			}
		}

		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
		long seconds = duration.toSeconds();

		resultBuilder.insert(0, "Execution time: " + seconds + " seconds\n\n");

		return resultBuilder.toString();
	}

}

