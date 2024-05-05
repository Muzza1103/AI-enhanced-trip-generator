@echo off

REM To get the current position of the folder
set "script_dir=%~dp0"

REM Launch the front-end, opens a page in your default navigator
start cmd /k "cd /d "%script_dir%apiTravellerFront\apitravellerfront" && npm start"


REM Set environment variables for API keys

REM Add your Ai Api key
set API_AI_KEY=
REM Put the maximum number of request per minutes allowed by your key
set RPM_API_KEY=

REM Database connection settings

REM Put the port of your MySQL server, its 3306 by default
set host=3306
REM The name of the BDD
set database=bdd_apitraveller
REM Put a user with enough access to modify the database
set user=
REM Put the password of the user
set password=

REM Launch your Spring Boot application with system variables
start java -Djdbc.url=jdbc:mysql://localhost:%host%/%database% -Djdbc.username=%user% -Djdbc.password=%password% -jar api-0.0.1-SNAPSHOT.jar

