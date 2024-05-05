package Repository;


import com.l3g1.apitraveller.model.Country;
import com.l3g1.apitraveller.model.enumeration.Climate;
import com.l3g1.apitraveller.model.enumeration.Continent;

import com.l3g1.apitraveller.repository.CountryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Test methods for CountryRepository
public class CountryRepositoryTest {
    Country country = Mockito.mock(Country.class);
    String countryName = country.getCountryName();
    Continent continent = Continent.ASIA;

    Climate climate = Climate.TROPICAL;

    CountryRepository countryRepository = Mockito.mock(CountryRepository.class);
    @Test
    @DisplayName("findByCountryName")
    public void findByCountryName(){
        when(countryRepository.findByCountryName(countryName)).thenReturn(country);
        Country country1= countryRepository.findByCountryName(countryName) ;
        assertEquals(country,country1);

    }
    @Test
    @DisplayName("deleteByCountryName")
    public void deleteByCountryName(){
        doNothing().when(countryRepository).deleteByCountryName(countryName);
        countryRepository.deleteByCountryName(countryName);
        verify(countryRepository, Mockito.times(1)).deleteByCountryName(countryName);



    }
    @Test
    @DisplayName("findAll")
    public void findAll(){
        List<Country>countryList = Collections.singletonList(country);
        when(countryRepository.findAll()).thenReturn(countryList);
        List<Country>resulTest = countryRepository.findAll();
        assertEquals(countryList,resulTest);

    }
    @Test
    @DisplayName("findAllByContinent")
    public void findAllByContinent(){
        List<Country>countryList = Collections.singletonList(country);
        when(countryRepository.findAllByContinent(continent)).thenReturn(countryList);
        List<Country>resulTest = countryRepository.findAllByContinent(continent);
        assertEquals(countryList,resulTest);

    }



}


