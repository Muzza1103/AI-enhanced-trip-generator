package Controller;


import com.l3g1.apitraveller.controller.CountryController;
import com.l3g1.apitraveller.model.Country;
import com.l3g1.apitraveller.service.CountryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CountryControllerTest {
    //  Test methode for  CountryController

    CountryController countryController = Mockito.mock(CountryController.class);

    CountryService countryService = Mockito.mock(CountryService.class);
    Country country = Mockito.mock(Country.class);
    Long id = country.getCountryID();

    String countryName= country.getCountryName();




    @DisplayName("getCountry")
    @Test
    public void getCountry(){
        when(countryService.getCountry()).thenReturn(new ArrayList<>());
        Iterable<Country> resultCountry = countryController.getCountry();
        assertEquals(resultCountry,countryService.getCountry());


    }
    @DisplayName("getCountryId")
    @Test
    public void getCountryId(){
        when(countryController.getCountryID(id)).thenReturn(Optional.of(country));
        Optional<Country> resultCountry = countryController.getCountryID(id);
        assertEquals(Optional.of(country),resultCountry);


    }
    @DisplayName("saveCountry with Succes")
    @Test
    public void saveCountry_Succes(){
        ResponseEntity<String>response = new ResponseEntity<>("Data saved successfully",HttpStatus.OK);
        when(countryController.saveCountry(country)).thenReturn(response);
        ResponseEntity<String>resultTest = countryController.saveCountry(country);
        assertEquals(HttpStatus.OK, resultTest .getStatusCode());
        assertEquals("Data saved successfully", resultTest.getBody());

    }

    @DisplayName("saveCountry with Failure")
    @Test
    public void saveCountry_Faillure(){
        ResponseEntity<String>response = new ResponseEntity<>("Failed to save country: "+country.getCountryName(),HttpStatus.BAD_REQUEST);
        when(countryController.saveCountry(country)).thenReturn(response);
        ResponseEntity<String>resulTest = countryController.saveCountry(country);
        assertEquals(HttpStatus.BAD_REQUEST,resulTest.getStatusCode());
        assertTrue(resulTest.getBody().contains("Failed to save country: "+country.getCountryName()));

    }
    @DisplayName("addCountry with Succes")
    @Test
    public void addCountry_Succes(){
        Country country1 = new Country();
        ResponseEntity<String>response = new ResponseEntity<>("Data added successfully",HttpStatus.OK);
        when(countryController.addCountry(country1)).thenReturn(response);
        ResponseEntity<String>resultTest = countryController.addCountry(country1);
        assertEquals(HttpStatus.OK, resultTest .getStatusCode());
        assertEquals("Data added successfully", resultTest.getBody());

    }

    @DisplayName("addCountry with Failure")
    @Test
    public void addCountry_Faillure(){
        Country country1 = new Country();
        ResponseEntity<String>response = new ResponseEntity<>("Failed to add country: "+country.getCountryName(),HttpStatus.BAD_REQUEST);
        when(countryController.addCountry(country1)).thenReturn(response);
        ResponseEntity<String>resulTest = countryController.addCountry(country1);
        assertEquals(HttpStatus.BAD_REQUEST,resulTest.getStatusCode());
        assertTrue(resulTest.getBody().contains("Failed to add country: "+country1.getCountryName()));

    }

    @DisplayName("deleteCountryByCountryName with Succes")
    @Test
    public void deleteCountryByCountryName_Succes(){
        ResponseEntity<String>response = new ResponseEntity<>("Data delete successfully",HttpStatus.OK);
        when(countryController.deleteCountry(countryName)).thenReturn(response);
        ResponseEntity<String>resultTest = countryController.deleteCountry(countryName);
        assertEquals(HttpStatus.OK, resultTest .getStatusCode());
        assertEquals("Data delete successfully", resultTest.getBody());

    }

    @DisplayName("deleteCountryByCountryName with Failure")
    @Test
    public void deleteCountryByCountryName_Faillure(){
        ResponseEntity<String>response = new ResponseEntity<>("Failed to delete country: "+country.getCountryName(),HttpStatus.BAD_REQUEST);
        when(countryController.deleteCountry(countryName)).thenReturn(response);
        ResponseEntity<String>resulTest = countryController.deleteCountry(countryName);
        assertEquals(HttpStatus.BAD_REQUEST,resulTest.getStatusCode());
        assertTrue(resulTest.getBody().contains("Failed to delete country: "+country.getCountryName()));

    }
    @DisplayName("deleteCountry with Succes")
    @Test
    public void deleteCountry_Succes(){
        ResponseEntity<String>response = new ResponseEntity<>("Data delete successfully",HttpStatus.OK);
        when(countryController.deleteCountry()).thenReturn(response);
        ResponseEntity<String>resultTest = countryController.deleteCountry();
        assertEquals(HttpStatus.OK, resultTest .getStatusCode());
        assertEquals("Data delete successfully", resultTest.getBody());

    }
    @DisplayName("deleteCountry with Failure")
    @Test
    public void deleteCountry_Faillure(){
        ResponseEntity<String>response = new ResponseEntity<>("Failed to delete country: "+country.getCountryName(),HttpStatus.BAD_REQUEST);
        when(countryController.deleteCountry()).thenReturn(response);
        ResponseEntity<String>resulTest = countryController.deleteCountry();
        assertEquals(HttpStatus.BAD_REQUEST,resulTest.getStatusCode());
        assertTrue(resulTest.getBody().contains("Failed to delete country: "+country.getCountryName()));

    }







}
