package com.l3g1.apitraveller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.l3g1.apitraveller.model.Activity;
import com.l3g1.apitraveller.repository.ActivityRepository;

import lombok.Data;
@Data
@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    // Retrieves an activity by its unique identifier.
    public Optional<Activity> getActivity(final Long id) {
        return activityRepository.findById(id);
    }

    // Retrieves all activities stored in the repository.
    public Iterable<Activity> getActivity() {
        return activityRepository.findAll();
    }

    // Deletes an activity from the repository by its unique identifier.
    public void deleteActivity(final Long id) {
    	activityRepository.deleteById(id);
    }

    // Saves an activity to the repository.
    public Activity saveActivity(Activity activity) {
    	Activity savedActivity = activityRepository.save(activity);
        return savedActivity;
    }

}