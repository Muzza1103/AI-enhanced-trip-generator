package com.l3g1.apitraveller.model;
import com.l3g1.apitraveller.model.enumeration.ActivityType;
import com.l3g1.apitraveller.model.enumeration.Climate;
import com.l3g1.apitraveller.model.enumeration.Landscape;
import com.l3g1.apitraveller.model.enumeration.Temperature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Class representing different options for TripSurvey
public class TripSurvey {
    private String localisation;
    private Climate climate;
    private Landscape landscape;
    private Temperature temperature;
    private List<ActivityType> activityType;
    private String startingDate;
    private String endingDate;
    private boolean roadTrip;
    private int budget;

}
