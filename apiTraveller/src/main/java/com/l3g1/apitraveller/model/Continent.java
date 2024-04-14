package com.l3g1.apitraveller.model;

// This enum represents different continents.
public enum Continent {
	EUROPE,
	ASIA,
	AFRICA,
	NORTH_AMERICA,
	SOUTH_AMERICA,
	OCEANIA,
	ANTARCTICA;

	// Checks if the given value is a valid value of the Continent enum.
	public static boolean isValidValue(String valeur) {
		for (Continent continent : Continent.values()) {
			if (continent.toString().equalsIgnoreCase(valeur)) {
				return true;
			}
		}
		return false;
	}

}
