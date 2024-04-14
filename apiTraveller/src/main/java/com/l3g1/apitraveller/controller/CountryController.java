package com.l3g1.apitraveller.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

import com.l3g1.apitraveller.model.Country;
import com.l3g1.apitraveller.service.CountryService;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    // GET endpoint to retrieve all countries
    @GetMapping("/get")
    public Iterable<Country> getCountry() {
        return countryService.getCountry();
    }

    // GET endpoint to retrieve a country by ID
    @GetMapping("/get/{id}")
    public Optional<Country> getCountryID(@PathVariable Long id) {
        return countryService.getCountryID(id);
    }

    // PUT endpoint to save a country with error handling
    @PutMapping("/save")
    public ResponseEntity<String> saveCountry(@RequestBody Country country) {
        try {
            countryService.saveCountry(country);
            return ResponseEntity.ok("Country saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to save country: " + e.getMessage());
        }
    }

    // PUT endpoint to add or update a country with error handling
    @PutMapping("/upsert")
    public ResponseEntity<String> addCountry(@RequestBody Country country) {
        try {
            countryService.addCountry(country);
            return ResponseEntity.ok("Data added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add country: " + e.getMessage());
        }
    }

    // DELETE endpoint to delete a country by name with error handling
    @DeleteMapping("/delete/{countryName}")
    public ResponseEntity<String> deleteCountry(@PathVariable String countryName) {
        try {
            countryService.deleteCountry(countryName);
            return ResponseEntity.ok("Country with name " + countryName + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete country with name " + countryName + ": " + e.getMessage());
        }
    }

    // DELETE endpoint to delete all countries with error handling
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteCountry() {
        try {
            countryService.deleteCountryAll();
            return ResponseEntity.ok("All countries deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete countries: " + e.getMessage());
        }
    }

}
