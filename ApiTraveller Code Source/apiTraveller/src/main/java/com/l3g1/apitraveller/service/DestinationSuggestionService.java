package com.l3g1.apitraveller.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.l3g1.apitraveller.cache.CacheConfig;
import com.l3g1.apitraveller.model.*;
import com.l3g1.apitraveller.model.enumeration.*;
import com.l3g1.apitraveller.repository.CityRepository;
import com.l3g1.apitraveller.repository.CountryRepository;
import com.l3g1.apitraveller.repository.ActivityRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Data;


@Data
@Service
// Service class for managing destination suggestions.
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
	private CacheConfig cacheConfig;

	// Generates suggestions based on the survey criteria provided with the data from the database
	// If the corresponding data is not present in the database, it returns nothing.
	public List<Suggestion>getSuggestion(Survey survey) throws IllegalArgumentException{

		// Extract survey parameters
		if(Continent.isValidValue(survey.getLocalisation().toUpperCase().replace(" ", "_"))){
			survey.setLocalisation(survey.getLocalisation().toUpperCase().replace(" ", "_"));
		}else if (!survey.getLocalisation().equals("ALL")) {
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
		Country countryDest;
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
					countryList = countryService.findAllByClimateListContaining(climate);
				}
			}else if(Continent.isValidValue(localisation.toUpperCase())) { // A specific continent is given by the user, return 1 to 3 suggestions of different countries in the specified continent
				// Retrieve the specified country based on the given continent and climate
				// Handle an error where the AI return erroneous values
				if(survey.getClimate() == Climate.MEDITERRANEAN && !survey.getLocalisation().equals(Continent.EUROPE.toString()) && !survey.getLocalisation().equals(Continent.AFRICA.toString())){
					survey.setClimate(Climate.TEMPERATE);
					climate = Climate.TEMPERATE;
				}

				if (climate.equals(Climate.ALL)) {
					countryList = countryRepository.findAllByContinent(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")));
				} else {
					countryList = countryService.findCountryByContinentAndClimateListContaining(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")), climate);
				}


			}

			// Determine number of suggestions to generate based on available cities
			random = new Random();
			if (countryList!=null) {
				numberOfSuggestions = Math.min(countryList.size(), 3);
				for (int i = 0; i < numberOfSuggestions; i++) {
					if(!countryList.isEmpty()) {
						// Select a random country in the list of country
						randomIndex = random.nextInt(countryList.size());
						countryDest = countryList.get(randomIndex);

						// Filter cities in the selected country based on criteria
						cityList = cityService.filterCitiesByCriteria(countryDest, landscape, temperature, climate);

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
							int numberOfActivities = Math.min(activityDest.size(),3);
							// Select 3 random activities from the list of suggested activities
							if(!activityDest.isEmpty()) {
								for (int j = 0; j < numberOfActivities; j++) {
									int randomIndex3;
									do {
										randomIndex3 = random3.nextInt(activityDest.size());
									} while (activitySuggestion.contains(activityDest.get(randomIndex3)));

									activitySuggestion.add(activityDest.get(randomIndex3));
								}

								// If there are activities available, create a suggestion
								if (!activitySuggestion.isEmpty()) {
									Suggestion suggestion = new Suggestion(countryDest, cityDest, activitySuggestion, survey);
									suggList.add(suggestion);
								}
							}
						}
						// Remove the selected country to avoid duplicates
						countryList.remove(randomIndex);
					}
				}
			}
		}else if (RecognizedCountry.isValidValue(localisation.toUpperCase().replace(" ", "_"))){// A specific country is specified by the user, return 1 to 3 suggestions of different cities of the specific country
			// Retrieve the specified country based on the country name and climate
			if(climate.equals(Climate.ALL)){
				countryDest = countryRepository.findByCountryName(localisation);
			}else{
				countryDest = countryService.findCountryByCountryNameAndClimateListContaining(localisation, climate);
			}
			if(countryDest!=null){
				// Filter cities in the specified country based on criteria
				cityList = cityService.filterCitiesByCriteria(countryDest, landscape, temperature, climate);
				// Determine number of suggestions to generate based on available cities
				random = new Random();
				if (!cityList.isEmpty()) {
					numberOfSuggestions = Math.min(cityList.size(), 3);
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
						int numberOfActivities = Math.min(activityDest.size(),3);
						// Select up to 3 random activities from the list of suggested activities
						if (!activityDest.isEmpty()) {
							for (int j = 0; j < numberOfActivities; j++) {
								int randomIndex3;
								do {
									randomIndex3 = random3.nextInt(activityDest.size());
								} while (activitySuggestion.contains(activityDest.get(randomIndex3)));

								activitySuggestion.add(activityDest.get(randomIndex3));
							}

							Suggestion suggestion = new Suggestion(countryDest, cityDest, activitySuggestion, survey);
							suggList.add(suggestion);
						}
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
		if(Continent.isValidValue(survey.getLocalisation().toUpperCase().replace(" ", "_"))){
			survey.setLocalisation(survey.getLocalisation().toUpperCase().replace(" ", "_"));
		}else if (!survey.getLocalisation().equals("ALL")) {
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
		Climate climate = survey.getClimate();
		Landscape landscape = survey.getLandscape();
		Temperature temperature = survey.getTemperature();
		List<ActivityType> activityType = survey.getActivityType();

		// Initialize variables
		Country countryDest;
		List<Suggestion> suggList = new ArrayList<>();
		List<Country> countryList = new ArrayList<>();
		List<City> cityList;
		int numberOfSuggestions = 3;
		int numberOfChoices = 3;
		Random random = new Random();
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

		List<Suggestion> randomSuggestionFromCache;

		List<List<Suggestion>> suggestionsFromCache = cacheConfig.getSuggestionFromCache(survey);
		if (suggestionsFromCache!=null&&!suggestionsFromCache.isEmpty()) {
			randomSuggestionFromCache = suggestionsFromCache.get(random.nextInt(suggestionsFromCache.size()));
			return randomSuggestionFromCache;
		}


		// Gets the value of the request per minute of the api key from an environment variable which is defined by the user prior to the launch
		int intValue = 3500;
		try {
			String maVariableEnv = System.getenv("RPM_API_KEY");
			intValue = Integer.parseInt(maVariableEnv);
		} catch (NumberFormatException e) {
			System.out.println("The environment variable RPM_API_KEY must be defined and must be an integer.");
		}

		//If you have a low RPM on your AI API Key, this prevents from sending too many requests to the AI
        int numberOfRequestPerMinute = intValue;
        long minMillisecondBetweenRequest = (60/numberOfRequestPerMinute)*1000;
        Instant timeLastRequest = null;


		// Generate suggestions based on survey criteria
		if (localisation.equals("ALL")||Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))) { // Manage the code for both cases as the code is almost the same for the two, only the initialisation of countryList change
			if (localisation.equals("ALL")) { // No specific country specified by the user, return 3 suggestions of different countries
				// Retrieve list of countries based on the Climate in the survey
				if (climate.equals(Climate.ALL)) {
					countryList = countryRepository.findAll();
				} else {
					countryList = countryService.findAllByClimateListContaining(climate);
				}
			} else if (Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))) { // A specific continent is given by the user, return 3 suggestions of different countries in the specified continent
				// Retrieve the specified country based on the given continent and climate
				// Handle an error where the AI return erroneous values
				if(survey.getClimate() == Climate.MEDITERRANEAN && !survey.getLocalisation().equals(Continent.EUROPE.toString()) && !survey.getLocalisation().equals(Continent.AFRICA.toString())){
					survey.setClimate(Climate.TEMPERATE);
					climate = Climate.TEMPERATE;
					suggestionsFromCache = cacheConfig.getSuggestionFromCache(survey);
					if (suggestionsFromCache!=null&&!suggestionsFromCache.isEmpty()) {
						randomSuggestionFromCache = suggestionsFromCache.get(random.nextInt(suggestionsFromCache.size()));
						return randomSuggestionFromCache;
					}

				}
				if (climate.equals(Climate.ALL)) {
					countryList = countryRepository.findAllByContinent(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")));
				} else {
					countryList = countryService.findCountryByContinentAndClimateListContaining(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")), climate);
				}

				//Put the number of suggestions to 1 because we can have only one country in Antarctica
				if(survey.getLocalisation().equals(Continent.ANTARCTICA.toString())){
					numberOfSuggestions = 1;
					numberOfChoices = 1;
				}
			}

			objectMapper = new ObjectMapper();
			iterationCountryAI = 0;
			// Iterate until enough countries are added by the AI or maximum iterations reached
			while ((countryList == null || countryList.size() < numberOfSuggestions) && iterationCountryAI < 10) {
				// Call the AI to add new countries based on the information on the survey

				//If you have a low RPM on your AI API Key, this prevents from sending too many requests to the AI
				if (timeLastRequest != null) {
					long millisecondBetweenRequest = millisecondsSince(timeLastRequest);
					if (millisecondBetweenRequest < minMillisecondBetweenRequest) {
						long waitTime = (minMillisecondBetweenRequest - millisecondBetweenRequest);
						try {
							Thread.sleep(waitTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				timeLastRequest = Instant.now();

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
						countryList = countryService.findAllByClimateListContaining(climate);
					}
				}else if (Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))){
					if (climate.equals(Climate.ALL)) {
						countryList = countryRepository.findAllByContinent(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")));
					} else {
						countryList = countryService.findCountryByContinentAndClimateListContaining(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")), climate);
					}
				}

				iterationCountryAI++;
			}
			if(countryList!=null) {
				// Generate suggestions for the selected country
				for (int i = 0; i < numberOfSuggestions; i++) {
					if(!countryList.isEmpty()) {
						random = new Random();
						// Select a random country
						randomIndex = random.nextInt(countryList.size());
						countryDest = countryList.get(randomIndex);

						// Filter cities in the selected country based on criteria
						cityList = cityService.filterCitiesByCriteria(countryDest, landscape, temperature, climate);

						iterationCityAI = 0;
						// Iterate until enough cities are found or maximum iterations reached
						while ((cityList == null || cityList.size() < numberOfChoices) && iterationCityAI < 10) {

							//If you have a low RPM on your AI API Key, this prevents from sending too many requests to the AI
							if (timeLastRequest != null) {
								long millisecondBetweenRequest = millisecondsSince(timeLastRequest);
								if (millisecondBetweenRequest < minMillisecondBetweenRequest) {
									long waitTime = (minMillisecondBetweenRequest - millisecondBetweenRequest);
									try {
										Thread.sleep(waitTime);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
							timeLastRequest = Instant.now();


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
							cityList = cityService.filterCitiesByCriteria(countryDest, landscape, temperature, climate);
							iterationCityAI++;
						}

						if (cityList != null) {
							// Select a random city
							random = new Random();
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

								//If you have a low RPM on your AI API Key, this prevents from sending too many requests to the AI
								if (timeLastRequest != null) {
									long millisecondBetweenRequest = millisecondsSince(timeLastRequest);
									if (millisecondBetweenRequest < minMillisecondBetweenRequest) {
										long waitTime = (minMillisecondBetweenRequest - millisecondBetweenRequest);
										try {
											Thread.sleep(waitTime);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								}
								timeLastRequest = Instant.now();

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
							if (activityDest.size() >= 3) {
								activitySuggestion = new ArrayList<>();
								random = new Random();
								// Select 3 random activities from the list of suggested activities
								for (int j = 0; j < 3; j++) {
									int randomIndex3;
									do {
										randomIndex3 = random.nextInt(activityDest.size());
									} while (activitySuggestion.contains(activityDest.get(randomIndex3)));

									activitySuggestion.add(activityDest.get(randomIndex3));
								}

								Suggestion suggestion = new Suggestion(countryDest, cityDest, activitySuggestion, survey);
								suggList.add(suggestion);
							}
						}
						countryList.remove(randomIndex);
						// Remove selected country to avoid duplicates
					}
				}
			}
		}else if(RecognizedCountry.isValidValue(localisation.toUpperCase().replace(" ", "_"))){ //The user has specified a country, return 3 suggestions of different cities in the specified country
			// Retrieve the specified country based on name and climate
			if(climate.equals(Climate.ALL)){
				countryDest = countryRepository.findByCountryName(localisation);
			}else{
				countryDest = countryService.findCountryByCountryNameAndClimateListContaining(localisation, climate);
			}
			//Ask the AI if the country is not in the DB
			random = new Random();
			objectMapper = new ObjectMapper();
			iterationCountryAI = 0;
			// Iterate until the specified country is found in the database
			while(countryDest==null && iterationCountryAI<10) {
				// Call the AI to add the new country based on the information on the survey
				try{
					jsonString = aiService.chatCountry(survey);
					Country country = objectMapper.readValue(jsonString, Country.class);
					// Handle an error where the AI return erroneous values
					if(survey.getClimate() == Climate.MEDITERRANEAN && country.getContinent() != Continent.EUROPE && country.getContinent() != Continent.AFRICA){
						survey.setClimate(Climate.TEMPERATE);
						climate = Climate.TEMPERATE;
						suggestionsFromCache = cacheConfig.getSuggestionFromCache(survey);
						if (suggestionsFromCache!=null&&!suggestionsFromCache.isEmpty()) {
							randomSuggestionFromCache = suggestionsFromCache.get(random.nextInt(suggestionsFromCache.size()));
							return randomSuggestionFromCache;
						}

						throw new IllegalArgumentException("MEDITERRANEAN Climate asked for a continent with no MEDITERRANEAN climate, Correction : The climate is changed from MEDITERRANEAN to TEMPERATE");
					}
					// Add suggested country to database
					countryService.addCountry(country);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(climate.equals(Climate.ALL)){
					countryDest = countryRepository.findByCountryName(localisation);
				} else {
					countryDest = countryService.findCountryByCountryNameAndClimateListContaining(localisation, climate);
				}
				iterationCountryAI++;
			}
			if(countryDest!=null) {
				// Filter cities in the specified country based on criteria
				cityList = cityService.filterCitiesByCriteria(countryDest, landscape, temperature, climate);

				iterationCityAI = 0;
				// Iterate until enough cities are found or maximum iterations reached
				while ((cityList == null || cityList.size() < numberOfSuggestions) && iterationCityAI < 10) {

					//If you have a low RPM on your AI API Key, this prevents from sending too many requests to the AI
					if (timeLastRequest != null) {
						long millisecondBetweenRequest = millisecondsSince(timeLastRequest);
						if (millisecondBetweenRequest < minMillisecondBetweenRequest) {
							long waitTime = (minMillisecondBetweenRequest - millisecondBetweenRequest);
							try {
								Thread.sleep(waitTime);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					timeLastRequest = Instant.now();


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
					cityList = cityService.filterCitiesByCriteria(countryDest, landscape, temperature, climate);
					iterationCityAI++;
				}
				if(cityList!=null) {
					// Generate suggestions for each selected city
					for (int i = 0; i < numberOfSuggestions; i++) {
						if(!cityList.isEmpty()) {
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

								//If you have a low RPM on your AI API Key, this prevents from sending too many requests to the AI
								if (timeLastRequest != null) {
									long millisecondBetweenRequest = millisecondsSince(timeLastRequest);
									if (millisecondBetweenRequest < minMillisecondBetweenRequest) {
										long waitTime = (minMillisecondBetweenRequest - millisecondBetweenRequest);
										try {
											Thread.sleep(waitTime);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								}
								timeLastRequest = Instant.now();

								// Call the AI to add new activities based on the information on the survey
								try {
									jsonString = aiService.chatActivity(countryDest, cityDest, activityDest, survey);
									objectMapper = new ObjectMapper();

									City city = objectMapper.readValue(jsonString, City.class);
									cityService.addCity(city);
								} catch (Exception e) {
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

							if (activityDest.size() >= 3) {
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
							}
							// Remove selected city to avoid duplicates
							cityList.remove(randomIndex2);
						}
					}
				}
			}
		}else{
			throw new IllegalArgumentException("The localisation you have given in the survey is incorrect, please check the survey.");
		}
		if(!suggList.isEmpty()) {
			cacheConfig.putSuggestionInCache(survey, suggList);
		}
		// Return the list of suggestions
		return suggList;

	}


	public List<Suggestion>getSuggestionAIWithCache(Survey survey) throws IllegalArgumentException{

		// Extract survey parameters
		if(Continent.isValidValue(survey.getLocalisation().toUpperCase().replace(" ", "_"))){
			survey.setLocalisation(survey.getLocalisation().toUpperCase().replace(" ", "_"));
		}else if (!survey.getLocalisation().equals("ALL")) {
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
		Country countryDest;
		List<Suggestion> suggList = new ArrayList<>();
		List<Country> countryList = new ArrayList<>();
		List<City> cityList;
		int numberOfSuggestions = 3;
		int numberOfChoices = 3;
		Random random = new Random();
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

		List<List<Suggestion>> suggestionsFromCache = cacheConfig.getSuggestionFromCache(survey);
		List<Suggestion> randomSuggestionFromCache;

		// Gets the value of the request per minute of the api key from an environment variable which is defined by the user prior to the launch
		int intValue = 3500;
		try {
			String maVariableEnv = System.getenv("RPM_API_KEY");
			intValue = Integer.parseInt(maVariableEnv);
		} catch (NumberFormatException e) {
			System.out.println("The environment variable RPM_API_KEY must be defined and must be an integer.");
		}

		 //If you have a low RPM on your AI API Key, this prevents from sending too many requests to the AI
        int numberOfRequestPerMinute = intValue;
        long minMillisecondBetweenRequest = (60/numberOfRequestPerMinute)*1000;
        Instant timeLastRequest = null;

		// Generate suggestions based on survey criteria
		if (localisation.equals("ALL")||Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))) { // Manage the code for both cases as the code is almost the same for the two, only the initialisation of countryList change
			if (localisation.equals("ALL")) { // No specific country specified by the user, return 3 suggestions of different countries
				// Retrieve list of countries based on the Climate in the survey
				if (climate.equals(Climate.ALL)) {
					countryList = countryRepository.findAll();
				} else {
					countryList = countryService.findAllByClimateListContaining(climate);
				}
			} else if (Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))) { // A specific continent is given by the user, return 3 suggestions of different countries in the specified continent
				// Retrieve the specified country based on the given continent and climate
				// Handle an error where the AI return erroneous values
				if(survey.getClimate() == Climate.MEDITERRANEAN && !survey.getLocalisation().equals(Continent.EUROPE.toString()) && !survey.getLocalisation().equals(Continent.AFRICA.toString())){
					survey.setClimate(Climate.TEMPERATE);
					climate = Climate.TEMPERATE;
					suggestionsFromCache = cacheConfig.getSuggestionFromCache(survey);
				}
				if (climate.equals(Climate.ALL)) {
					countryList = countryRepository.findAllByContinent(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")));
				} else {
					countryList = countryService.findCountryByContinentAndClimateListContaining(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")), climate);
				}

				//Check if the number of countries for the continent won't exceed the number of possible countries for this precise continent. If we ask for more countries than they are in the continent the AI may send incorrect data
				if(suggestionsFromCache!=null&&!suggestionsFromCache.isEmpty()) {
					if ((suggestionsFromCache.size()+1) * 3 > Continent.valueOf(localisation.toUpperCase()).getNumberOfCountries()){
						randomSuggestionFromCache = suggestionsFromCache.get(random.nextInt(suggestionsFromCache.size()));
						return randomSuggestionFromCache;
					}
				}

				//Put the number of suggestions to 1 because we can have only one country in Antarctica
				if(survey.getLocalisation().equals(Continent.ANTARCTICA.toString())){
					numberOfSuggestions = 1;
					numberOfChoices = 1;
				}
			}
			random = new Random();

			// Remove the countries which have been given in previous suggestions
			if (suggestionsFromCache != null) {
				for (List<Suggestion> suggestionFromCache : suggestionsFromCache) {
					for (Suggestion suggestion : suggestionFromCache) {
						Country country = suggestion.getCountry();
						if (country != null && countryList.contains(country)) {
							countryList.remove(country);
						}
					}
				}
			}

			objectMapper = new ObjectMapper();
			iterationCountryAI = 0;
			// Iterate until enough countries are added by the AI or maximum iterations reached
			while ((countryList == null || countryList.size() < numberOfSuggestions) && iterationCountryAI < 10) {
				// Call the AI to add new countries based on the information on the survey

				//If you have a low RPM on your AI API Key, this prevents from sending too many requests to the AI
				if (timeLastRequest != null) {
					long millisecondBetweenRequest = millisecondsSince(timeLastRequest);
					if (millisecondBetweenRequest < minMillisecondBetweenRequest) {
						long waitTime = (minMillisecondBetweenRequest - millisecondBetweenRequest);
						try {
							Thread.sleep(waitTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				timeLastRequest = Instant.now();

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
						countryList = countryService.findAllByClimateListContaining(climate);
					}
				}else if (Continent.isValidValue(localisation.toUpperCase().replace(" ", "_"))){
					if (climate.equals(Climate.ALL)) {
						countryList = countryRepository.findAllByContinent(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")));
					} else {
						countryList = countryService.findCountryByContinentAndClimateListContaining(Continent.valueOf(localisation.toUpperCase().replace(" ", "_")), climate);
					}

				}

				// Remove the countries which have been given in previous suggestions
				if (suggestionsFromCache != null) {
					for (List<Suggestion> suggestionFromCache : suggestionsFromCache) {
						for (Suggestion suggestion : suggestionFromCache) {
							Country country = suggestion.getCountry();
							if (country != null && countryList!=null && countryList.contains(country)) {
								countryList.remove(country);
							}
						}
					}
				}

				iterationCountryAI++;
			}
			if(countryList!=null) {
				// Generate suggestions for the selected country
				for (int i = 0; i < numberOfSuggestions; i++) {
					if(!countryList.isEmpty()) {
						// Select a random country
						random = new Random();
						randomIndex = random.nextInt(countryList.size());
						countryDest = countryList.get(randomIndex);

						// Filter cities in the selected country based on criteria
						cityList = cityService.filterCitiesByCriteria(countryDest, landscape, temperature, climate);

						iterationCityAI = 0;
						// Iterate until enough cities are found or maximum iterations reached
						while ((cityList == null || cityList.size() < numberOfChoices) && iterationCityAI < 10) {

							//If you have a low RPM on your AI API Key, this prevents from sending too many requests to the AI
							if (timeLastRequest != null) {
								long millisecondBetweenRequest = millisecondsSince(timeLastRequest);
								if (millisecondBetweenRequest < minMillisecondBetweenRequest) {
									long waitTime = (minMillisecondBetweenRequest - millisecondBetweenRequest);
									try {
										Thread.sleep(waitTime);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
							timeLastRequest = Instant.now();

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
							cityList = cityService.filterCitiesByCriteria(countryDest, landscape, temperature, climate);
							iterationCityAI++;
						}
						if (cityList != null) {
							// Select a random city

							random = new Random();
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

								//If you have a low RPM on your AI API Key, this prevents from sending too many requests to the AI
								if (timeLastRequest != null) {
									long millisecondBetweenRequest = millisecondsSince(timeLastRequest);
									if (millisecondBetweenRequest < minMillisecondBetweenRequest) {
										long waitTime = (minMillisecondBetweenRequest - millisecondBetweenRequest);
										try {
											Thread.sleep(waitTime);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								}
								timeLastRequest = Instant.now();

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

							if (activityDest.size() >= 3) {
								activitySuggestion = new ArrayList<>();
								random = new Random();
								// Select 3 random activities from the list of suggested activities
								for (int j = 0; j < 3; j++) {

									int randomIndex3;
									do {
										randomIndex3 = random.nextInt(activityDest.size());
									} while (activitySuggestion.contains(activityDest.get(randomIndex3)));

									activitySuggestion.add(activityDest.get(randomIndex3));
								}

								Suggestion suggestion = new Suggestion(countryDest, cityDest, activitySuggestion, survey);
								suggList.add(suggestion);
							}
						}
						countryList.remove(randomIndex);
						// Remove selected country to avoid duplicates
					}
				}
			}
		}else if(RecognizedCountry.isValidValue(localisation.toUpperCase().replace(" ", "_"))){ //The user has specified a country, return 3 suggestions of different cities in the specified country
			// Retrieve the specified country based on name and climate
			if(climate.equals(Climate.ALL)){
				countryDest = countryRepository.findByCountryName(localisation);
			}else{
				countryDest = countryService.findCountryByCountryNameAndClimateListContaining(localisation, climate);
			}
			//Ask the AI if the country is not in the DB
			random = new Random();
			objectMapper = new ObjectMapper();

			iterationCountryAI = 0;
			// Iterate until the specified country is found in the database
			while(countryDest==null && iterationCountryAI<10) {
				// Call the AI to add the new country based on the information on the survey

				try{
					jsonString = aiService.chatCountry(survey);
					Country country = objectMapper.readValue(jsonString, Country.class);
					// Handle an error where the AI return erroneous values
					if(survey.getClimate() == Climate.MEDITERRANEAN && country.getContinent() != Continent.EUROPE && country.getContinent() != Continent.AFRICA){
						survey.setClimate(Climate.TEMPERATE);
						climate = Climate.TEMPERATE;
						suggestionsFromCache = cacheConfig.getSuggestionFromCache(survey);
						throw new IllegalArgumentException("MEDITERRANEAN Climate asked for a continent with no MEDITERRANEAN climate, Correction : The climate is changed from MEDITERRANEAN to TEMPERATE");
					}
					// Add suggested country to database
					countryService.addCountry(country);
				}catch(Exception e){
					e.printStackTrace();
				}
				if(climate.equals(Climate.ALL)){
					countryDest = countryRepository.findByCountryName(localisation);
				} else {
					countryDest = countryService.findCountryByCountryNameAndClimateListContaining(localisation, climate);
				}
				iterationCountryAI++;
			}

			if(countryDest!=null) {
				// Filter cities in the specified country based on criteria
				cityList = cityService.filterCitiesByCriteria(countryDest, landscape, temperature, climate);

				// Remove the cities which have been given in previous suggestions
				if (suggestionsFromCache != null) {
					for (List<Suggestion> suggestionFromCache : suggestionsFromCache) {
						for (Suggestion suggestion : suggestionFromCache) {
							City city = suggestion.getCity();
							if (city != null && cityList.contains(city)) {
								cityList.remove(city);
							}
						}
					}
				}

				iterationCityAI = 0;
				// Iterate until enough cities are found or maximum iterations reached
				while ((cityList == null || cityList.size() < numberOfSuggestions) && iterationCityAI < 10) {

					//If you have a low RPM on your AI API Key, this prevents from sending too many requests to the AI
					if (timeLastRequest != null) {
						long millisecondBetweenRequest = millisecondsSince(timeLastRequest);
						if (millisecondBetweenRequest < minMillisecondBetweenRequest) {
							long waitTime = (minMillisecondBetweenRequest - millisecondBetweenRequest);
							try {
								Thread.sleep(waitTime);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					timeLastRequest = Instant.now();

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
					cityList = cityService.filterCitiesByCriteria(countryDest, landscape, temperature, climate);
					iterationCityAI++;

					// Remove the cities which have been given in previous suggestions
					if (suggestionsFromCache != null) {
						for (List<Suggestion> suggestionFromCache : suggestionsFromCache) {
							for (Suggestion suggestion : suggestionFromCache) {
								City city = suggestion.getCity();
								if (city != null && cityList.contains(city)) {
									cityList.remove(city);
								}
							}
						}
					}

				}

				if(cityList!=null) {

					// Generate suggestions for each selected city
					for (int i = 0; i < numberOfSuggestions; i++) {

						if(!cityList.isEmpty()) {
							randomIndex2 = random.nextInt(cityList.size());
							cityDest = cityList.get(randomIndex2);
							activityDest = new ArrayList<>();
							// Retrieve activities in the selected city based on activity types
							for (ActivityType activityType1 : activityType) {
								activityDest.addAll(activityRepository.findAllByCityAndActivityType(cityDest, activityType1));
							}

							iterationActivityAI = 0;
							// Iterate until enough activities are found or maximum iterations reached
							while ((activityDest.size() < 3) && iterationActivityAI < 10) {
								// Call the AI to add new activities based on the information on the survey

								//If you have a low RPM on your AI API Key, this prevents from sending too many requests to the AI
								if (timeLastRequest != null) {
									long millisecondBetweenRequest = millisecondsSince(timeLastRequest);
									if (millisecondBetweenRequest < minMillisecondBetweenRequest) {
										long waitTime = (minMillisecondBetweenRequest - millisecondBetweenRequest);
										try {
											Thread.sleep(waitTime);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
								}
								timeLastRequest = Instant.now();

								try {
									jsonString = aiService.chatActivity(countryDest, cityDest, activityDest, survey);
									objectMapper = new ObjectMapper();

									City city = objectMapper.readValue(jsonString, City.class);
									cityService.addCity(city);
								} catch (Exception e) {
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

							if (activityDest.size() >= 3) {

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
							}
							// Remove selected city to avoid duplicates
							cityList.remove(randomIndex2);
						}
					}
				}
			}
		}else{
			throw new IllegalArgumentException("The localisation you have given in the survey is incorrect, please check the survey.");
		}
		if(!suggList.isEmpty()) {
			cacheConfig.putSuggestionInCache(survey, suggList);
		}

		// Return the list of suggestions
		return suggList;

	}
	//Calculates the number of milliseconds elapsed since the specified start instant.

	private long millisecondsSince(Instant start) {
		Instant end = Instant.now();
		return end.toEpochMilli()-start.toEpochMilli();
	}

}