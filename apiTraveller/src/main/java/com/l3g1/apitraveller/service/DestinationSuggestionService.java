package com.l3g1.apitraveller.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.l3g1.apitraveller.model.*;
import com.l3g1.apitraveller.repository.CityRepository;
import com.l3g1.apitraveller.repository.CountryRepository;
import com.l3g1.apitraveller.repository.ActivityRepository;
import lombok.SneakyThrows;
import org.hibernate.graph.internal.parse.SubGraphGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.Data;


@Data
@Service

public class DestinationSuggestionService {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private AiService aiService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private CityService cityService;

	@Autowired
	@Qualifier("destinationCacheManager")
	private CacheManager cacheManager;

	// Generates suggestions based on the survey criteria provided with the data from the database
	// If the corresponding data is not present in the database, it returns nothing.
	public List<Suggestion>getSuggestion(Survey survey) throws IllegalArgumentException{

		// Extract survey parameters
		if(!survey.getLocalisation().equals("ALL")) {
			String localisation = survey.getLocalisation().toLowerCase().replace("_", " ");
			// Capitalizes the first letter of the location
			localisation = Character.toUpperCase(localisation.charAt(0)) + localisation.substring(1);
			// Capitalizes the first letter after each space in the location
			for (int i = 0; i < localisation.length(); i++) {
				if (localisation.charAt(i) == ' ') {
					localisation = localisation.substring(0, i + 1) + Character.toUpperCase(localisation.charAt(i + 1)) + localisation.substring(i + 2);
				}
			}
			survey.setLocalisation(localisation);
		}
		String localisation = survey.getLocalisation();
		survey.setLocalisation(localisation);
		Climate climate = survey.getClimate();
		Landscape landscape = survey.getLandscape();
		Temperature temperature = survey.getTemperature();
		List<ActivityType> activityType = survey.getActivityType();

		// Initialize variables
		Country countryDest = null;
		List<Suggestion> suggList = new ArrayList<>();
		List<Country> countryList = new ArrayList<>();
		List<City> cityList;
		int numberOfSuggestions;
		Random random;
		int randomIndex;
		City cityDest;
		List<Activity> activityDest;

		// Generate suggestions based on survey criteria
		if (localisation.equals("ALL")||Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))){ // Manage the code for both cases as the code is almost the same for the two, only the initialisation of countryList change
			if (localisation.equals("ALL")) { // No specific country specified by the user, return 1 to 3 suggestions of different countries
				// Retrieve list of countries based on the Climate in the survey
				if (climate.equals(Climate.ALL)) {
					countryList = countryRepository.findAll();
				} else {
					countryList = findAllByClimateListContaining(climate);
				}
			}else if(Continent.isValidValue(localisation.toUpperCase())) { // A specific continent is given by the user, return 1 to 3 suggestions of different countries in the specified continent
				// Retrieve the specified country based on the given continent and climate
				if (climate.equals(Climate.ALL)) {
					countryList = countryRepository.findAllByContinent(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")));
				} else {
					countryList = findCountryByContinentAndClimateListContaining(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")), climate);
				}
			}

			// Determine number of suggestions to generate based on available cities
			random = new Random();
			numberOfSuggestions = Math.min(countryList.size(), 3);
			if (!countryList.isEmpty()) {
				for (int i = 0; i < numberOfSuggestions; i++) {
					// Select a random country in the list of country
					randomIndex = random.nextInt(countryList.size());
					countryDest = countryList.get(randomIndex);

					// Filter cities in the selected country based on criteria
					cityList = filterCitiesByCriteria(countryDest, landscape, temperature, climate);

					if (!cityList.isEmpty()) {
						// Select a random city in the list of city
						int randomIndex2 = random.nextInt(cityList.size());
						cityDest = cityList.get(randomIndex2);
						activityDest = new ArrayList<>();
						// Retrieve activities in the selected city based on activity types
						for (ActivityType activityType1 : activityType) {
							activityDest.addAll(activityRepository.findAllByCityAndActivityType(cityDest, activityType1));
						}
						List<Activity> activitySuggestion = new ArrayList<>();
						Random random3 = new Random();
						// Select 3 random activities from the list of suggested activities
						for (int j = 0; j < 3; j++) {
							int randomIndex3;
							do {
								randomIndex3 = random3.nextInt(activityDest.size());
							} while (activitySuggestion.contains(activityDest.get(randomIndex3)));

							activitySuggestion.add(activityDest.get(randomIndex3));
						}

						// If there are activities available, create a suggestion
						if (!activityDest.isEmpty()) {
							Suggestion suggestion = new Suggestion(countryDest, cityDest, activitySuggestion, survey);
							suggList.add(suggestion);
						}
					}
					// Remove the selected country to avoid duplicates
					countryList.remove(randomIndex);
				}
			}
		}else if (RecognizedCountry.isValidValue(localisation.toUpperCase().replace(" ", "_"))){// A specific country is specified by the user, return 1 to 3 suggestions of different cities of the specific country
			// Retrieve the specified country based on the country name and climate
			if(climate.equals(Climate.ALL)){
				countryDest = countryRepository.findByCountryName(localisation);
			}else{
				countryDest = findCountryByCountryNameAndClimateListContaining(localisation, climate);
			}
			if(countryDest!=null){
				// Filter cities in the specified country based on criteria
				cityList = filterCitiesByCriteria(countryDest, landscape, temperature, climate);
				// Determine number of suggestions to generate based on available cities
				numberOfSuggestions = Math.min(cityList.size(), 3);
				random = new Random();
				if (!cityList.isEmpty()) {
					for (int i = 0; i < numberOfSuggestions; i++) {
						// Select a random city in the list of city
						randomIndex = random.nextInt(cityList.size());
						cityDest = cityList.get(randomIndex);
						activityDest = new ArrayList<>();
						// Retrieve activities in the selected city based on activity types
						for (ActivityType activityType1 : activityType) {
							activityDest.addAll(activityRepository.findAllByCityAndActivityType(cityDest, activityType1));
						}

						List<Activity> activitySuggestion = new ArrayList<>();
						Random random3 = new Random();
						// Select 3 random activities from the list of suggested activities
						for (int j = 0; j < 3; j++) {
							int randomIndex3;
							do {
								randomIndex3 = random3.nextInt(activityDest.size());
							} while (activitySuggestion.contains(activityDest.get(randomIndex3)));

							activitySuggestion.add(activityDest.get(randomIndex3));
						}

						Suggestion suggestion = new Suggestion(countryDest, cityDest, activitySuggestion, survey);
						suggList.add(suggestion);
						// Remove the selected city to avoid duplicates
						cityList.remove(randomIndex);
					}
				}
			}
		}else{
			throw new IllegalArgumentException("The localisation you have given in the survey is incorrect, please check the survey.");
		}
		// Return the list of suggestions
		return suggList;
	}

	// Generates suggestions based on the survey criteria provided with the data from the database
	// If the corresponding data is not present in the database, the API calls an Ai to ask for the needed data
	// The new data is saved in the database during the process.
	@SneakyThrows
	public List<Suggestion>getSuggestionAI (Survey survey) throws IllegalArgumentException{

		// Extract survey parameters
		String localisation = survey.getLocalisation().toLowerCase().replace("_", " ");
		localisation = Character.toUpperCase(localisation.charAt(0)) + localisation.substring(1);
		for (int i = 0; i < localisation.length(); i++) {
			if (localisation.charAt(i) == ' ') {
				localisation = localisation.substring(0, i + 1) + Character.toUpperCase(localisation.charAt(i + 1)) + localisation.substring(i + 2);
			}
		}
		survey.setLocalisation(localisation);
		Climate climate = survey.getClimate();
		Landscape landscape = survey.getLandscape();
		Temperature temperature = survey.getTemperature();
		List<ActivityType> activityType = survey.getActivityType();

		// Initialize variables
		Country countryDest = null;
		List<Suggestion> suggList = new ArrayList<>();
		List<Country> countryList = new ArrayList<>();
		List<City> cityList;
		int numberOfSuggestions;
		Random random;
		int randomIndex;
		int randomIndex2;
		City cityDest;
		List<Activity> activityDest;
		String jsonString;
		List<Activity> activitySuggestion;
		ObjectMapper objectMapper;
		int iterationCountryAI;
		int iterationCityAI;
		int iterationActivityAI;

		// Generate suggestions based on survey criteria
		if (localisation.equals("ALL")||Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))) { // Manage the code for both cases as the code is almost the same for the two, only the initialisation of countryList change
			if (localisation.equals("ALL")) { // No specific country specified by the user, return 3 suggestions of different countries
				// Retrieve list of countries based on the Climate in the survey
				if (climate.equals(Climate.ALL)) {
					countryList = countryRepository.findAll();
				} else {
					countryList = findAllByClimateListContaining(climate);
				}
			} else if (Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))) { // A specific continent is given by the user, return 3 suggestions of different countries in the specified continent
				// Retrieve the specified country based on the given continent and climate
				if (climate.equals(Climate.ALL)) {
					countryList = countryRepository.findAllByContinent(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")));
					for (Country country : countryList){
					}
				} else {
					countryList = findCountryByContinentAndClimateListContaining(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")), climate);
				}
			}
			random = new Random();
			numberOfSuggestions = 3;

			objectMapper = new ObjectMapper();
			iterationCountryAI = 0;
			// Iterate until enough countries are added by the AI or maximum iterations reached
			while (countryList.size() < 3 && iterationCountryAI < 10) {
				// Call the AI to add new countries based on the information on the survey

				try {
					jsonString = aiService.chatCountry(survey);
					Country country = objectMapper.readValue(jsonString, Country.class);
					// Add the new country to the database
					countryService.addCountry(country);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// Retrieve updated country list
				if (localisation.equals("ALL")) {
					if (climate.equals(Climate.ALL)) {
						countryList = countryRepository.findAll();
					} else {
						countryList = findAllByClimateListContaining(climate);
					}
				}else if (Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))){
					if (climate.equals(Climate.ALL)) {
						countryList = countryRepository.findAllByContinent(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")));
						for (Country country : countryList){
						}
					} else {
						countryList = findCountryByContinentAndClimateListContaining(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")), climate);
					}
				}

				iterationCountryAI++;
			}

			// Generate suggestions for the selected country
			for (int i = 0; i < numberOfSuggestions; i++) {
				// Select a random country
				randomIndex = random.nextInt(countryList.size());
				countryDest = countryList.get(randomIndex);

				// Filter cities in the selected country based on criteria
				cityList = filterCitiesByCriteria(countryDest, landscape, temperature, climate);

				iterationCityAI = 0;
				// Iterate until enough cities are found or maximum iterations reached
				while (cityList.size() < 3 && iterationCityAI < 10) {
					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					// Call the AI to add new cities based on the information on the survey
					try {
						jsonString = aiService.chatCity(countryDest, survey);
						objectMapper = new ObjectMapper();
						City city = objectMapper.readValue(jsonString, City.class);
						// Add suggested city to database
						cityService.addCity(city);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// Retrieve updated city list
					cityList = filterCitiesByCriteria(countryDest, landscape, temperature, climate);
					iterationCityAI++;
				}
				// Select a random city
				randomIndex2 = random.nextInt(cityList.size());
				cityDest = cityList.get(randomIndex2);
				activityDest = new ArrayList<>();
				// Retrieve activities in the selected city based on activity types
				for (ActivityType activityType1 : activityType) {
					activityDest.addAll(activityRepository.findAllByCityAndActivityType(cityDest, activityType1));
				}

				iterationActivityAI = 0;
				// Iterate until enough activities are found or maximum iterations reached
				while (activityDest.size() < 3 && iterationActivityAI < 10) {
					// Call the AI to add new activities based on the information on the survey
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}

					try {
						jsonString = aiService.chatActivity(countryDest, cityDest, activityDest, survey);
						objectMapper = new ObjectMapper();

						City city = objectMapper.readValue(jsonString, City.class);
						cityService.addCity(city);
					} catch (Exception e) {
						e.printStackTrace();
					}
					List<Activity> newActivities = new ArrayList<>();
					// Retrieve updated activity list for the city
					for (ActivityType activityType1 : activityType) {
						newActivities.addAll(activityRepository.findAllByCityAndActivityType(cityDest, activityType1));
					}
					// Add new activities to suggestion list
					for (Activity newActivity : newActivities) {
						// Add the new activity only if it's not already present in activityDest
						if (!activityDest.contains(newActivity)) {
							activityDest.add(newActivity);
							if (activityDest.size() >= 3) {
								break; // Exit the loop if we have enough activities
							}
						}
					}
					iterationActivityAI++;
				}

				activitySuggestion = new ArrayList<>();
				Random random3 = new Random();
				// Select 3 random activities from the list of suggested activities
				for (int j = 0; j < 3; j++) {
					int randomIndex3;
					do {
						randomIndex3 = random3.nextInt(activityDest.size());
					} while (activitySuggestion.contains(activityDest.get(randomIndex3)));

					activitySuggestion.add(activityDest.get(randomIndex3));
				}

				Suggestion suggestion = new Suggestion(countryDest, cityDest, activitySuggestion, survey);
				suggList.add(suggestion);
				countryList.remove(randomIndex);
				// Remove selected country to avoid duplicates
			}
		}else if(RecognizedCountry.isValidValue(localisation.toUpperCase().replace(" ", "_"))){ //The user has specified a country, return 3 suggestions of different cities in the specified country
			// Retrieve the specified country based on name and climate
			if(climate.equals(Climate.ALL)){
				countryDest = countryRepository.findByCountryName(localisation);
			}else{
				countryDest = findCountryByCountryNameAndClimateListContaining(localisation, climate);
			}
			//Ask the AI if the country is not in the DB
			random = new Random();
			objectMapper = new ObjectMapper();
			numberOfSuggestions=3;
			iterationCountryAI = 0;
			// Iterate until the specified country is found in the database
			while(countryDest==null && iterationCountryAI<10) {
				// Call the AI to add the new country based on the information on the survey
				try{
					jsonString = aiService.chatCountry(survey);
					Country country = objectMapper.readValue(jsonString, Country.class);
					// Add suggested country to database
					countryService.addCountry(country);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(climate.equals(Climate.ALL)){
					countryDest = countryRepository.findByCountryName(localisation);
				} else {
					countryDest = findCountryByCountryNameAndClimateListContaining(localisation, climate);
				}
				iterationCountryAI++;
			}
			// Filter cities in the specified country based on criteria
			cityList = filterCitiesByCriteria(countryDest, landscape, temperature, climate);

			iterationCityAI = 0;
			// Iterate until enough cities are found or maximum iterations reached
			while(cityList.size() < 3 && iterationCityAI < 10) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				// Call the AI to add new cities based on the information on the survey
				try {
					jsonString = aiService.chatCity(countryDest, survey);
					objectMapper = new ObjectMapper();
					City city = objectMapper.readValue(jsonString, City.class);
					// Add suggested city to database
					cityService.addCity(city);
				}catch(Exception e){
					e.printStackTrace();
				}
				// Retrieve updated city list
				cityList = filterCitiesByCriteria(countryDest, landscape, temperature, climate);
				iterationCityAI++;
			}
			// Generate suggestions for each selected city
			for(int i = 0; i < numberOfSuggestions; i++) {

				randomIndex2 = random.nextInt(cityList.size());
				cityDest = cityList.get(randomIndex2);
				activityDest = new ArrayList<>();
				// Retrieve activities in the selected city based on activity types
				for (ActivityType activityType1 : activityType) {
					activityDest.addAll(activityRepository.findAllByCityAndActivityType(cityDest, activityType1));
				}

				iterationActivityAI = 0;
				// Iterate until enough activities are found or maximum iterations reached
				while (activityDest.size() < 3 && iterationActivityAI < 10) {
					// Call the AI to add new activities based on the information on the survey
					try {
						jsonString = aiService.chatActivity(countryDest, cityDest, activityDest, survey);
						objectMapper = new ObjectMapper();

						City city = objectMapper.readValue(jsonString, City.class);
						cityService.addCity(city);
					}catch(Exception e){
						e.printStackTrace();
					}
					List<Activity> newActivities = new ArrayList<>();
					for (ActivityType activityType1 : activityType) {
						newActivities.addAll(activityRepository.findAllByCityAndActivityType(cityDest, activityType1));
					}
					for (Activity newActivity : newActivities) {
						// Add the new activity only if it's not already present in activityDest
						if (!activityDest.contains(newActivity)) {
							activityDest.add(newActivity);
							if (activityDest.size() >= 3) {
								break; // Exit the loop if we have enough activities
							}
						}
					}
					iterationActivityAI++;
				}

				// Select 3 random activities from the list of suggested activities
				activitySuggestion = new ArrayList<>();
				Random random3 = new Random();

				for (int j = 0; j < 3; j++) {
					int randomIndex3;
					do {
						randomIndex3 = random3.nextInt(activityDest.size());
					} while (activitySuggestion.contains(activityDest.get(randomIndex3)));

					activitySuggestion.add(activityDest.get(randomIndex3));
				}

				Suggestion suggestion = new Suggestion(countryDest, cityDest, activitySuggestion, survey);
				suggList.add(suggestion);
				// Remove selected city to avoid duplicates
				cityList.remove(randomIndex2);
			}
		}else{
			throw new IllegalArgumentException("The localisation you have given in the survey is incorrect, please check the survey.");
		}
		// Return the list of suggestions
		return suggList;

	}


	public List<Suggestion>getSuggestionAIWithCache(Survey survey) throws IllegalArgumentException{

		// Extract survey parameters
		if(!survey.getLocalisation().equals("ALL")) {
			String localisation = survey.getLocalisation().toLowerCase().replace("_", " ");
			// Capitalizes the first letter of the location
			localisation = Character.toUpperCase(localisation.charAt(0)) + localisation.substring(1);
			// Capitalizes the first letter after each space in the location
			for (int i = 0; i < localisation.length(); i++) {
				if (localisation.charAt(i) == ' ') {
					localisation = localisation.substring(0, i + 1) + Character.toUpperCase(localisation.charAt(i + 1)) + localisation.substring(i + 2);
				}
			}
			survey.setLocalisation(localisation);
		}
		String localisation = survey.getLocalisation();
		survey.setLocalisation(localisation);
		Climate climate = survey.getClimate();
		Landscape landscape = survey.getLandscape();
		Temperature temperature = survey.getTemperature();
		List<ActivityType> activityType = survey.getActivityType();

		// Initialize variables
		Country countryDest = null;
		List<Suggestion> suggList = new ArrayList<>();
		List<Country> countryList = new ArrayList<>();
		List<City> cityList;
		int numberOfSuggestions;
		Random random;
		int randomIndex;
		int randomIndex2;
		City cityDest;
		List<Activity> activityDest;
		String jsonString;
		List<Activity> activitySuggestion;
		ObjectMapper objectMapper;
		int iterationCountryAI;
		int iterationCityAI;
		int iterationActivityAI;

		List<Suggestion> suggestionsFromCache = getSuggestionAIWithCache(survey);

		// Generate suggestions based on survey criteria
		if (localisation.equals("ALL")||Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))) { // Manage the code for both cases as the code is almost the same for the two, only the initialisation of countryList change
			if (localisation.equals("ALL")) { // No specific country specified by the user, return 3 suggestions of different countries
				// Retrieve list of countries based on the Climate in the survey
				if (climate.equals(Climate.ALL)) {
					countryList = countryRepository.findAll();
				} else {
					countryList = findAllByClimateListContaining(climate);
				}
			} else if (Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))) { // A specific continent is given by the user, return 3 suggestions of different countries in the specified continent
				// Retrieve the specified country based on the given continent and climate
				if (climate.equals(Climate.ALL)) {
					countryList = countryRepository.findAllByContinent(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")));
					for (Country country : countryList){
					}
				} else {
					countryList = findCountryByContinentAndClimateListContaining(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")), climate);
				}
			}
			random = new Random();
			numberOfSuggestions = 3;

			if (suggestionsFromCache != null) {
				for (Suggestion suggestion : suggestionsFromCache) {
					Country country = suggestion.getCountry();
					if (country != null && countryList.contains(country)) {
						countryList.remove(country);
					}
				}
			}

			objectMapper = new ObjectMapper();
			iterationCountryAI = 0;
			// Iterate until enough countries are added by the AI or maximum iterations reached
			while (countryList.size() < 3 && iterationCountryAI < 10) {
				// Call the AI to add new countries based on the information on the survey

				try {
					jsonString = aiService.chatCountry(survey);
					Country country = objectMapper.readValue(jsonString, Country.class);
					// Add the new country to the database
					countryService.addCountry(country);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// Retrieve updated country list
				if (localisation.equals("ALL")) {
					if (climate.equals(Climate.ALL)) {
						countryList = countryRepository.findAll();
					} else {
						countryList = findAllByClimateListContaining(climate);
					}
				}else if (Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))){
					if (climate.equals(Climate.ALL)) {
						countryList = countryRepository.findAllByContinent(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")));
						for (Country country : countryList){
						}
					} else {
						countryList = findCountryByContinentAndClimateListContaining(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")), climate);
					}
				}

				if (suggestionsFromCache != null) {
					for (Suggestion suggestion : suggestionsFromCache) {
						Country country = suggestion.getCountry();
						if (country != null && countryList.contains(country)) {
							countryList.remove(country);
						}
					}
				}

				iterationCountryAI++;
			}

			// Generate suggestions for the selected country
			for (int i = 0; i < numberOfSuggestions; i++) {
				// Select a random country
				randomIndex = random.nextInt(countryList.size());
				countryDest = countryList.get(randomIndex);

				// Filter cities in the selected country based on criteria
				cityList = filterCitiesByCriteria(countryDest, landscape, temperature, climate);

				iterationCityAI = 0;
				// Iterate until enough cities are found or maximum iterations reached
				while (cityList.size() < 3 && iterationCityAI < 10) {
					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					// Call the AI to add new cities based on the information on the survey
					try {
						jsonString = aiService.chatCity(countryDest, survey);
						objectMapper = new ObjectMapper();
						City city = objectMapper.readValue(jsonString, City.class);
						// Add suggested city to database
						cityService.addCity(city);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// Retrieve updated city list
					cityList = filterCitiesByCriteria(countryDest, landscape, temperature, climate);
					iterationCityAI++;
				}
				// Select a random city
				randomIndex2 = random.nextInt(cityList.size());
				cityDest = cityList.get(randomIndex2);
				activityDest = new ArrayList<>();
				// Retrieve activities in the selected city based on activity types
				for (ActivityType activityType1 : activityType) {
					activityDest.addAll(activityRepository.findAllByCityAndActivityType(cityDest, activityType1));
				}

				iterationActivityAI = 0;
				// Iterate until enough activities are found or maximum iterations reached
				while (activityDest.size() < 3 && iterationActivityAI < 10) {
					// Call the AI to add new activities based on the information on the survey
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}

					try {
						jsonString = aiService.chatActivity(countryDest, cityDest, activityDest, survey);
						objectMapper = new ObjectMapper();

						City city = objectMapper.readValue(jsonString, City.class);
						cityService.addCity(city);
					} catch (Exception e) {
						e.printStackTrace();
					}
					List<Activity> newActivities = new ArrayList<>();
					// Retrieve updated activity list for the city
					for (ActivityType activityType1 : activityType) {
						newActivities.addAll(activityRepository.findAllByCityAndActivityType(cityDest, activityType1));
					}
					// Add new activities to suggestion list
					for (Activity newActivity : newActivities) {
						// Add the new activity only if it's not already present in activityDest
						if (!activityDest.contains(newActivity)) {
							activityDest.add(newActivity);
							if (activityDest.size() >= 3) {
								break; // Exit the loop if we have enough activities
							}
						}
					}
					iterationActivityAI++;
				}

				activitySuggestion = new ArrayList<>();
				Random random3 = new Random();
				// Select 3 random activities from the list of suggested activities
				for (int j = 0; j < 3; j++) {
					int randomIndex3;
					do {
						randomIndex3 = random3.nextInt(activityDest.size());
					} while (activitySuggestion.contains(activityDest.get(randomIndex3)));

					activitySuggestion.add(activityDest.get(randomIndex3));
				}

				Suggestion suggestion = new Suggestion(countryDest, cityDest, activitySuggestion, survey);
				suggList.add(suggestion);
				countryList.remove(randomIndex);
				// Remove selected country to avoid duplicates
			}
		}else if(RecognizedCountry.isValidValue(localisation.toUpperCase().replace(" ", "_"))){ //The user has specified a country, return 3 suggestions of different cities in the specified country
			// Retrieve the specified country based on name and climate
			if(climate.equals(Climate.ALL)){
				countryDest = countryRepository.findByCountryName(localisation);
			}else{
				countryDest = findCountryByCountryNameAndClimateListContaining(localisation, climate);
			}
			//Ask the AI if the country is not in the DB
			random = new Random();
			objectMapper = new ObjectMapper();
			numberOfSuggestions=3;

			iterationCountryAI = 0;
			// Iterate until the specified country is found in the database
			while(countryDest==null && iterationCountryAI<10) {
				// Call the AI to add the new country based on the information on the survey
				try{
					jsonString = aiService.chatCountry(survey);
					Country country = objectMapper.readValue(jsonString, Country.class);
					// Add suggested country to database
					countryService.addCountry(country);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(climate.equals(Climate.ALL)){
					countryDest = countryRepository.findByCountryName(localisation);
				} else {
					countryDest = findCountryByCountryNameAndClimateListContaining(localisation, climate);
				}
				iterationCountryAI++;
			}
			// Filter cities in the specified country based on criteria
			cityList = filterCitiesByCriteria(countryDest, landscape, temperature, climate);

			if (suggestionsFromCache != null) {
				for (Suggestion suggestion : suggestionsFromCache) {
					City city = suggestion.getCity();
					if (city != null && cityList.contains(city)) {
						cityList.remove(city);
					}
				}
			}

			iterationCityAI = 0;
			// Iterate until enough cities are found or maximum iterations reached
			while(cityList.size() < 3 && iterationCityAI < 10) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				// Call the AI to add new cities based on the information on the survey
				try {
					jsonString = aiService.chatCity(countryDest, survey);
					objectMapper = new ObjectMapper();
					City city = objectMapper.readValue(jsonString, City.class);
					// Add suggested city to database
					cityService.addCity(city);
				}catch(Exception e){
					e.printStackTrace();
				}
				// Retrieve updated city list
				cityList = filterCitiesByCriteria(countryDest, landscape, temperature, climate);
				iterationCityAI++;

				if (suggestionsFromCache != null) {
					for (Suggestion suggestion : suggestionsFromCache) {
						City city = suggestion.getCity();
						if (city != null && cityList.contains(city)) {
							cityList.remove(city);
						}
					}
				}

			}

			// Generate suggestions for each selected city
			for(int i = 0; i < numberOfSuggestions; i++) {

				randomIndex2 = random.nextInt(cityList.size());
				cityDest = cityList.get(randomIndex2);
				activityDest = new ArrayList<>();
				// Retrieve activities in the selected city based on activity types
				for (ActivityType activityType1 : activityType) {
					activityDest.addAll(activityRepository.findAllByCityAndActivityType(cityDest, activityType1));
				}

				iterationActivityAI = 0;
				// Iterate until enough activities are found or maximum iterations reached
				while (activityDest.size() < 3 && iterationActivityAI < 10) {
					// Call the AI to add new activities based on the information on the survey
					try {
						jsonString = aiService.chatActivity(countryDest, cityDest, activityDest, survey);
						objectMapper = new ObjectMapper();

						City city = objectMapper.readValue(jsonString, City.class);
						cityService.addCity(city);
					}catch(Exception e){
						e.printStackTrace();
					}
					List<Activity> newActivities = new ArrayList<>();
					for (ActivityType activityType1 : activityType) {
						newActivities.addAll(activityRepository.findAllByCityAndActivityType(cityDest, activityType1));
					}
					for (Activity newActivity : newActivities) {
						// Add the new activity only if it's not already present in activityDest
						if (!activityDest.contains(newActivity)) {
							activityDest.add(newActivity);
							if (activityDest.size() >= 3) {
								break; // Exit the loop if we have enough activities
							}
						}
					}
					iterationActivityAI++;
				}

				// Select 3 random activities from the list of suggested activities
				activitySuggestion = new ArrayList<>();
				Random random3 = new Random();

				for (int j = 0; j < 3; j++) {
					int randomIndex3;
					do {
						randomIndex3 = random3.nextInt(activityDest.size());
					} while (activitySuggestion.contains(activityDest.get(randomIndex3)));

					activitySuggestion.add(activityDest.get(randomIndex3));
				}

				Suggestion suggestion = new Suggestion(countryDest, cityDest, activitySuggestion, survey);
				suggList.add(suggestion);
				// Remove selected city to avoid duplicates
				cityList.remove(randomIndex2);
			}
		}else{
			throw new IllegalArgumentException("The localisation you have given in the survey is incorrect, please check the survey.");
		}

		putSuggestionInCache(survey, suggList);

		// Return the list of suggestions
		return suggList;

	}

	public List<Suggestion> getSuggestionFromCache(Survey survey) throws IllegalArgumentException {
		Cache cache = cacheManager.getCache("destinationSuggestion");
		Survey cacheKey = survey;
		Cache.ValueWrapper valueWrapper = cache != null ? cache.get(cacheKey) : null;

		if (valueWrapper != null) {
			return (List<Suggestion>) valueWrapper.get();
		}

		return null;
	}

	public void putSuggestionInCache(Survey survey, List<Suggestion> newSuggestions) throws IllegalArgumentException {
		Cache cache = cacheManager.getCache("destinationSuggestion");
		Survey cacheKey = survey;

		if (cache != null) {
			Cache.ValueWrapper valueWrapper = cache != null ? cache.get(cacheKey) : null;
			List<Suggestion> existingSuggestions = new ArrayList<>();
			if (valueWrapper != null) {
				existingSuggestions = (List<Suggestion>) valueWrapper.get();
			}
			List<Suggestion> mergedSuggestions = mergeSuggestions(existingSuggestions, newSuggestions);
			cache.put(cacheKey, mergedSuggestions);
		}
	}


	@Scheduled(fixedRate = 30 * 60 * 1000)
	public void evictObsoleteEntries() {
		Cache cache = cacheManager.getCache("destinationSuggestion");
		if (cache != null) {
			cache.clear();
		}
	}

	private List<Suggestion> mergeSuggestions(List<Suggestion> existingSuggestions, List<Suggestion> newSuggestions) {
		List<Suggestion> mergedSuggestions = new ArrayList<>();
		if (existingSuggestions != null) {
			for (Suggestion suggestion : existingSuggestions) {
				mergedSuggestions.add(suggestion);
			}
		}
		if (newSuggestions != null) {
			for (Suggestion suggestion : newSuggestions) {
				mergedSuggestions.add(suggestion);
			}
		}
		return mergedSuggestions;
	}



	// Finds a country by its name and checks if it contains the specified climate.
	public Country findCountryByCountryNameAndClimateListContaining(String countryName, Climate climate) {
		Country country = countryRepository.findByCountryName(countryName);
		if (country != null && country.getClimateList().contains(climate)) {
			return country;
		} else {
			return null;
		}
	}

	// Finds all countries in the specified continent and checks if it contains the specified climate.
	public List<Country> findCountryByContinentAndClimateListContaining(Continent continent, Climate climate) {
		List<Country> listCountry = countryRepository.findAllByContinent(continent);
		List<Country> matchingCountries = new ArrayList<>();

		for (Country country : listCountry) {
			if (country != null && country.getClimateList().contains(climate)) {
				matchingCountries.add(country);
			}
		}

		return matchingCountries.isEmpty() ? null : matchingCountries;
	}

	// Finds all countries that contain the specified climate.
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

	// Filters cities based on specified criteria such as country, landscape, temperature, and climate.
	public List<City> filterCitiesByCriteria(Country countryDest, Landscape landscape, Temperature temperature, Climate climate) {
		List<City> cityList;
		if (landscape.equals(Landscape.ALL) && temperature.equals(Temperature.ALL) && climate.equals(Climate.ALL)) {
			cityList = cityRepository.findAllByCountry(countryDest);
		} else if (landscape.equals(Landscape.ALL) && temperature.equals(Temperature.ALL)) {
			cityList = cityRepository.findAllByCountryAndClimate(countryDest, climate);
		} else if (landscape.equals(Landscape.ALL) && climate.equals(Climate.ALL)) {
			cityList = cityRepository.findAllByCountryAndTemperature(countryDest, temperature);
		} else if (temperature.equals(Temperature.ALL) && climate.equals(Climate.ALL)) {
			cityList = cityRepository.findAllByCountryAndLandscape(countryDest, landscape);
		} else if (landscape.equals(Landscape.ALL)) {
			cityList = cityRepository.findAllByCountryAndTemperatureAndClimate(countryDest, temperature, climate);
		} else if (temperature.equals(Temperature.ALL)) {
			cityList = cityRepository.findAllByCountryAndLandscapeAndClimate(countryDest, landscape, climate);
		} else if (climate.equals(Climate.ALL)) {
			cityList = cityRepository.findAllByCountryAndLandscapeAndTemperature(countryDest, landscape, temperature);
		} else {
			cityList = cityRepository.findAllByCountryAndLandscapeAndTemperatureAndClimate(countryDest, landscape, temperature, climate);
		}

		return cityList;
	}



}