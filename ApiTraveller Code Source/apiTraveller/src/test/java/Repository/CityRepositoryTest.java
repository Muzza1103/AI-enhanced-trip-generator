package Repository;

import com.l3g1.apitraveller.model.City;
import com.l3g1.apitraveller.model.Country;
import com.l3g1.apitraveller.model.enumeration.Climate;
import com.l3g1.apitraveller.model.enumeration.Landscape;
import com.l3g1.apitraveller.repository.CityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//Test methods for CityRepository
public class CityRepositoryTest {
    City city = Mockito.mock(City.class);
    Country country =Mockito.mock(Country.class);
    CityRepository cityRepository = Mockito.mock(CityRepository.class);
    Landscape landscape = Landscape.FOREST;
    Climate climate = Climate.DESERT;

    String cityName = city.getCityName();
    String countryName = country.getCountryName();

    @Test
    @DisplayName("findAllByCountry")
    public void findAllByCountry(){
        List<City>countryList = Collections.singletonList(city);
        when(cityRepository.findAllByCountry(country)).thenReturn(countryList);
        List<City>resulTest = cityRepository.findAllByCountry(country);
        assertEquals(countryList,resulTest);

    }
    @Test
    @DisplayName("findAllByCountryAndLandscape")
    public void findAllByCountryAndLandscape(){
        List<City>countryList = Collections.singletonList(city);
        when(cityRepository.findAllByCountryAndLandscape(country,landscape)).thenReturn(countryList);
        List<City>resulTest = cityRepository.findAllByCountryAndLandscape(country,landscape);
        assertEquals(countryList,resulTest);

    }

    @Test
    @DisplayName("findAllByCountryAndLandscapeAndClimate")
    public void findAllByCountryAndLandscapeAndClimate(){
        List<City>countryList = Collections.singletonList(city);
        when(cityRepository.findAllByCountryAndLandscapeAndClimate(country,landscape,climate)).thenReturn(countryList);
        List<City>resulTest = cityRepository.findAllByCountryAndLandscapeAndClimate(country,landscape,climate);
        assertEquals(countryList,resulTest);
    }

    @Test
    @DisplayName("findAllByCountryAndClimate")
    public void findAllByCountryAndClimate(){
        List<City>countryList = Collections.singletonList(city);
        when(cityRepository.findAllByCountryAndClimate(country,climate)).thenReturn(countryList);
        List<City>resulTest = cityRepository.findAllByCountryAndClimate(country,climate);
        assertEquals(countryList,resulTest);
    }

    @Test
    @DisplayName("findByCountryAndCityName")
    public void findByCountryAndCityName(){
        when(cityRepository.findByCountryAndCityName(country,cityName)).thenReturn(city);
        City city1= cityRepository.findByCountryAndCityName(country,cityName);
        assertEquals(city,city1);
    }

    @Test
    @DisplayName("findByCountryCountryNameAndCityName")
    public void findByCountryCountryNameAndCityName(){
        when(cityRepository.findByCountryCountryNameAndCityName(countryName,cityName)).thenReturn(city);
        City city1= cityRepository.findByCountryCountryNameAndCityName(countryName,cityName);
        assertEquals(city,city1);
    }
}
