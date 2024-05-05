package Service;

import com.l3g1.apitraveller.controller.CityController;
import com.l3g1.apitraveller.model.City;
import com.l3g1.apitraveller.repository.CityRepository;
import com.l3g1.apitraveller.repository.CountryRepository;
import com.l3g1.apitraveller.service.CityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
//Test methods for CityService
public class CityServiceTest {
    CityService cityService = Mockito.mock(CityService.class);
    CityController cityController = new CityController();

    CityRepository cityRepository = Mockito.mock(CityRepository.class);

    CountryRepository countryRepository = Mockito.mock(CountryRepository.class);
    City city = Mockito.mock(City.class);


    Long id = city.getCityID();

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
    @DisplayName("Cityservice addcity")
    @Test
    public void addcity(){
        City city1 = new City();
        cityService.addCity(city1);
        assertFalse(cityService.getCity().equals(city1));

    }


}
