    package com.l3g1.apitraveller.model;

    import java.util.List;
    import java.time.LocalDate;

    import lombok.Data;
    import lombok.AllArgsConstructor;
    import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    // Class representing a suggestion for Trip Survey
    public class TripSuggestion {
        // Private fields representing various aspects of the trip suggestion
        private Country country;
        private City city;
        private List<Activity> activityList;
        private TripSurvey survey;
        private LocalDate departureDate ;
        private LocalDate arrivalDate ;
        private String dayActivity;
        private int cost;
        private int costCity;

        // Constructor with parameters for initializing all fields
        public TripSuggestion(Country country,City city,List<Activity> activity,TripSurvey survey,LocalDate dateDeparture,LocalDate dateArrival,String dayActivity,int cost){
            this.country = country;
            this.city = city;
            this.activityList = activity;
            this.survey = survey;
            this.departureDate = dateDeparture;
            this.arrivalDate = dateArrival;
            this.dayActivity = dayActivity;
            this.cost = cost;
            this.costCity = 0;
        }
    }
