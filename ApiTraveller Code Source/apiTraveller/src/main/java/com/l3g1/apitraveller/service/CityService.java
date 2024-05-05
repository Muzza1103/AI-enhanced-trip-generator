package com.l3g1.apitraveller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.l3g1.apitraveller.model.Country;
import com.l3g1.apitraveller.model.enumeration.Climate;
import com.l3g1.apitraveller.model.enumeration.Landscape;
import com.l3g1.apitraveller.model.enumeration.Temperature;
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
                for(Temperature temp : newCity.getTemperatureList()){
                    if(!existingCity.getTemperatureList().contains(temp)){
                        existingCity.addTemperature(temp);
                    }
                }
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

                    // Save the country and the city at the same time as both are interdependent
                    countryRepository.save(existingCountry);

                } else {
                    // If the country does not exist yet, save the new country and associate it with the new city
                    existingCountry = countryRepository.save(newCity.getCountry());
                    newCity.setCountry(existingCountry);
                    existingCountry.addClimateList(newCity.getClimate());
                    existingCountry.getCityList().add(newCity);

                    // Save the country and the city at the same time as both are interdependent
                    countryRepository.save(existingCountry);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Finds cities in a specific country with a certain temperature.
    public List<City> findCountryAndTemperatureListContaining(Country country, Temperature temperature){
        List<City> allCities = cityRepository.findAllByCountry(country);
        List<City> citiesWithTemperature = new ArrayList<>();

        for (City city : allCities) {
            if (city.getTemperatureList().contains(temperature)) {
                citiesWithTemperature.add(city);
            }
        }
        return citiesWithTemperature;
    }

    //Finds cities in a specific country with a certain landscape and temperature.
    public List<City> findAllByCountryAndClimateAndTemperatureListContaining(Country country, Climate climate, Temperature temperature){
        List<City> allCities = cityRepository.findAllByCountryAndClimate(country,climate);
        List<City> citiesWithTemperature = new ArrayList<>();

        for (City city : allCities) {
            if (city.getTemperatureList().contains(temperature)) {
                citiesWithTemperature.add(city);
            }
        }
        return citiesWithTemperature;
    }

    //Finds cities in a specific country with a certain landscape and temperature.
    public List<City> findAllByCountryAndLandscapeAndTemperatureListContaining(Country country, Landscape landscape, Temperature temperature){
        List<City> allCities = cityRepository.findAllByCountryAndLandscape(country,landscape);
        List<City> citiesWithTemperature = new ArrayList<>();

        for (City city : allCities) {
            if (city.getTemperatureList().contains(temperature)) {
                citiesWithTemperature.add(city);
            }
        }
        return citiesWithTemperature;
    }

    //Finds cities in a specific country with a certain landscape, climate, and temperature.
    public List<City> findAllByCountryAndLandscapeAndClimateAndTemperatureListContaining(Country country,Landscape landscape, Climate climate, Temperature temperature){
        List<City> allCities = cityRepository.findAllByCountryAndLandscapeAndClimate(country,landscape, climate);
        List<City> citiesWithTemperature = new ArrayList<>();

        for (City city : allCities) {
            if (city.getTemperatureList().contains(temperature)) {
                citiesWithTemperature.add(city);
            }
        }
        return citiesWithTemperature;
    }

    // Filters cities based on specified criteria such as country, landscape, temperature, and climate.
    public List<City> filterCitiesByCriteria(Country countryDest, Landscape landscape, Temperature temperature, Climate climate) {
        List<City> cityList;
        if (landscape.equals(Landscape.ALL) && temperature.equals(Temperature.ALL) && climate.equals(Climate.ALL)) {
            cityList = cityRepository.findAllByCountry(countryDest);
        } else if (landscape.equals(Landscape.ALL) && temperature.equals(Temperature.ALL)) {
            cityList = cityRepository.findAllByCountryAndClimate(countryDest, climate);
        } else if (landscape.equals(Landscape.ALL) && climate.equals(Climate.ALL)) {
            cityList = findCountryAndTemperatureListContaining(countryDest, temperature);
        } else if (temperature.equals(Temperature.ALL) && climate.equals(Climate.ALL)) {
            cityList = cityRepository.findAllByCountryAndLandscape(countryDest, landscape);
        } else if (landscape.equals(Landscape.ALL)) {
            cityList = findAllByCountryAndClimateAndTemperatureListContaining(countryDest, climate,temperature);
        } else if (temperature.equals(Temperature.ALL)) {
            cityList = cityRepository.findAllByCountryAndLandscapeAndClimate(countryDest, landscape, climate);
        } else if (climate.equals(Climate.ALL)) {
            cityList = findAllByCountryAndLandscapeAndTemperatureListContaining(countryDest, landscape, temperature);
        } else {
            cityList = findAllByCountryAndLandscapeAndClimateAndTemperatureListContaining(countryDest, landscape, climate, temperature);
        }

        return cityList;
    }

}