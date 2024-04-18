package com.l3g1.apitraveller.service;

import java.util.List;
import java.util.Optional;


import com.l3g1.apitraveller.model.Climate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.l3g1.apitraveller.model.Activity;
import com.l3g1.apitraveller.model.City;
import com.l3g1.apitraveller.model.Country;
import com.l3g1.apitraveller.repository.CityRepository;
import com.l3g1.apitraveller.repository.CountryRepository;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    // This method retrieves a country by its unique identifier.
    public Optional<Country> getCountryID(final Long id) {
        return countryRepository.findById(id);
    }

    // Retrieves all countries stored in the repository.
    public Iterable<Country> getCountry() {
        return countryRepository.findAll();
    }

    // Deletes a country from the repository by its name.
    @Transactional
    public void deleteCountry(final String countryName) {
        countryRepository.deleteByCountryName(countryName);
    }

    // Deletes all countries from the repository.
    public void deleteCountryAll() {
        countryRepository.deleteAll();
    }

    // Saves a country to the repository.
    public Country saveCountry(Country country) {
        Country savedCountry = countryRepository.save(country);
        return savedCountry;
    }

    // Adds a new country to the repository or updates an existing one.
    public Country addCountry(Country newCountry) {
        // Search if a country with the same name exists
        Country existingCountry;
        City existingCity;

        try {
            existingCountry = countryRepository.findByCountryName(newCountry.getCountryName());
        }catch (Exception e){
            e.printStackTrace();
            existingCountry = null;
        }

        if (existingCountry != null) {
            // If the country already exists, update its climates and add cities and activities
            List<Climate> existingClimates = existingCountry.getClimateList();
            List<Climate> newClimates = newCountry.getClimateList();

            for (Climate newClimate : newClimates) {
                if (!existingClimates.contains(newClimate)) {
                    existingClimates.add(newClimate);
                }
            }

            countryRepository.save(existingCountry);

            for (City city : newCountry.getCityList()) {
                // Check if the city exists in the existing country
                try {
                    existingCity = cityRepository.findByCountryAndCityName(existingCountry, city.getCityName());
                }catch (Exception e){
                    e.printStackTrace();
                    existingCity = null;
                }
                if (existingCity != null) {
                    // Update activities for the existing city
                    for (Activity activity : city.getActivityList()) {
                        if (!existingCity.getActivityList().contains(activity)) {
                            //activity.setCityName(existingCity.getCityName());
                            existingCity.getActivityList().add(activity);
                        }
                    }
                    cityService.saveCity(existingCity);
                } else {
                    // Add the city to the existing country
                    city.setCountry(existingCountry);
                    existingCountry.getCityList().add(city);
                }
            }
            // Save the existing country with the new city or update the activities of existing city
            return countryRepository.save(existingCountry);
        } else {
            // If the country does not exist, create a new one
            return countryRepository.save(newCountry);
        }
    }



}