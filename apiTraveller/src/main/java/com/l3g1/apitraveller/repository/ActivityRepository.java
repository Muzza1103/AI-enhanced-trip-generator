package com.l3g1.apitraveller.repository;

import com.l3g1.apitraveller.model.Activity;
import com.l3g1.apitraveller.model.ActivityType;
import org.springframework.beans.factory.annotation.Autowired;

import com.l3g1.apitraveller.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    // Retrieve all activities for a given city and activity type

    List<Activity> findAllByCityAndActivityType(City city, ActivityType activityType);

    // Retrieve all activities for a given city
    List<Activity> findAllByCity(City city);
}
