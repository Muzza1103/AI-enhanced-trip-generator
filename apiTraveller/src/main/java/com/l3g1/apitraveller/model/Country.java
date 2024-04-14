package com.l3g1.apitraveller.model;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
// This class represents a country in the API Traveller model.
@Data
@Entity
@Table(name = "Country")
public class Country {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="countryID")
	
    private Long countryID;

 
    @Column(name="name")
    private String countryName;


    @Column(name="climateList")
    private List<Climate> climateList = new ArrayList<>();;

    @Column(name="continent")
    private Continent continent;

    @Column(name = "description")
    private String description;

    // Establishes a one-to-many relationship between Country and City.
    // CascadeType.ALL ensures changes cascade to associated cities.
    // JoinColumn specifies the foreign key relationship using fk_countryID referencing countryID.
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_countryID",referencedColumnName = "countryID")
    private List<City> cityList = new ArrayList<>();

    // Default constructor
    public Country() {}

    // Constructor with all parameters including countryID
    public Country(Long countryID, String countryName, List<Climate> climateList, Continent continent, String description, List<City> cityList) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.climateList = climateList;
        this.continent = continent;
        this.description = description;
        this.cityList = cityList;
    }

    // Constructor with parameters excluding countryID
    public Country(String countryName, List<Climate> climateList, Continent continent, String description, List<City> cityList) {
        this.countryName = countryName;
        this.climateList = climateList;
        this.continent = continent;
        this.description = description;
        this.cityList = cityList;
    }

    // Getters and Setters
    public Long getCountryID() {
        return countryID;
    }
    public void setCountryID(Long countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public List<Climate> getClimateList() {
        return climateList;
    }
    public void setClimateList(List<Climate> climateList) {
        this.climateList = climateList;
    }
    public void addClimateList(Climate climate) {
        climateList.add(climate);
    }
    public Continent getContinent() {
        return continent;
    }
    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<City> getCityList() {
        return cityList;
    }
    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
    public void addCityList(City city) {
    	cityList.add(city);
    }
    
}