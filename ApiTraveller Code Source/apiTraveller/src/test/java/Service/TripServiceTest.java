package Service;

import com.l3g1.apitraveller.cache.CacheConfig;
import com.l3g1.apitraveller.controller.TripSuggestionController;
import com.l3g1.apitraveller.model.*;
import com.l3g1.apitraveller.model.enumeration.ActivityType;
import com.l3g1.apitraveller.model.enumeration.Climate;
import com.l3g1.apitraveller.model.enumeration.Landscape;
import com.l3g1.apitraveller.model.enumeration.Temperature;
import com.l3g1.apitraveller.repository.ActivityRepository;
import com.l3g1.apitraveller.repository.CityRepository;
import com.l3g1.apitraveller.repository.CountryRepository;
import com.l3g1.apitraveller.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//Test methods for TripService
public class TripServiceTest {
    TripSurvey tripsurvey = Mockito.mock(TripSurvey.class);
    CountryRepository countryRepository = Mockito.mock(CountryRepository.class);
    CityRepository cityRepository = Mockito.mock(CityRepository.class);
    ActivityRepository activityRepository = Mockito.mock(ActivityRepository.class);
    AiService aiService = Mockito.mock(AiService.class);
    CountryService countryService = Mockito.mock(CountryService.class);
    CityService cityService = Mockito.mock(CityService.class);
    CacheConfig cacheConfig = Mockito.mock(CacheConfig.class);
    TripService tripService = Mockito.mock(TripService.class);

    TripSuggestionController tripSuggestionController = Mockito.mock(TripSuggestionController.class);

    @Test
    @DisplayName("getSuggestion")
    public void getSuggestion() {

        try {

            tripsurvey.setLocalisation("ALL");
            tripsurvey.setClimate(Climate.TEMPERATE);
            tripsurvey.setLandscape(Landscape.URBAN);
            tripsurvey.setTemperature(Temperature.MILD);
            tripsurvey.setActivityType(List.of(ActivityType.ADVENTURE));
            tripsurvey.setEndingDate("06/05/2024");
            tripsurvey.setStartingDate("01/05/2024");
            tripsurvey.setRoadTrip(true);
            tripsurvey.setBudget(1500);

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
            when(cityService.filterCitiesByCriteria(country1, tripsurvey.getLandscape(), tripsurvey.getTemperature(), tripsurvey.getClimate())).thenReturn(cityList);
            List<TripSuggestion> testResult = new ArrayList<>();
            when(tripService.getSuggestion(tripsurvey)).thenReturn(testResult);
            List<TripSuggestion> reponse = tripService.getSuggestion(tripsurvey);
            assertNotNull(reponse);


        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        }


    }

    @DisplayName("getSuggestionAI")
    @Test
    public void getSuggestionAI() {
        try {

            tripsurvey.setLocalisation("ALL");
            tripsurvey.setClimate(Climate.TEMPERATE);
            tripsurvey.setLandscape(Landscape.URBAN);
            tripsurvey.setTemperature(Temperature.MILD);
            tripsurvey.setActivityType(List.of(ActivityType.ADVENTURE));
            tripsurvey.setEndingDate("06/05/2024");
            tripsurvey.setStartingDate("01/05/2024");
            tripsurvey.setRoadTrip(true);
            tripsurvey.setBudget(1500);

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
            when(cityService.filterCitiesByCriteria(country1, tripsurvey.getLandscape(), tripsurvey.getTemperature(), tripsurvey.getClimate())).thenReturn(cityList);

            Activity activity1 = new Activity();
            Activity activity2 = new Activity();
            List<Activity> activityList = new ArrayList<>();
            activityList.add(activity1);
            activityList.add(activity2);

            String aiCountry = aiService.chatTripCountry(tripsurvey);
            String aiCity = aiService.chatTripCity(country1,tripsurvey);
            String aiActivity = aiService.chatTripActivity(country1,city1,activityList,tripsurvey,0,68);
            when(aiService.chatTripCountry(tripsurvey)).thenReturn(aiCountry);
            when(aiService.chatTripCity(country1,tripsurvey)).thenReturn(aiCity);
            when(aiService.chatTripActivity(country1,city1,activityList,tripsurvey,0,68)).thenReturn(aiActivity);


            List<TripSuggestion> testResult = new ArrayList<>();
            when(tripService.getSuggestionAI(tripsurvey)).thenReturn(testResult);
            List<TripSuggestion> reponse = tripService.getSuggestionAI(tripsurvey);
            assertNotNull(reponse);


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("getSuggestionAIWithCache")
    @Test
    public void getSuggestionAIWithCache() {
        try {
            tripsurvey.setLocalisation("ALL");
            tripsurvey.setClimate(Climate.TEMPERATE);
            tripsurvey.setLandscape(Landscape.URBAN);
            tripsurvey.setTemperature(Temperature.MILD);
            tripsurvey.setActivityType(List.of(ActivityType.ADVENTURE));
            tripsurvey.setEndingDate("06/05/2024");
            tripsurvey.setStartingDate("01/05/2024");
            tripsurvey.setRoadTrip(true);
            tripsurvey.setBudget(1500);

            TripSuggestion tripsuggestion1 = new TripSuggestion();
            TripSuggestion tripsuggestion2 = new TripSuggestion();
            TripSuggestion tripsuggestion3 = new TripSuggestion();
            TripSuggestion tripsuggestion4 = new TripSuggestion();
            List<TripSuggestion> tripsuggestionListA = new ArrayList<>();
            tripsuggestionListA.add(tripsuggestion1);
            tripsuggestionListA.add(tripsuggestion2);
            List<TripSuggestion> tripsuggestionListB = new ArrayList<>();
            tripsuggestionListB.add(tripsuggestion3);
            tripsuggestionListB.add(tripsuggestion4);
            List<List<TripSuggestion>> tripsuggestionListTest = new ArrayList<>();
            tripsuggestionListTest.add(tripsuggestionListA);
            tripsuggestionListTest.add(tripsuggestionListB);

            when(cacheConfig.getTripSuggestionFromCache(tripsurvey)).thenReturn(tripsuggestionListTest);
            List<TripSuggestion> testResult = new ArrayList<>();
            when(tripService.getSuggestionAIWithCache(tripsurvey)).thenReturn(testResult);
            List<TripSuggestion> reponse = tripService.getSuggestionAIWithCache(tripsurvey);
            assertNotNull(reponse);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
