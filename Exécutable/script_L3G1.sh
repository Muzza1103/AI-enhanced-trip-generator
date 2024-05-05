#!/bin/bash

# To get the current position of the folder
script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Launch the front-end, opens a page in your default navigator
cd "$script_dir/apiTravellerFront/apitravellerfront" && npm start &

# Set environment variables for API keys

# Add your Ai Api key
export API_AI_KEY=
# Put the maximum number of request per minutes allowed by your key
export RPM_API_KEY=

# Database connection settings

# Put the port of your MySQL server, its 3306 by default
host=3306
# The name of the BDD
database=bdd_apitraveller
# Put a user with enough access to modify the database
user=
# Put the password of the user
password=

# Launch your Spring Boot application with system variables
java -Djdbc.url=jdbc:mysql://localhost:"$host"/"$database" -Djdbc.username="$user" -Djdbc.password="$password" -jar api-0.0.1-SNAPSHOT.jar