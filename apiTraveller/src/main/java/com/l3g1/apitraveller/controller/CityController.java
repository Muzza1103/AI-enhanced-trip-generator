package com.l3g1.apitraveller.controller;

import java.util.Optional;
import com.l3g1.apitraveller.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.l3g1.apitraveller.service.CityService;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    // Get all cities
    @GetMapping("/get")
    public Iterable<City> getCity() {
        return cityService.getCity();
    }

    // Get city by ID
    @GetMapping("/get/{id}")
    public Optional<City> getCity(@PathVariable Long id) {
        return cityService.getCity(id);
    }

    // Save or update a city
    @PutMapping("/save")
    public City saveCity(@RequestBody City city) {
        return cityService.saveCity(city);
    }

    // Add a new city or update an existing city with error handling
    @PutMapping("/upsert")
    public ResponseEntity<String> addCity(@RequestBody City city) {
        try {
            cityService.addCity(city);
            return ResponseEntity.ok("Data added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add city: " + e.getMessage());
        }
    }

    // Delete city by ID
    @DeleteMapping("/delete/{id}")
    public void deleteCountry(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}
