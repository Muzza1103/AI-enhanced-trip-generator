package com.l3g1.apitraveller.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.l3g1.apitraveller.model.*;
import com.l3g1.apitraveller.repository.CityRepository;
import com.l3g1.apitraveller.repository.CountryRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.l3g1.apitraveller.dtoAi.AiRequest;
import com.l3g1.apitraveller.dtoAi.AiResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Service
public class AiService {
    @Value("${openai.api.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate template;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;
    // Static declarations of values for different types of attributes
    private static String climateValues = "climate: [\"TROPICAL\", \"DESERT\", \"POLAR\", \"MEDITERRANEAN\", \"TEMPERATE\"]";
    private static String landscapeValues = "landscape: [\"MOUNTAIN\", \"BEACH\", \"FOREST\", \"DESERT\", \"VALLEY\", \"COASTAL\", \"RURAL\", \"URBAN\"]";
    private static String temperatureValues = "temperature: [\"HOT\", \"WARM\", \"MILD\", \"TEMPERATE\", \"COOL\", \"COLD\"]]";
    private static String transportValues = "transport: [\"BUS\", \"METRO\", \"BICYCLE\", \"SCOOTER\", \"CAR\", \"TAXI\", \"FERRY\", \"WALKING\", \"TRAM\", \"BIKE\", \"OTHER\"]";
    private static String activityTypeValues = "activityType: [\"OUTDOOR\", \"CULTURAL\", \"RELAXATION\", \"ADVENTURE\", \"GASTRONOMIC\", \"ENTERTAINMENT\", \"ROMANTIC\", \"HISTORICAL\", \"OTHER\"]";
    private static String continentValues = "continent: [\"EUROPE\", \"ASIA\", \"AFRICA\", \"NORTH_AMERICA\", \"SOUTH_AMERICA\", \"OCEANIA\",\"ANTARCTICA\"]";

    //Initiates a conversation with the AI to gather information about countries, cities, and activities based on the provided survey. Generates a prompt with specific criteria and sends it to the AI model. Processes the AI response to retrieve suggestions for country, cities, and activities.
    public String chatCountry(Survey survey) {
        StringBuilder promptBuilder = new StringBuilder();
        List<Country> listCountry;
        // Build prompt based on user's preferences
        if (survey.getLocalisation().equals("ALL")||Continent.isValidValue(survey.getLocalisation().toUpperCase())) {
            if(survey.getLocalisation().equals("ALL")) {
                promptBuilder.append("Give me a random country ");
            }else if (Continent.isValidValue(survey.getLocalisation().toUpperCase())) {
                promptBuilder.append("Give me a country in the continent " + survey.getLocalisation().toUpperCase());
            }
            if(!survey.getClimate().equals(Climate.ALL)){
                promptBuilder.append("with the climate " + survey.getClimate());
                promptBuilder.append(" which is not in the following list : [");
                listCountry = findAllByClimateListContaining(survey.getClimate());
                for (Country country : listCountry){
                    promptBuilder.append(" \"" + country.getCountryName() + "\", ");
                }
            }else{
                listCountry = countryRepository.findAll();
                promptBuilder.append(" which is not in the following list :");
                for (Country country : listCountry){
                    promptBuilder.append(" \"" + country.getCountryName() + "\", ");
                }
            }
            promptBuilder.append("]");
        }else{
            promptBuilder.append("Give me for the country "+survey.getLocalisation());
        }

        promptBuilder.append(", three of its cities ");
        if(!survey.getClimate().equals(Climate.ALL)){
            promptBuilder.append("with the climate " + survey.getClimate());
        }
        if(!survey.getLandscape().equals(Landscape.ALL)){
            promptBuilder.append(", with the landscape " + survey.getLandscape());
        }
        if(!survey.getTemperature().equals(Temperature.ALL)){
            promptBuilder.append(" and with the temperature " + survey.getTemperature());
        }
        promptBuilder.append(", and three activities per city in the activityList, the activities must be of the following types :");
        for (ActivityType activityType : survey.getActivityType()){
            promptBuilder.append(" "+activityType+" ");
        }
        promptBuilder.append(" Complete the data. Price is an attribute with a number only, the currency is in euro, climateList is a list of all the climate present in the country");
        promptBuilder.append(" Here are the only possible values for these specific attributes: " + climateValues + ", " + landscapeValues + ", " + temperatureValues + ", " + transportValues + ", " + continentValues + ".");
        promptBuilder.append(" For transportList, you can only put transport which is included in this specific list : " + transportValues );
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
        // Create AI request with generated prompt
        AiRequest request = new AiRequest(model, promptBuilder.toString());

        // Send request to AI model
        AiResponse response = template.postForObject(apiUrl, request, AiResponse.class);

        // Process AI response
        if (response != null) {
            // Extract and process AI message
            String suggestion = response.getChoices().get(0).getMessage().getContent();
            return processAiMessage(suggestion);
        }
        return "";

    }

    public String chatCity(Country country, Survey survey) {

        StringBuilder promptBuilder = new StringBuilder();
        List<City> listCity = cityRepository.findAllByCountry(country);

        promptBuilder.append("Give me a city of "+country.getCountryName() );
        if(!survey.getClimate().equals(Climate.ALL)){
            promptBuilder.append("with the climate " + survey.getClimate());
        }
        promptBuilder.append(" which is not in the following list : [");
        for (City city : listCity){
            promptBuilder.append(" \"" + city.getCityName() + "\", ");
        }
        promptBuilder.append("]");
        if(!survey.getLandscape().equals(Landscape.ALL)){
            promptBuilder.append(", with the landscape " + survey.getLandscape());
        }
        if(!survey.getTemperature().equals(Temperature.ALL)){
            promptBuilder.append(" and with the temperature " + survey.getTemperature());
        }
        promptBuilder.append("and three activities in the activityList, the activities must be of the following types :");
        for (ActivityType activityType : survey.getActivityType()){
            promptBuilder.append(" "+activityType+" ");
        }
        promptBuilder.append(" Here are the only possible values for these specific attributes: " + climateValues + ", " + landscapeValues + ", " + temperatureValues + ", " + transportValues + ", " + continentValues + ".");
        promptBuilder.append(" Here are the possible values for the elements in transportList : "+transportValues);
        promptBuilder.append(". Give me the result in the following format, complete the data, price is an attribute with the number only,consider the currency is the Dollar, :");
        promptBuilder.append(" Here is the format you need to return : ");
        promptBuilder.append("{\n");
        promptBuilder.append("    \"cityName\": \"\",\n");
        promptBuilder.append("    \"climate\": \"\",\n");
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


        AiRequest request = new AiRequest(model, promptBuilder.toString());
        AiResponse response = template.postForObject(apiUrl, request, AiResponse.class);

        if (response != null) {
            //return response.getChoices().get(0).getMessage().getContent();
            String suggestion = response.getChoices().get(0).getMessage().getContent();

            return suggestion = processAiMessage(suggestion);
        }
        return "";

    }
    //Initiates a conversation with the AI to gather information about a city in the specified country based on the provided survey. Generates a prompt with specific criteria and sends it to the AI model. Processes the AI response to retrieve suggestions for the city and its activities.
    public String chatActivity(Country country, City city, List<Activity> listActivity, Survey survey) {
        StringBuilder promptBuilder = new StringBuilder();
        // Build prompt based on user's preferences
        promptBuilder.append("Give me 3 activities in activityList of " + city.getCityName() + ", the activities must be of the following types :");
        for (ActivityType activityType : survey.getActivityType()){
            promptBuilder.append(" "+activityType+" ");
        }
        promptBuilder.append("Give me activities which are not in the following list : [");
        for (Activity activity : listActivity){
            promptBuilder.append(" \"" + activity.getActivityName() + "\", ");
        }
        promptBuilder.append("]");
        promptBuilder.append(". Give me the result in the following format, complete the data, price is an attribute with the number only, consider the currency is the Dollar, :");
        promptBuilder.append("{\n");
        promptBuilder.append("    \"cityName\": \"" + city.getCityName() + "\",\n");
        promptBuilder.append("    \"climate\": \""+ city.getClimate() +"\",\n");
        promptBuilder.append("    \"activityList\": [\n");
        promptBuilder.append("        {\n");
        promptBuilder.append("            \"activityName\": \"\",\n");
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
        promptBuilder.append("        \"countryName\": \"" + country.getCountryName() + "\"\n");
        promptBuilder.append("    }\n");
        promptBuilder.append("}\n");

        // Create AI request with generated prompt
        AiRequest request = new AiRequest(model, promptBuilder.toString());

        // Send request to AI model
        AiResponse response = template.postForObject(apiUrl, request, AiResponse.class);

        // Process AI response
        if (response != null) {
            // Extract and process AI message
            String suggestion = response.getChoices().get(0).getMessage().getContent();
            return processAiMessage(suggestion);
        }
        return "";
    }
    public String chatTripCountry(TripSurvey survey) {
        StringBuilder promptBuilder = new StringBuilder();
        List<Country> listCountry;
        // Build prompt based on user's preferences
        if (survey.getLocalisation().equals("ALL")||Continent.isValidValue(survey.getLocalisation().toUpperCase())) {
            if(survey.getLocalisation().equals("ALL")) {
                promptBuilder.append("Give me a random country ");
            }else if (Continent.isValidValue(survey.getLocalisation().toUpperCase())) {
                promptBuilder.append("Give me a country in the continent " + survey.getLocalisation().toUpperCase());
            }
            if(!survey.getClimate().equals(Climate.ALL)){
                promptBuilder.append("with the climate " + survey.getClimate());
                promptBuilder.append(" which is not in the following list : [");
                listCountry = findAllByClimateListContaining(survey.getClimate());
                for (Country country : listCountry){
                    promptBuilder.append(" \"" + country.getCountryName() + "\", ");
                }
            }else{
                listCountry = countryRepository.findAll();
                promptBuilder.append(" which is not in the following list :");
                for (Country country : listCountry){
                    promptBuilder.append(" \"" + country.getCountryName() + "\", ");
                }
            }
            promptBuilder.append("]");
        }else{
            promptBuilder.append("Give me for the country "+survey.getLocalisation());
        }

        promptBuilder.append(", three of its cities ");
        if(!survey.getClimate().equals(Climate.ALL)){
            promptBuilder.append("with the climate " + survey.getClimate());
        }
        if(!survey.getLandscape().equals(Landscape.ALL)){
            promptBuilder.append(", with the landscape " + survey.getLandscape());
        }
        if(!survey.getTemperature().equals(Temperature.ALL)){
            promptBuilder.append(" and with the temperature " + survey.getTemperature());
        }
        promptBuilder.append(", and three activities per city in the activityList, the activities must be of the following types :");
        for (ActivityType activityType : survey.getActivityType()){
            promptBuilder.append(" "+activityType+" ");
        }
        promptBuilder.append(" Complete the data. Price is an attribute with a number only, the currency is in euro, climateList is a list of all the climate present in the country");
        promptBuilder.append(" Here are the only possible values for these specific attributes: " + climateValues + ", " + landscapeValues + ", " + temperatureValues + ", " + transportValues + ", " + continentValues + ".");
        promptBuilder.append(" For transportList, you can only put transport which is included in this specific list : " + transportValues );
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
        // Create AI request with generated prompt
        AiRequest request = new AiRequest(model, promptBuilder.toString());
        AiResponse response = template.postForObject(apiUrl, request, AiResponse.class);

        if (response != null) {
            //return response.getChoices().get(0).getMessage().getContent();
            String suggestion = response.getChoices().get(0).getMessage().getContent();
            return suggestion = processAiMessage(suggestion);
        }
        return "";

    }

    //Initiates a conversation with the AI to gather information about a trip including country, cities, and activities based on the provided trip survey. Generates a prompt with specific criteria and sends it to the AI model. Processes the AI response to retrieve suggestions for the trip details.
    public String chatTripCity(Country country, TripSurvey survey) {

        StringBuilder promptBuilder = new StringBuilder();
        List<City> listCity = cityRepository.findAllByCountry(country);

        promptBuilder.append("Give me a city of "+country.getCountryName() );
        if(!survey.getClimate().equals(Climate.ALL)){
            promptBuilder.append("with the climate " + survey.getClimate());
        }
        promptBuilder.append(" which is not in the following list : [");
        for (City city : listCity){
            promptBuilder.append(" \"" + city.getCityName() + "\", ");
        }
        promptBuilder.append("]");
        if(!survey.getLandscape().equals(Landscape.ALL)){
            promptBuilder.append(", with the landscape " + survey.getLandscape());
        }
        if(!survey.getTemperature().equals(Temperature.ALL)){
            promptBuilder.append(" and with the temperature " + survey.getTemperature());
        }
        promptBuilder.append("and three activities in the activityList, the activities must be of the following types :");
        for (ActivityType activityType : survey.getActivityType()){
            promptBuilder.append(" "+activityType+" ");
        }
        promptBuilder.append(" Here are the only possible values for these specific attributes: " + climateValues + ", " + landscapeValues + ", " + temperatureValues + ", " + transportValues + ", " + continentValues + ".");
        promptBuilder.append(" Here are the possible values for the elements in transportList : "+transportValues);
        promptBuilder.append(". Give me the result in the following format, complete the data, price is an attribute with the number only,consider the currency is the Dollar, :");
        promptBuilder.append(" Here is the format you need to return : ");
        promptBuilder.append("{\n");
        promptBuilder.append("    \"cityName\": \"\",\n");
        promptBuilder.append("    \"climate\": \"\",\n");
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

        // Create AI request with generated prompt
        AiRequest request = new AiRequest(model, promptBuilder.toString());

        // Send request to AI model
        AiResponse response = template.postForObject(apiUrl, request, AiResponse.class);

        // Process AI response
        if (response != null) {
            // Extract and process AI message
            String suggestion = response.getChoices().get(0).getMessage().getContent();
            return processAiMessage(suggestion);
        }
        return "";

    }

    //Initiates a conversation with the AI to gather information about activities in a city for a trip. Generates a prompt with specific criteria and sends it to the AI model. Processes the AI response to retrieve suggestions for the activities.
    public String chatTripActivity(Country country, City city, List<Activity> listActivity, TripSurvey survey, Integer upperLimit, Integer lowerLimit) {
        StringBuilder promptBuilder = new StringBuilder();
        // Build prompt based on user's preferences
        promptBuilder.append("Give me 5 activities in activityList of " + city.getCityName() + ", the activities must be of the following types :");
        for (ActivityType activityType : survey.getActivityType()){
            promptBuilder.append(" "+activityType+" ");
        }
        promptBuilder.append("Give me activities which are not in the following list : [");
        for (Activity activity : listActivity){
            promptBuilder.append(" \"" + activity.getActivityName() + "\", ");
        }
        promptBuilder.append("]");
        if (lowerLimit!=0) {
            if (upperLimit != null) {
                if (upperLimit >= 1) {
                    promptBuilder.append(". Give me activites with price higher or equal to " + lowerLimit +" and below or equal to " + upperLimit);
                } else {
                    promptBuilder.append(". Give me free activities with price equal to 0");
                }
            }
        }else{
            if (upperLimit != null) {
                if (upperLimit >= 1) {
                    promptBuilder.append(". Give me activites with price below or equal to " + upperLimit);
                } else {
                    promptBuilder.append(". Give me free activities with price equal to 0");
                }
            }
        }
        promptBuilder.append(". Give me the result in the following format, complete the data, price is an attribute with the number only, consider the currency is the Dollar, :");
        promptBuilder.append("{\n");
        promptBuilder.append("    \"cityName\": \"" + city.getCityName() + "\",\n");
        promptBuilder.append("    \"climate\": \""+ city.getClimate() +"\",\n");
        promptBuilder.append("    \"activityList\": [\n");
        promptBuilder.append("        {\n");
        promptBuilder.append("            \"activityName\": \"\",\n");
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
        promptBuilder.append("        \"countryName\": \"" + country.getCountryName() + "\"\n");
        promptBuilder.append("    }\n");
        promptBuilder.append("}\n");

        // Create AI request with generated prompt
        AiRequest request = new AiRequest(model, promptBuilder.toString());

        // Send request to AI model
        AiResponse response = template.postForObject(apiUrl, request, AiResponse.class);

        // Process AI response
        if (response != null) {
            // Extract and process AI message
            String suggestion = response.getChoices().get(0).getMessage().getContent();
            return processAiMessage(suggestion);
        }
        return "";
    }

    //Processes the response message from the AI to ensure validity of certain fields. Replaces invalid values with appropriate default values.
    private String processAiMessage(String aiMessage) {
        List<String> validTransportValues = Arrays.asList("BUS", "METRO", "BICYCLE", "SCOOTER", "CAR", "TAXI", "FERRY", "WALKING", "TRAM", "BIKE", "OTHER");
        List<String> validActivityTypeValues = Arrays.asList("OUTDOOR", "CULTURAL", "RELAXATION", "ADVENTURE", "GASTRONOMIC", "ENTERTAINMENT", "ROMANTIC", "HISTORICAL", "OTHER");
        List<String> validLandscapeValues = Arrays.asList("MOUNTAIN", "BEACH", "FOREST", "DESERT", "VALLEY", "COASTAL", "RURAL", "URBAN");
        List<String> validClimateValues = Arrays.asList("TROPICAL", "DESERT", "POLAR", "MEDITERRANEAN", "TEMPERATE", "OTHER");
        List<String> validContinentValues = Arrays.asList("EUROPE", "ASIA", "AFRICA", "NORTH_AMERICA", "SOUTH_AMERICA", "OCEANIA","ANTARCTICA");

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Convert the AI message to JSON object
            JsonNode messageJson = mapper.readTree(aiMessage);

            // Check if the message contains cityList
            if (messageJson.has("cityList")) {
                // Get all occurrences of cityList
                ArrayNode cityList = (ArrayNode) messageJson.get("cityList");

                // Iterate through each occurrence of cityList
                for (JsonNode cityNode : cityList) {
                    // Check if the city node contains transportList
                    if (cityNode.has("transportList")) {
                        // Get the list of transports for the current city
                        ArrayNode transportList = (ArrayNode) cityNode.get("transportList");
                        ArrayNode validTransportList = mapper.createArrayNode();

                        // Iterate through the values of transportList
                        for (JsonNode transportNode : transportList) {
                            String transportValue = transportNode.asText();
                            // Check if the transport value is valid, sinon met Other
                            if (validTransportValues.contains(transportValue)) {
                                validTransportList.add(transportValue);
                            } else {
                                validTransportList.add("OTHER");
                            }
                        }

                        // Replace the transportList with the new valid list
                        ((ObjectNode) cityNode).set("transportList", validTransportList);
                    }

                    if (cityNode.has("landscape")) {
                        // Check if the city node contains landscape
                        String landscapeValue = cityNode.get("landscape").asText();
                        // Check if the landscape value is valid
                        if (!validLandscapeValues.contains(landscapeValue)) {
                            ((ObjectNode) cityNode).put("landscape", "OTHER");
                        }
                    }

                }
            }
            // Check if the message contains cityList
            if (messageJson.has("cityList")) {
                // Get all occurrences of cityList
                ArrayNode cityList = (ArrayNode) messageJson.get("cityList");

                // Iterate through each occurrence of cityList
                for (JsonNode cityNode : cityList) {
                    // Check if the city node contains activityList
                    if (cityNode.has("activityList")) {
                        // Get the list of activities for the current city
                        ArrayNode activityList = (ArrayNode) cityNode.get("activityList");

                        // Iterate through each activity in the list
                        for (JsonNode activityNode : activityList) {
                            // Check if the activity node contains activityType
                            if (activityNode.has("activityType")) {
                                // Get the value of activityType
                                String activityTypeValue = activityNode.get("activityType").asText();

                                // Check if the activityType value is valid
                                if (!validActivityTypeValues.contains(activityTypeValue)) {
                                    // Replace the invalid value with "OTHER"
                                    ((ObjectNode) activityNode).put("activityType", "OTHER");
                                }
                            }
                        }
                    }
                }
            }

            // Check if the message node contains activityList
            if (messageJson.has("activityList")) {
                // Get the list of activities for the current city
                ArrayNode activityList = (ArrayNode) messageJson.get("activityList");

                // Iterate through each activity in the list
                for (JsonNode activityNode : activityList) {
                    // Check if the activity node contains activityType
                    if (activityNode.has("activityType")) {
                        // Get the value of activityType
                        String activityTypeValue = activityNode.get("activityType").asText();

                        // Check if the activityType value is valid
                        if (!validActivityTypeValues.contains(activityTypeValue)) {
                            // Replace the invalid value with "OTHER"
                            ((ObjectNode) activityNode).put("activityType", "OTHER");
                        }
                    }
                }
            }

            // Check if the message contains cityList
            if (messageJson.has("cityList")) {
                // Get all occurrences of cityList
                ArrayNode cityList = (ArrayNode) messageJson.get("cityList");

                // Iterate through each occurrence of cityList
                for (JsonNode cityNode : cityList) {
                    // Check if the city node contains landscape
                    if (cityNode.has("landscape")) {
                        // Get the value of landscape
                        String landscapeValue = cityNode.get("landscape").asText();

                        // Check if the landscape value is valid
                        if (!validLandscapeValues.contains(landscapeValue)) {
                            // Replace the invalid value with "OTHER"
                            ((ObjectNode) cityNode).put("landscape", "OTHER");
                        }
                    }
                }
            }
            // Check if the message contains cityList
            if (messageJson.has("cityList")) {
                // Get all occurrences of cityList
                ArrayNode cityList = (ArrayNode) messageJson.get("cityList");

                // Iterate through each occurrence of cityList
                for (JsonNode cityNode : cityList) {
                    // Check if the city node contains climate
                    if (cityNode.has("climate")) {
                        // Get the value of climate
                        String climateValue = cityNode.get("climate").asText();

                        // Check if the climate value is valid
                        if (!validClimateValues.contains(climateValue)) {
                            // Replace the invalid value with "OTHER"
                            ((ObjectNode) cityNode).put("climate", "OTHER");
                        }
                    }
                }
            }
            // Check if the message contains climate
            if (messageJson.has("climate")) {
                // Get the climate value
                String climateValue = messageJson.get("climate").asText();

                // Check if the climate value is valid
                if (!validClimateValues.contains(climateValue)) {
                    // Replace the invalid value with "OTHER"
                    ((ObjectNode) messageJson).put("climate", "OTHER");
                }
            }
            // Check if the message contains transportList
            if (messageJson.has("transportList")) {
                // Get the transportList
                ArrayNode transportList = (ArrayNode) messageJson.get("transportList");
                ArrayNode validTransportList = mapper.createArrayNode();

                // Iterate through values of transportList
                for (JsonNode transportNode : transportList) {
                    String transportValue = transportNode.asText();
                    // Check if the transport value is valid
                    if (validTransportValues.contains(transportValue)) {
                        validTransportList.add(transportValue);
                    } else {
                        validTransportList.add("OTHER");
                    }
                }

                // Replace the transportList with the new valid list
                ((ObjectNode) messageJson).set("transportList", validTransportList);
            }

            // Check if the message contains climateList
            if (messageJson.has("climateList")) {
                // Get the climateList
                ArrayNode climateList = (ArrayNode) messageJson.get("climateList");
                ArrayNode validClimateList = mapper.createArrayNode();

                // Iterate through values of climateList
                for (JsonNode climateNode : climateList) {
                    String climateValue = climateNode.asText();
                    // Check if the climate value is valid
                    if (validClimateValues.contains(climateValue)) {
                        validClimateList.add(climateValue);
                    } else {
                        validClimateList.add("OTHER");
                    }
                }

                // Replace the climateList with the new valid list
                ((ObjectNode) messageJson).set("climateList", validClimateList);
            }

            // Check if the message contains continent
            if (messageJson.has("continent")) {
                // Get the continent value
                String continentValue = messageJson.get("continent").asText();

                // Check if the continent value is valid
                if (!validContinentValues.contains(continentValue)) {
                    // Replace the invalid value with upper case
                    ((ObjectNode) messageJson).put("continent", continentValue.toUpperCase());
                }
            }



            // Convert the modified JSON object back to a string
            return mapper.writeValueAsString(messageJson);
        } catch (IOException e) {
            e.printStackTrace();
            return ""; // Handle the error accordingly
        }

    }

    //Finds all countries that have the specified climate in their climate list.
    public List<Country> findAllByClimateListContaining(Climate climate) {

        List<Country> allCountries = countryRepository.findAll();
        List<Country> countriesWithClimate = new ArrayList<>();

        for (Country country : allCountries) {
            if (country.getClimateList().contains(climate)) {
                countriesWithClimate.add(country);
            }
        }
        return countriesWithClimate;
    }


}

