package com.l3g1.apitraveller.repository;

import com.l3g1.apitraveller.model.enumeration.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.l3g1.apitraveller.model.Country;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    // Find a country by its name
    Country findByCountryName(String countryName);

    // Delete a country by its name
    void deleteByCountryName(String countryName);

    // Find all countries
    List<Country> findAll();

    // Find all countries with the correspondind continent
    List<Country> findAllByContinent(Continent continent);

}
