package com.l3g1.apitraveller.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

import lombok.Data;

// This class represents a city in the API Traveller model.
@Data
@Entity
@Table(name = "City")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cityID")

    private Long cityID;

    @Column(name="name")
    private String cityName;


    @Column(name="climate")
    private Climate climate;

    @Column(name="temperature")
    private Temperature temperature;

    @Column(name="landscape")
    private Landscape landscape;

    @Column(name = "description")
    private String description;

    // Represents a collection of means of transport available in the city.
    // The @ElementCollection annotation specifies that this field should be persisted as an element collection.
    // The @Column annotation specifies the details of the column where the means of transport will be stored.
    // In this case, it defines the column name as "means_of_transport" with a maximum length of 1000 characters.
    @ElementCollection
    @Column(name = "means_of_transport", length = 1000)
    private List<Transport> transportList = new ArrayList<>();

    // Represents the country to which the city belongs.
    // The @ManyToOne annotation indicates a many-to-one relationship between City and Country entities.
    // The @JoinColumn annotation specifies the foreign key relationship between City and Country using fk_countryID.
    @ManyToOne
    @JoinColumn(name = "fk_countryID")
    private Country country;

    // Represents a list of activities available in the city.
    // The @OneToMany annotation indicates a one-to-many relationship between City and Activity entities.
    // The CascadeType.ALL ensures that any changes made to a city are cascaded to its associated activities.
    // The @JoinColumn annotation specifies the foreign key relationship between Activity and City using fk_cityID.
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_cityID",referencedColumnName = "cityID")
    private List<Activity>activityList;

    // Default constructor
    public City() {
    }

    // Constructor with all parameters including cityID
    public City(Long cityID, String cityName, Climate climate, Temperature temperature,
                Landscape landscape, String description, List<Transport> transportList, Country country, List<Activity> activityList) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.climate = climate;
        this.temperature = temperature;
        this.landscape = landscape;
        this.description = description;
        this.transportList = transportList;
        this.country = country;
        this.activityList = activityList;
    }

    // Constructor with parameters excluding cityID
    public City(String cityName, Climate climate, Temperature temperature,
                Landscape landscape, String description, List<Transport> transportList, Country country, List<Activity> activityList) {
        this.cityName = cityName;
        this.climate = climate;
        this.temperature = temperature;
        this.landscape = landscape;
        this.description = description;
        this.transportList = transportList;
        this.country = country;
        this.activityList = activityList;
    }

    // Getters et Setters
    public Long getCityID() {
        return cityID;
    }
    public void setCityID(Long cityID) {
        this.cityID = cityID;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Climate getClimate() {
        return climate;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Landscape getLandscape() {
        return landscape;
    }

    public void setLandscape(Landscape landscape) {
        this.landscape = landscape;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Transport> getTransport() {
        return transportList;
    }

    public void setTransport(List<Transport> transportList) {
        this.transportList = transportList;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    public List<Activity> getActivityList() {
        return activityList;
    }
    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(cityName, city.cityName);
    }

}