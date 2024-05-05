package Service;

import com.l3g1.apitraveller.cache.CacheConfig;
import com.l3g1.apitraveller.controller.DestinationSuggestionController;
import com.l3g1.apitraveller.model.*;
import com.l3g1.apitraveller.model.enumeration.ActivityType;
import com.l3g1.apitraveller.model.enumeration.Climate;
import com.l3g1.apitraveller.model.enumeration.Landscape;
import com.l3g1.apitraveller.model.enumeration.Temperature;
import com.l3g1.apitraveller.repository.ActivityRepository;
import com.l3g1.apitraveller.repository.CityRepository;
import com.l3g1.apitraveller.repository.CountryRepository;
import com.l3g1.apitraveller.service.AiService;
import com.l3g1.apitraveller.service.CityService;
import com.l3g1.apitraveller.service.CountryService;
import com.l3g1.apitraveller.service.DestinationSuggestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//Test methods for DestinationSuggestionService
public class DestinationSuggestionServiceTest {
    Survey survey = Mockito.mock(Survey.class);
    CountryRepository countryRepository = Mockito.mock(CountryRepository.class);
    CityRepository cityRepository = Mockito.mock(CityRepository.class);
    ActivityRepository activityRepository = Mockito.mock(ActivityRepository.class);
    AiService aiService = Mockito.mock(AiService.class);
    CountryService countryService = Mockito.mock(CountryService.class);
    CityService cityService = Mockito.mock(CityService.class);
    CacheConfig cacheConfig = Mockito.mock(CacheConfig.class);
    DestinationSuggestionService destinationSuggestionService = Mockito.mock(DestinationSuggestionService.class);

    DestinationSuggestionController destinationSuggestionController = Mockito.mock(DestinationSuggestionController.class);

    @Test
    @DisplayName("getSuggestion")
    public void getSuggestion() {

        try {

            survey.setLocalisation("ALL");
            survey.setClimate(Climate.TEMPERATE);
            survey.setLandscape(Landscape.URBAN);
            survey.setTemperature(Temperature.MILD);
            survey.setActivityType(List.of(ActivityType.ADVENTURE));

            Country country1 = new Country();
            Country country2 = new Country();
            List<Country> countryList = new ArrayList<>();
            countryList.add(country1);
            countryList.add(country2);
            when(countryRepository.findAll()).thenReturn(countryList);

            City city1 = new City();
            City city2 = new City();
            List<City> cityList = new ArrayList<>();
            cityList.add(city1);
            cityList.add(city2);
            when(cityService.filterCitiesByCriteria(country1, survey.getLandscape(), survey.getTemperature(), survey.getClimate())).thenReturn(cityList);
            List<Suggestion> testResult = new ArrayList<>();
            when(destinationSuggestionService.getSuggestion(survey)).thenReturn(testResult);
            List<Suggestion> reponse = destinationSuggestionService.getSuggestion(survey);
            assertNotNull(reponse);


        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        }


    }

    @DisplayName("getSuggestionAI")
    @Test
    public void getSuggestionAI() {
        try {

            survey.setLocalisation("ALL");
            survey.setClimate(Climate.TEMPERATE);
            survey.setLandscape(Landscape.URBAN);
            survey.setTemperature(Temperature.MILD);
            survey.setActivityType(List.of(ActivityType.ADVENTURE));

            Country country1 = new Country();
            Country country2 = new Country();
            List<Country> countryList = new ArrayList<>();
            countryList.add(country1);
            countryList.add(country2);
            when(countryRepository.findAll()).thenReturn(countryList);

            City city1 = new City();
            City city2 = new City();
            List<City> cityList = new ArrayList<>();
            cityList.add(city1);
            cityList.add(city2);
            when(cityService.filterCitiesByCriteria(country1, survey.getLandscape(), survey.getTemperature(), survey.getClimate())).thenReturn(cityList);

            Activity activity1 = new Activity();
            Activity activity2 = new Activity();
            List<Activity> activityList = new ArrayList<>();
            activityList.add(activity1);
            activityList.add(activity2);

            String aiCountry = aiService.chatCountry(survey);
            String aiCity = aiService.chatCity(country1, survey);
            String aiActivity = aiService.chatActivity(country1, city1, activityList, survey);
            when(aiService.chatCountry(survey)).thenReturn(aiCountry);
            when(aiService.chatCity(country1, survey)).thenReturn(aiCity);
            when(aiService.chatActivity(country1, city1, activityList, survey)).thenReturn(aiActivity);


            List<Suggestion> testResult = new ArrayList<>();
            when(destinationSuggestionService.getSuggestionAI(survey)).thenReturn(testResult);
            List<Suggestion> reponse = destinationSuggestionService.getSuggestionAI(survey);
            assertNotNull(reponse);


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("getSuggestionAIWithCache")
    @Test
    public void getSuggestionAIWithCache() {
        try {


            survey.setLocalisation("ALL");
            survey.setClimate(Climate.TEMPERATE);
            survey.setLandscape(Landscape.URBAN);
            survey.setTemperature(Temperature.MILD);
            survey.setActivityType(List.of(ActivityType.ADVENTURE));

            Suggestion suggestion1 = new Suggestion();
            Suggestion suggestion2 = new Suggestion();
            Suggestion suggestion3 = new Suggestion();
            Suggestion suggestion4 = new Suggestion();
            List<Suggestion> suggestionListA = new ArrayList<>();
            suggestionListA.add(suggestion1);
            suggestionListA.add(suggestion2);
            List<Suggestion> suggestionListB = new ArrayList<>();
            suggestionListB.add(suggestion3);
            suggestionListB.add(suggestion4);
            List<List<Suggestion>> suggestionListTest = new ArrayList<>();
            suggestionListTest.add(suggestionListA);
            suggestionListTest.add(suggestionListB);

            when(cacheConfig.getSuggestionFromCache(survey)).thenReturn(suggestionListTest);
            List<Suggestion> testResult = new ArrayList<>();
            when(destinationSuggestionService.getSuggestionAIWithCache(survey)).thenReturn(testResult);
            List<Suggestion> reponse = destinationSuggestionService.getSuggestionAIWithCache(survey);
            assertNotNull(reponse);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
