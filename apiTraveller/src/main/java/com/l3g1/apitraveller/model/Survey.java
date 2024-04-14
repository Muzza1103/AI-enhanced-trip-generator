package com.l3g1.apitraveller.model;
import java.util.List;
import java.util.Scanner;

import lombok.*;

// Represents a survey for travel preferences.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey {
	private String localisation; // Location preference
	private Climate climate; // Preferred climate
	private Landscape landscape; // Preferred landscape
	private Temperature temperature; // Preferred temperature
	private List<ActivityType> activityType; // Preferred activity types
}
	/*** Exemple of Survey
	 {
	 "countryUtil" : "France",
	 "localisation" : "LOCAL",
	 "climate" : "TEMPERATE",
	 "landscape":"URBAN",
	 "temperature":"TEMPERATE",
	 "activityType":{"CULTURAL"},
	 }
	 * {,
	 * "localisation" : "LOCAL",
	 *  "climate" : "TEMPERATE",
	 *  "landscape":"URBAN",
	 *  "temperature":"TEMPERATE",
	 *  "activityType":{"CULTURAL"},
	 * }
	 */

