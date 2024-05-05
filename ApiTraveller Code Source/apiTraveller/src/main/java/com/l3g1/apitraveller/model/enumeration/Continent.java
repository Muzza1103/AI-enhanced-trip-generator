package com.l3g1.apitraveller.model.enumeration;

// This enum represents different continents.
public enum Continent {
	EUROPE(50), // Example: 50 countries in Europe
	ASIA(60), // Example: 60 countries in Asia
	AFRICA(54), // Example: 54 countries in Africa
	NORTH_AMERICA(23), // Example: 23 countries in North America
	SOUTH_AMERICA(12), // Example: 12 countries in South America
	OCEANIA(14), // Example: 14 countries in Oceania
	ANTARCTICA(1); // No countries in Antarctica, but we can count the different stations as cities

	private int numberOfCountries;

	// Private constructor to initialize the number of countries for each continent
	private Continent(int numberOfCountries) {
		this.numberOfCountries = numberOfCountries;
	}

	// Method to get the number of countries for a given continent
	public int getNumberOfCountries() {
		return numberOfCountries;
	}

	// Checks if the given value is a valid value of the Continent enum.
	public static boolean isValidValue(String value) {
		for (Continent continent : Continent.values()) {
			if (continent.toString().equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}
}