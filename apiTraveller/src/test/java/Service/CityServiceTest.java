package Service;

import com.l3g1.apitraveller.controller.CityController;
import com.l3g1.apitraveller.model.Activity;
import com.l3g1.apitraveller.model.City;
import com.l3g1.apitraveller.model.Climate;
import com.l3g1.apitraveller.repository.CityRepository;
import com.l3g1.apitraveller.repository.CountryRepository;
import com.l3g1.apitraveller.service.CityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CityServiceTest {
    CityService cityService = Mockito.mock(CityService.class);
    CityController cityController = new CityController();

    CityRepository cityRepository = Mockito.mock(CityRepository.class);

    CountryRepository countryRepository = Mockito.mock(CountryRepository.class);


    Long id =1L;

    @DisplayName("CityService getcity(id)")
    @Test
    public void getCityTestbyId(){
        Optional<City> city1 = Optional.empty();
        assertEquals(city1,cityService.getCity(id));
    }
    @DisplayName("CityService getcity()")
    @Test
    public void getCityTest(){
        Iterable<City> city2 = new ArrayList<>();
        assertEquals(city2,cityService.getCity());

    }
    @DisplayName("Cityservice deletecity")
    @Test
    public void deletecity(){
        cityService.deleteCity(id);
        assertFalse(cityService.getCity(id).isPresent());
    }
   /*** @DisplayName("Cityservice addcity")
    @Test
    public void addcity(){
        City city =
        city.setCityName("Munich");
        city.setClimate(Climate.valueOf("TEMPARATE"));
        cityService.addCity(city);

    }
    ***/

}
