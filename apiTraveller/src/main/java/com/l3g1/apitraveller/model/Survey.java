package com.l3g1.apitraveller.model;
import java.util.*;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Survey)) return false;
		Survey survey = (Survey) o;
		return Objects.equals(localisation, survey.localisation) &&
				climate == survey.climate &&
				landscape == survey.landscape &&
				temperature == survey.temperature &&
				Objects.equals(activityTypeAsSet(), survey.activityTypeAsSet());
	}

	@Override
	public int hashCode() {
		return Objects.hash(localisation, climate, landscape, temperature, activityTypeAsSet());
	}

	private Set<ActivityType> activityTypeAsSet() {
		return new HashSet<>(activityType);
	}
}
