package com.l3g1.apitraveller.repository;

import com.l3g1.apitraveller.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	// Find all cities by country, landscape, temperature, and climate
	List<City> findAllByCountryAndLandscapeAndTemperatureAndClimate(Country country, Landscape landscape, Temperature temperature, Climate climate);

	// Find all cities by country
	List<City> findAllByCountry(Country country);

	// Find all cities by country and temperature
	List<City> findAllByCountryAndTemperature(Country country, Temperature temperature);

	// Find all cities by country and landscape
	List<City> findAllByCountryAndLandscape(Country country, Landscape landscape);

	// Find all cities by country, temperature, and climate
	List<City> findAllByCountryAndTemperatureAndClimate(Country countryDest, Temperature temperature, Climate climate);

	// Find all cities by country, landscape, and climate
	List<City> findAllByCountryAndLandscapeAndClimate(Country countryDest, Landscape landscape, Climate climate);

	// Find all cities by country and climate
	List<City> findAllByCountryAndClimate(Country countryDest, Climate climate);

	// Find all cities by country, landscape, and temperature
	List<City> findAllByCountryAndLandscapeAndTemperature(Country countryDest, Landscape landscape, Temperature temperature);

	// Find city by country name and city name
	City findByCountryCountryNameAndCityName(String countryName, String cityName);

	// Find city by country and city name
	City findByCountryAndCityName(Country existingCountry, String cityName);
}
