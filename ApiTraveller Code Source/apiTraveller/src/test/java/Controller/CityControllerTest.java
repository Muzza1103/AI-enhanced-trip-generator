package Controller;

import com.l3g1.apitraveller.controller.CityController;
import com.l3g1.apitraveller.model.City;
import com.l3g1.apitraveller.service.CityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CityControllerTest {
    // Test methode for  AuthController
    CityController cityController = Mockito.mock(CityController.class);
    CityService cityService=Mockito.mock(CityService.class);

    City city = Mockito.mock(City.class);
    Long id = city.getCityID();


    @DisplayName("getCity")
    @Test
    public void getCity(){
        when(cityService.getCity()).thenReturn(new ArrayList<>());
        Iterable<City> resultCity = cityController.getCity();
        assertEquals(resultCity,cityService.getCity());
}
    @DisplayName("getCityById")
    @Test
    public void getCityById(){
        when(cityService.getCity(id)).thenReturn(Optional.of(city));
        when(cityController.getCity(id)).thenReturn(Optional.of(city));
        Optional<City> resultCity = cityController.getCity(id);
        assertEquals(resultCity,cityService.getCity(id));
    }

    @DisplayName("saveCity")
    @Test
    public void saveCity(){
        when(cityService.saveCity(city)).thenReturn(city);
        when(cityController.saveCity(city)).thenReturn(city);
        City city1 = cityController.saveCity(city);
        assertEquals(city,city1);
    }

    @DisplayName("addCity with Succes")
    @Test
    public void addCity_Succes(){
        City city1 = new City();
        ResponseEntity<String> response = new ResponseEntity<>("Data added successfully", HttpStatus.OK);
        when(cityController.addCity(city1)).thenReturn(response);
        ResponseEntity<String>resultTest = cityController.addCity(city1);
        assertEquals(HttpStatus.OK, resultTest .getStatusCode());
        assertEquals("Data added successfully", resultTest.getBody());
    }

    @DisplayName("addCity with  Faillure")
    @Test
    public void addCity_Faillure(){
        City city1 = new City();
        ResponseEntity<String> response = new ResponseEntity<>("failed to add city"+city1.getCityName(), HttpStatus.BAD_REQUEST);
        when(cityController.addCity(city1)).thenReturn(response);
        ResponseEntity<String>resultTest = cityController.addCity(city1);
        assertEquals(HttpStatus.BAD_REQUEST, resultTest .getStatusCode());
        assertEquals("failed to add city"+city1.getCityName(), resultTest.getBody());
    }

    @DisplayName("deleteCountry")
    @Test
    public void deleteCountry(){
        long id = city.getCityID();
        doNothing().when(cityController).deleteCountry(id);
        doNothing().when(cityService).deleteCity(id);
        cityController.deleteCountry(id);
        assertFalse(cityService.getCity(city.getCityID()).isPresent());


    }
}
