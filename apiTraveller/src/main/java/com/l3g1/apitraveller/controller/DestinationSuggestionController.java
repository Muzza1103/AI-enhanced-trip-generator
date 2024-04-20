package com.l3g1.apitraveller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.l3g1.apitraveller.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;


import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.l3g1.apitraveller.service.DestinationSuggestionService;


@RestController
@RequestMapping("/suggestion")
@CrossOrigin
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
	public ObjectNode getSuggestionAI(
			@RequestParam String localisation,
			@RequestParam Climate climate,
			@RequestParam Landscape landscape,
			@RequestParam Temperature temperature,
			@RequestParam List<ActivityType> activityType
	) {
		Survey survey = new Survey(localisation,climate,landscape,temperature,activityType);
		Iterable<Suggestion> suggestions = new ArrayList<>();

		try{
			suggestions = DestinationSuggestionService.getSuggestionAI(survey);
		}catch (IllegalArgumentException e){
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode errorNode = objectMapper.createObjectNode();
			errorNode.put("error", e.getMessage());
			return errorNode;
		}

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode jsonNode = objectMapper.createObjectNode();
		ArrayNode suggestionsArray = objectMapper.createArrayNode();
		// Process suggestions based on survey criteria
		if (survey.getLocalisation().equals("ALL")||Continent.isValidValue(survey.getLocalisation().toUpperCase())) {
			// Process all countries
			for (Suggestion suggestion : suggestions) {
				ObjectNode suggestionNode = objectMapper.createObjectNode();
				// Append country details
				ObjectNode countryNode = objectMapper.createObjectNode();
				countryNode.put("Country", suggestion.getCountry().getCountryName());
				countryNode.put("Description", suggestion.getCountry().getDescription());

				// Append city details
				ObjectNode cityNode = objectMapper.createObjectNode();
				cityNode.put("City", suggestion.getCity().getCityName());
				cityNode.put("Description", suggestion.getCity().getDescription());
				cityNode.put("Climate", String.valueOf(suggestion.getCity().getClimate()));
				cityNode.put("Temperature", String.valueOf(suggestion.getCity().getTemperature()));
				cityNode.put("Transport", String.valueOf(suggestion.getCity().getTransport()));

				// Append activities details
				ArrayNode activitiesArray = objectMapper.createArrayNode();
				for (int i = 0; i < suggestion.getActivityList().size(); i++) {
					ObjectNode activityNode = objectMapper.createObjectNode();
					activityNode.put("Activity", (i + 1));
					activityNode.put("Name", suggestion.getActivityList().get(i).getActivityName());
					activityNode.put("Type of the activity", String.valueOf(suggestion.getActivityList().get(i).getActivityType()));
					activityNode.put("Description", suggestion.getActivityList().get(i).getDescription());
					activityNode.put("Price", suggestion.getActivityList().get(i).getPrice());
					activitiesArray.add(activityNode);
				}
				suggestionNode.set("Country", countryNode);
				suggestionNode.set("City", cityNode);
				suggestionNode.set("Activities", activitiesArray);

				suggestionsArray.add(suggestionNode);
			}
			jsonNode.set("Suggestions", suggestionsArray);
		} else {
			// Process specific country
			Suggestion suggest = suggestions.iterator().next();
			jsonNode.put("Country", suggest.getCountry().getCountryName());
			jsonNode.put("Continent", String.valueOf(suggest.getCountry().getContinent()));
			jsonNode.put("Description", suggest.getCountry().getDescription());

			// Process cities and activities for the specific country
			ArrayNode suggestionsArrays = objectMapper.createArrayNode();
			for (Suggestion suggestion : suggestions) {
				ObjectNode suggestionNode = objectMapper.createObjectNode();
				ObjectNode cityNode = objectMapper.createObjectNode();

				cityNode.put("City", suggestion.getCity().getCityName());
				cityNode.put("Description", suggestion.getCity().getDescription());
				cityNode.put("Climate", String.valueOf(suggestion.getCity().getClimate()));
				cityNode.put("Temperature", String.valueOf(suggestion.getCity().getTemperature()));
				cityNode.put("Transport", String.valueOf(suggestion.getCity().getTransport()));

				// Append activities details
				ArrayNode activitiesArray = objectMapper.createArrayNode();
				for (int i = 0; i < suggestion.getActivityList().size(); i++) {
					ObjectNode activityNode = objectMapper.createObjectNode();
					activityNode.put("Activity" , (i + 1));
					activityNode.put("Name", suggestion.getActivityList().get(i).getActivityName());
					activityNode.put("Type of the activity", String.valueOf(suggestion.getActivityList().get(i).getActivityType()));
					activityNode.put("Description", suggestion.getActivityList().get(i).getDescription());
					activityNode.put("Price", suggestion.getActivityList().get(i).getPrice());
					activitiesArray.add(activityNode);
				}
				suggestionNode.set("City", cityNode);
				suggestionNode.set("Activities", activitiesArray);

				suggestionsArray.add(suggestionNode);
			}
			jsonNode.set("Suggestions", suggestionsArray);
		}

		return jsonNode;
	}

	// GET endpoint to retrieve suggestions with AI and with the usage of a cache
	@GetMapping("/getDestinationSuggestion")
	public ObjectNode getDestinationSuggestion(
			@RequestParam String localisation,
			@RequestParam Climate climate,
			@RequestParam Landscape landscape,
			@RequestParam Temperature temperature,
			@RequestParam List<ActivityType> activityType
	) {
		Survey survey = new Survey(localisation,climate,landscape,temperature,activityType);
		Iterable<Suggestion> suggestions = new ArrayList<>();

		try{
			suggestions = DestinationSuggestionService.getSuggestionAIWithCache(survey);
		}catch (IllegalArgumentException e){
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode errorNode = objectMapper.createObjectNode();
			errorNode.put("error", e.getMessage());
			return errorNode;
		}

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode jsonNode = objectMapper.createObjectNode();
		ArrayNode suggestionsArray = objectMapper.createArrayNode();
		// Process suggestions based on survey criteria
		if (survey.getLocalisation().equals("ALL")||Continent.isValidValue(survey.getLocalisation().toUpperCase())) {
			// Process all countries
			for (Suggestion suggestion : suggestions) {
				ObjectNode suggestionNode = objectMapper.createObjectNode();
				// Append country details
				ObjectNode countryNode = objectMapper.createObjectNode();
				countryNode.put("Country", suggestion.getCountry().getCountryName());
				countryNode.put("Description", suggestion.getCountry().getDescription());

				// Append city details
				ObjectNode cityNode = objectMapper.createObjectNode();
				cityNode.put("City", suggestion.getCity().getCityName());
				cityNode.put("Description", suggestion.getCity().getDescription());
				cityNode.put("Climate", String.valueOf(suggestion.getCity().getClimate()));
				cityNode.put("Temperature", String.valueOf(suggestion.getCity().getTemperature()));
				cityNode.put("Transport", String.valueOf(suggestion.getCity().getTransport()));

				// Append activities details
				ArrayNode activitiesArray = objectMapper.createArrayNode();
				for (int i = 0; i < suggestion.getActivityList().size(); i++) {
					ObjectNode activityNode = objectMapper.createObjectNode();
					activityNode.put("Activity", (i + 1));
					activityNode.put("Name", suggestion.getActivityList().get(i).getActivityName());
					activityNode.put("Type of the activity", String.valueOf(suggestion.getActivityList().get(i).getActivityType()));
					activityNode.put("Description", suggestion.getActivityList().get(i).getDescription());
					activityNode.put("Price", suggestion.getActivityList().get(i).getPrice());
					activitiesArray.add(activityNode);
				}
				suggestionNode.set("Country", countryNode);
				suggestionNode.set("City", cityNode);
				suggestionNode.set("Activities", activitiesArray);

				suggestionsArray.add(suggestionNode);
			}
			jsonNode.set("Suggestions", suggestionsArray);
		} else {
			// Process specific country
			Suggestion suggest = suggestions.iterator().next();
			jsonNode.put("Country", suggest.getCountry().getCountryName());
			jsonNode.put("Continent", String.valueOf(suggest.getCountry().getContinent()));
			jsonNode.put("Description", suggest.getCountry().getDescription());

			// Process cities and activities for the specific country
			ArrayNode suggestionsArrays = objectMapper.createArrayNode();
			for (Suggestion suggestion : suggestions) {
				ObjectNode suggestionNode = objectMapper.createObjectNode();
				ObjectNode cityNode = objectMapper.createObjectNode();

				cityNode.put("City", suggestion.getCity().getCityName());
				cityNode.put("Description", suggestion.getCity().getDescription());
				cityNode.put("Climate", String.valueOf(suggestion.getCity().getClimate()));
				cityNode.put("Temperature", String.valueOf(suggestion.getCity().getTemperature()));
				cityNode.put("Transport", String.valueOf(suggestion.getCity().getTransport()));

				// Append activities details
				ArrayNode activitiesArray = objectMapper.createArrayNode();
				for (int i = 0; i < suggestion.getActivityList().size(); i++) {
					ObjectNode activityNode = objectMapper.createObjectNode();
					activityNode.put("Activity", (i + 1));
					activityNode.put("Name", suggestion.getActivityList().get(i).getActivityName());
					activityNode.put("Type of the activity", String.valueOf(suggestion.getActivityList().get(i).getActivityType()));
					activityNode.put("Description", suggestion.getActivityList().get(i).getDescription());
					activityNode.put("Price", suggestion.getActivityList().get(i).getPrice());
					activitiesArray.add(activityNode);
				}
				suggestionNode.set("City", cityNode);
				suggestionNode.set("Activities", activitiesArray);

				suggestionsArray.add(suggestionNode);
			}
			jsonNode.set("Suggestions", suggestionsArray);
		}

		return jsonNode;
	}

}