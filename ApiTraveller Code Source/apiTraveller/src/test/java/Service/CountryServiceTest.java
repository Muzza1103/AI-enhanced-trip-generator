package Service;

import com.l3g1.apitraveller.model.Country;
import com.l3g1.apitraveller.model.enumeration.Climate;
import com.l3g1.apitraveller.model.enumeration.Continent;
import com.l3g1.apitraveller.repository.CountryRepository;
import com.l3g1.apitraveller.service.CountryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
//Test methods for CountryService
public class CountryServiceTest {
    CountryRepository countryRepository = Mockito.mock(CountryRepository.class);
    CountryService countryService = Mockito.mock(CountryService.class);
    Country country = Mockito.mock(Country.class);

    String countryName = country.getCountryName();
    Continent continent = Continent.ASIA;

    Climate climate = Climate.TROPICAL;

    Long id = country.getCountryID();

    @Test
    @DisplayName("get country id")

    public void getCountryId() {
        Optional<Country> country1 = Optional.empty();
        assertEquals(country1, countryService.getCountryID(id));
    }

    @Test
    @DisplayName(" get Country")
    public void getCountry() {
        Iterable<Country> country2 = new ArrayList<>();
        assertEquals(country2, countryService.getCountry());
    }

    @Test
    @DisplayName("deleteCountry")
    public void deleteCountry() {
        String countryName = country.getCountryName();
        doNothing().when(countryService).deleteCountry(countryName);
        countryService.deleteCountry(countryName);
        assertFalse(countryService.getCountryID(id).isPresent());

    }

    @Test
    @DisplayName("saveCountry")
    public void saveCountry() {
        when(countryRepository.save(country)).thenReturn(country);
        when(countryService.saveCountry(country)).thenReturn(country);
        assertEquals(countryService.saveCountry(country), countryRepository.save(country));
    }

    @Test
    @DisplayName("addCountry")
    public void addCountry() {
        Country country1 = new Country();
        countryService.addCountry(country1);
        assertFalse(countryService.getCountry().equals(country1));

    }

    @Test
    @DisplayName("findCountryByCountryNameAndClimateListContaining")
    public void findCountryByCountryNameAndClimateListContaining() {
        when(countryService.findCountryByCountryNameAndClimateListContaining(countryName, climate)).thenReturn(country);
        Country country1 = countryService.findCountryByCountryNameAndClimateListContaining(countryName, climate);
        assertEquals(country, country1);

    }

    @Test
    @DisplayName("findAllByClimateListContaining")
    public void findAllByClimateListContaining() {
        List<Country> countryList = new ArrayList<>();
        when(countryService.findAllByClimateListContaining(climate)).thenReturn(countryList);
        List<Country> testList = countryService.findAllByClimateListContaining(climate);
        assertEquals(countryList, testList);

    }

    @Test
    @DisplayName("findCountryByContinentAndClimateListContaining")
    public void findCountryByContinentAndClimateListContaining() {
        List<Country> countryList = new ArrayList<>();
        when(countryService.findCountryByContinentAndClimateListContaining(continent,climate)).thenReturn(countryList);
        List<Country>testList =countryService.findCountryByContinentAndClimateListContaining(continent,climate);
        assertEquals(countryList,testList);

    }
}

