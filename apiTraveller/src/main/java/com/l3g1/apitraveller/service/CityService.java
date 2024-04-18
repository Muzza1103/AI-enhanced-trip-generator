package com.l3g1.apitraveller.service;

import java.util.Optional;


import com.l3g1.apitraveller.model.Country;
import com.l3g1.apitraveller.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.l3g1.apitraveller.model.City;
import com.l3g1.apitraveller.repository.CityRepository;

import lombok.Data;

@Data
@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    //Retrieves a city by its unique identifier.
    public Optional<City> getCity(final Long id) {
        return cityRepository.findById(id);
    }

    //Retrieves all cities stored in the repository.
    public Iterable<City> getCity() {
        return cityRepository.findAll();
    }

    //Deletes a city from the repository by its unique identifier.
    public void deleteCity(final Long id) {
        cityRepository.deleteById(id);
    }

    //Saves a city to the repository.
    public City saveCity(City city) {
        City savedCity = cityRepository.save(city);
        return savedCity;
    }

    // Adds a new city to the database, or updates an existing city if it already exists.
    public void addCity(City newCity) {
        try {
            // Check if the city already exists in the database
            City existingCity = cityRepository.findByCountryCountryNameAndCityName(newCity.getCountry().getCountryName(), newCity.getCityName());

            if (existingCity!= null) {
                // If the city already exists, add the activities to the existing city
                existingCity.getActivityList().addAll(newCity.getActivityList());
                cityRepository.save(existingCity);

            } else {
                // Check if the country of the new city already exists
                Country existingCountry = countryRepository.findByCountryName(newCity.getCountry().getCountryName());

                if (existingCountry!= null) {
                    // If the country already exists, associate the new city with the existing country
                    // and add the climate value of newCity to the list of climates of the existing country
                    newCity.setCountry(existingCountry);
                    existingCountry.getCityList().add(newCity);

                    // Add the climate value of newCity to the list of climates of the existing country
                    if (!existingCountry.getClimateList().contains(newCity.getClimate())) {
                        existingCountry.getClimateList().add(newCity.getClimate());
                    }

                    // Save the country and the city at the same time as both are interdependant
                    countryRepository.save(existingCountry);

                } else {
                    // If the country does not exist yet, save the new country and associate it with the new city
                    existingCountry = countryRepository.save(newCity.getCountry());
                    newCity.setCountry(existingCountry);
                    existingCountry.addClimateList(newCity.getClimate());
                    existingCountry.getCityList().add(newCity);

                    // Save the country and the city at the same time as both are interdependant
                    countryRepository.save(existingCountry);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}