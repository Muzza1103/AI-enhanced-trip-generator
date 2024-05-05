package com.l3g1.apitraveller.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

// Represents a suggestion for travel plans, including country, city, activities, and survey.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Suggestion {
    private Country country; // Country for the suggested travel plan
    private City city; // City within the suggested country
    private List<Activity> activityList; // List of activities suggested for the city
    private Survey survey; // Survey associated with the suggestion
}
