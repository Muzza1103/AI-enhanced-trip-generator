package Service;

import com.l3g1.apitraveller.model.*;
import com.l3g1.apitraveller.repository.ActivityRepository;
import com.l3g1.apitraveller.repository.CityRepository;
import com.l3g1.apitraveller.repository.CountryRepository;
import com.l3g1.apitraveller.service.AiService;
import com.l3g1.apitraveller.service.CountryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//Test methods for AiService
public class AiServiceTest {
    Survey survey = Mockito.mock(Survey.class);
    CountryRepository countryRepository = Mockito.mock(CountryRepository.class);
    AiService aiService = Mockito.mock(AiService.class);

    CountryService countryService = Mockito.mock(CountryService.class);

    CityRepository cityRepository = Mockito.mock(CityRepository.class);
    City city = Mockito.mock(City.class);
    Country country = Mockito.mock(Country.class);
    Activity activity =Mockito.mock(Activity.class);
    ActivityRepository activityRepository = Mockito.mock(ActivityRepository.class);
    TripSurvey tripSurvey =Mockito.mock(TripSurvey.class);


    @Test
    @DisplayName("String chatCountry Survey")
    public void chatCountry(){
        when(countryRepository.findAll()).thenReturn(new ArrayList<>());
        String TestResult = aiService.chatCountry(survey);
        List<Country>countryList = countryRepository.findAll();
        for(Country country : countryList){
            assertTrue(TestResult.contains(country.getCountryName()));
        }


    }
    @Test
    @DisplayName("String chatCity")
    public void chatCity(){
        when(countryRepository.findAll()).thenReturn(new ArrayList<>());
        when(cityRepository.findAllByCountry(country)).thenReturn(new ArrayList<>());

        String ResultCity = aiService.chatCity(country,survey);
        List<City> cityList =cityRepository.findAllByCountry(country);
        for(City city :cityList ){
            assertTrue(ResultCity.contains(city.getCityName()));

        }

    }
    @Test
    @DisplayName("String chatActivity")
    public void chatActivity(){
        when(countryRepository.findAll()).thenReturn(new ArrayList<>());
        when(cityRepository.findAllByCountry(country)).thenReturn(new ArrayList<>());
        when(activityRepository.findAllByCity(city)).thenReturn(new ArrayList<>());
        when(activityRepository.findAll()).thenReturn(new ArrayList<>());
        String resultActivity = aiService.chatActivity(country,city,activityRepository.findAllByCity(city),survey);
        List<Activity>activityList = activityRepository.findAll();
        for(Activity activity : activityList){
            assertTrue(resultActivity.contains(activity.getActivityName()));
        }

    }
    @Test
    @DisplayName("String chatTripCountry ")
    public void chatTripCountry() {
        when(countryRepository.findAll()).thenReturn(new ArrayList<>());
        String resultTripCountry = aiService.chatTripCountry(tripSurvey);
        List<Country>countryList = countryRepository.findAll();
        for(Country country : countryList){
            assertTrue(resultTripCountry.contains(country.getCountryName()));
        }

    }
    @Test
    @DisplayName("String chatTripCity")
    public void chatTripCity(){
        when(countryRepository.findAll()).thenReturn(new ArrayList<>());
        when(cityRepository.findAllByCountry(country)).thenReturn(new ArrayList<>());
        String resultTripCity = aiService.chatTripCity(country,tripSurvey);
        List<City> cityList =cityRepository.findAllByCountry(country);
        for(City city :cityList ){
            assertTrue(resultTripCity.contains(city.getCityName()));

        }

    }
    @Test
    @DisplayName("String chatTripActivity")
    public void chatTripActivity() {
        int lower = 0;
        int upper = 50;
        when(countryRepository.findAll()).thenReturn(new ArrayList<>());
        when(cityRepository.findAllByCountry(country)).thenReturn(new ArrayList<>());
        when(activityRepository.findAllByCity(city)).thenReturn(new ArrayList<>());
        when(activityRepository.findAll()).thenReturn(new ArrayList<>());
        String resultTripActivity = aiService.chatTripActivity(country, city, activityRepository.findAllByCity(city), tripSurvey, lower, upper);
        List<Activity> activityList = activityRepository.findAll();
        for (Activity activity : activityList) {
            assertTrue(resultTripActivity.contains(activity.getActivityName()));


        }

    }




}
