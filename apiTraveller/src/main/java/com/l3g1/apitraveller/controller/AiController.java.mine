package com.l3g1.apitraveller.controller;

import com.l3g1.apitraveller.model.Country;
import com.l3g1.apitraveller.model.Survey;
import com.l3g1.apitraveller.model.TripSurvey;
import com.l3g1.apitraveller.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.l3g1.apitraveller.dtoAi.AiRequest;
import com.l3g1.apitraveller.dtoAi.AiResponse;

@RestController
@RequestMapping("/ai")
public class AiController {
	@Value("${openai.api.model}")
	private String model;
	@Value("${openai.api.url}")
	private String apiUrl;

	@Autowired
	private RestTemplate template;

	@Autowired
	private AiService aiService;


	// Static declarations of values for different types of attributes
	private static String climateValues = "Climate: ['TROPICAL', 'DESERT', 'POLAR', 'MEDITERRANEAN', 'TEMPERATE']";
	private static String landscapeValues = "Landscape: ['MOUNTAIN', 'BEACH', 'FOREST', 'DESERT', 'VALLEY', 'COASTAL', 'RURAL', 'URBAN']";
	private static String temperatureValues = "Temperature: ['HOT', 'WARM', 'MILD', 'TEMPERATE', 'COOL', 'COLD']]";
	private static String transportValues = "Transport: ['BUS', 'METRO', 'BICYCLE', 'SCOOTER', 'CAR', 'TAXI', 'FERRY', 'WALKING', 'TRAM', 'BIKE']";
	private static String activityTypeValues = "Activity Type: ['OUTDOOR', 'CULTURAL', 'RELAXATION', 'ADVENTURE', 'GASTRONOMIC', 'ENTERTAINMENT', 'ROMANTIC', 'HISTORICAL', 'NONE']";
	private static String continentValues = "Season: ['AUTUMN', 'SUMMER', 'SPRING', 'WINTER']";
	private static String seasonTypeValues = "Continent: ['EUROPE', 'ASIA', 'AFRICA', 'NORTH_AMERICA', 'SOUTH_AMERICA', 'OCEANIA']";
	// Endpoint to test a request to the AI
	@GetMapping ("/chat")
	public String chat() {
		// Constructing the prompt
		StringBuilder promptBuilder = new StringBuilder();
		 promptBuilder.append("Give me a random country, three of its cities, and three activities in the activityList per city in the following format. Complete the data; price is an attribute with a number only, without the currency. Here are the only possible values for these specific attributes: " + climateValues + ", " + landscapeValues + ", " + temperatureValues + ", " + transportValues + ", " + activityTypeValues + ", " + seasonTypeValues + ", " + continentValues + "." +
		 " For transportList, you can only put transport which is include in his specific list : " + transportValues + "and for activityList, the type of the activity has to be one of these specific values : " + activityTypeValues);
		promptBuilder.append(". Give me the result in the following format, complete the data, price is an attribute with the number only, consider the currency is the Dollar, :");
		promptBuilder.append("{\n");
		promptBuilder.append("    \"countryName\": \"\",\n");
		promptBuilder.append("    \"climateList\": \"[\"],\n");
		promptBuilder.append("    \"continent\": \"\",\n");
		promptBuilder.append("    \"description\": \"\",\n");
		promptBuilder.append("    \"cityList\": [\n");
		promptBuilder.append("        {\n");
		promptBuilder.append("            \"cityName\": \"\",\n");
		promptBuilder.append("            \"climate\": \"\",\n");
		promptBuilder.append("            \"temperature\": \"\",\n");
		promptBuilder.append("            \"landscape\": \"\",\n");
		promptBuilder.append("            \"description\": \"\",\n");
		promptBuilder.append("            \"transportList\": [\"BUS\", \"METRO\", \"BICYCLE\", \"SCOOTER\", \"CAR\", \"TAXI\", \"BOAT\", \"WALKING\", \"TRAM\", \"BIKE\"],\n"); // Les valeurs possibles pour transportList sont énumérées ici
		promptBuilder.append("            \"activityList\": [\n");
		promptBuilder.append("                {\n");
		promptBuilder.append("                    \"activityName\": \"\",\n");
		promptBuilder.append("                    \"activityType\": \"\",\n");
		promptBuilder.append("                    \"description\": \"\",\n");
		promptBuilder.append("                    \"price\": \"\"\n");
		promptBuilder.append("                },\n");
		promptBuilder.append("                {\n");
		promptBuilder.append("                    \"activityName\": \"\",\n");
		promptBuilder.append("                    \"activityType\": \"\",\n");
		promptBuilder.append("                    \"description\": \"\",\n");
		promptBuilder.append("                    \"price\": \"\"\n");
		promptBuilder.append("                }\n");
		promptBuilder.append("            ]\n");
		promptBuilder.append("        },\n");
		promptBuilder.append("        {\n");
		promptBuilder.append("            \"cityName\": \"\",\n");
		promptBuilder.append("            \"climate\": \"\",\n");
		promptBuilder.append("            \"temperature\": \"\",\n");
		promptBuilder.append("            \"landscape\": \"\",\n");
		promptBuilder.append("            \"description\": \"\",\n");
		promptBuilder.append("            \"transportList\": [\"BUS\", \"METRO\", \"BICYCLE\", \"SCOOTER\", \"CAR\", \"TAXI\", \"BOAT\", \"WALKING\", \"TRAM\", \"BIKE\"],\n"); // Les valeurs possibles pour transportList sont énumérées ici
		promptBuilder.append("            \"activityList\": [\n");
		promptBuilder.append("                {\n");
		promptBuilder.append("                    \"activityName\": \"\",\n");
		promptBuilder.append("                    \"activityType\": \"\",\n");
		promptBuilder.append("                    \"description\": \"\",\n");
		promptBuilder.append("                    \"price\": \"\"\n");
		promptBuilder.append("                },\n");
		promptBuilder.append("                {\n");
		promptBuilder.append("                    \"activityName\": \"\",\n");
		promptBuilder.append("                    \"activityType\": \"\",\n");
		promptBuilder.append("                    \"description\": \"\",\n");
		promptBuilder.append("                    \"price\": \"\"\n");
		promptBuilder.append("                }\n");
		promptBuilder.append("            ]\n");
		promptBuilder.append("        }\n");
		promptBuilder.append("    ]\n");
		promptBuilder.append("}");

		String prompt = promptBuilder.toString();
		// Sending the prompt to the AI API and retrieving the response
		AiRequest request = new AiRequest(model, prompt);
		AiResponse response = template.postForObject(apiUrl, request, AiResponse.class);

		if (response != null) {
			return response.getChoices().get(0).getMessage().getContent();
		}
		return "";
	}
	// Endpoint to test a request to the AI
	@GetMapping ("/chatCity")
	public String chatCity(@RequestBody Country country) {
		// Constructing the prompt
		StringBuilder promptBuilder = new StringBuilder();
		 promptBuilder.append("Give me a city of "+country.getCountryName()+", three activities in the activityList in the following format, here are the possible values for these attributes: "+climateValues+" "+landscapeValues+" "+temperatureValues+" "+transportValues+" "+activityTypeValues+", :");
		 promptBuilder.append("{\n");
		 promptBuilder.append("    \"cityName\": \"\",\n");
		 promptBuilder.append("    \"season\": \"\",\n");
		 promptBuilder.append("    \"climateList\": \"\",\n");
		 promptBuilder.append("    \"temperature\": \"\",\n");
		 promptBuilder.append("    \"landscape\": \"\",\n");
		 promptBuilder.append("    \"description\": \"\",\n");
		 promptBuilder.append("    \"transportList\": [\"\"],\n");
		 promptBuilder.append("    \"activityList\": [\n");
		 promptBuilder.append("        {\n");
		 promptBuilder.append("            \"activityName\": \" \",\n");
		 promptBuilder.append("            \"activityType\": \"\",\n");
		 promptBuilder.append("            \"description\": \"\",\n");
		 promptBuilder.append("            \"price\": \"\"\n");
		 promptBuilder.append("        },\n");
		 promptBuilder.append("        {\n");
		 promptBuilder.append("            \"activityName\": \"\",\n");
		 promptBuilder.append("            \"activityType\": \"\",\n");
		 promptBuilder.append("            \"description\": \"\",\n");
		 promptBuilder.append("            \"price\": \"\"\n");
		 promptBuilder.append("        }\n");
		 promptBuilder.append("    ],\n");
		 promptBuilder.append("    \"country\": {\n");
		 promptBuilder.append("        \"countryName\": \""+country.getCountryName()+"\"\n");
		 promptBuilder.append("    }\n");
		 promptBuilder.append("}\n");

		String prompt = promptBuilder.toString();
		// Sending the prompt to the AI API and retrieving the response
		AiRequest request = new AiRequest(model, prompt);
		AiResponse response = template.postForObject(apiUrl, request, AiResponse.class);

		if (response != null) {
			return response.getChoices().get(0).getMessage().getContent();
		}
		return "";

	}
	// Endpoint to test the AI with survey data
	@GetMapping ("/chatTest")
	public String chatTest(@RequestBody Survey survey) {
		return aiService.chatCountry(survey);
	}
	// Endpoint to test the AI with trip survey data
	@GetMapping ("/chatTripTest")
	public String chatTest(@RequestBody TripSurvey tripSurvey) {
		return aiService.chatTripCountry(tripSurvey);
	}

}
