package com.l3g1.apitraveller.model;

import java.util.Objects;

import javax.persistence.*;

import com.l3g1.apitraveller.model.enumeration.ActivityType;
import lombok.Data;

// This class represents an activity that can be performed by travelers.
@Data
@Entity
@Table(name = "Activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="activityID")
    private Long activityID;

    @Column(name="name")
    private String activityName;

    @Column(name = "activity_type")
    private ActivityType activityType;

    @Column(name = "description")
    private String description;

    @Column (name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "fk_cityID")
    private City city;

    // Default constructor
    public Activity() {
    }

    // Constructor with all parameters including activityID
    public Activity(Long activityID, String activityName, ActivityType activityType, String description, int price, City city) {
        this.activityID = activityID;
        this.activityName = activityName;
        this.activityType = activityType;
        this.description = description;
        this.price = price;
        this.city = city;
    }

    // Constructor with parameters excluding activityID
    public Activity(String activityName, ActivityType activityType, String description, int price, City city) {
        this.activityName = activityName;
        this.activityType = activityType;
        this.description = description;
        this.price = price;
        this.city = city;
    }

    // Getters et Setters
    public Long getActivityID() {
        return activityID;
    }
    public void setActivityID(Long activityID) {
        this.activityID = activityID;
    }
    public String getActivityName() {
        return activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }


    // Equals method to compare objects based on activityName and activityType
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(activityName, activity.activityName) &&
                Objects.equals(activityType, activity.activityType);
    }

    // HashCode method to generate hash codes based on activityName, description, activityType, and price
    @Override
    public int hashCode() {
        return Objects.hash(activityName, description, activityType, price);
    }
}