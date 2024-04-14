package com.l3g1.apitraveller.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.l3g1.apitraveller.model.Activity;
import com.l3g1.apitraveller.service.ActivityService;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    // Get all activities
    @GetMapping("/get")
    public Iterable<Activity> getActivity() {
        return activityService.getActivity();
    }

    // Get activity by ID
    @GetMapping("/get/{id}")
    public Optional<Activity> getActivity(@PathVariable long id) {
        return activityService.getActivity(id);
    }

    // Save a new activity
    @PutMapping("/save")
    public Activity saveActivity(@RequestBody Activity activity) {
        return activityService.saveActivity(activity);
    }

    // Delete activity by ID
    @DeleteMapping("/delete/{id}")
    public void deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
    }
}
