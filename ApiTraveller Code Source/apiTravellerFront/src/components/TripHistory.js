import React, { useEffect, useState } from 'react';
import axios from 'axios';
import NavBar from "./NavBar";
import AuthService from "../services/auth.service";
import "./css/History.css";
import croix from "../assets/croix rouge.png";

const TripHistory = () => {
    const [tripSuggestions, setTripSuggestions] = useState([]);
    const user = AuthService.getCurrentUser();
    const [openIndex, setOpenIndex] = useState(null);

    const fetchTripSuggestions = async () => {
        try {
            const response = await axios.get('http://localhost:9000/registerSuggestion/getTripSuggestionHistory', {
                headers: {
                    'Authorization': `Bearer ${user.accessToken}`
                }
            });
            setTripSuggestions(response.data);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleDeleteTripSuggestion = async (index) => {
        try {
            const confirmDelete = window.confirm("Are you sure you want to delete this suggestion?");
            if (confirmDelete) {
                await axios.delete(`http://localhost:9000/registerSuggestion/deleteTripSuggestionInHistory/${index}`, {
                    headers: {
                        'Authorization': `Bearer ${user.accessToken}`
                    }
                });
                // Mettre à jour la liste des suggestions après la suppression
                fetchTripSuggestions();
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    useEffect(() => {
        fetchTripSuggestions();
    }, []);

    const toggleOpen = (index) => {
        setOpenIndex(openIndex === index ? null : index);
    };

    return (
        <div>
            <NavBar/>
            <h1 className={"titre"}>Travel Trip Suggestions History</h1>
            <ul>
                {tripSuggestions.map((trip, index) => (
                    <div key={index}>
                        <div className={"text-history"}>
                            <div>
                                <h2 className="titre-trip-history">{trip.dataTripSurvey.Country}</h2>
                                <p className="titre-trip-history">{trip.dataTripSurvey.Description}</p>
                            </div>
                            {trip.dataTripSurvey && trip.dataTripSurvey.Cities && trip.dataTripSurvey.Cities.map((city, cityIndex) => (
                                <div key={`${index}-${cityIndex}`}>
                                    <h3 className="titre-trip-history">{city.City}</h3>
                                    <p className="titre-trip-history">{city.Description}</p>
                                    {city.Days && (
                                        <div className="container-line">
                                            {city.Days.map((day, dayIndex) => (
                                                <div
                                                    className={"days-container-history"}
                                                    key={`${index}-${dayIndex}`}
                                                    onClick={() => toggleOpen(cityIndex)}
                                                >
                                                    <h4>Day {dayIndex + 1}</h4>
                                                    <ul>
                                                        {day.activities.slice(1).map((activity, activityIndex) => (
                                                            <li key={`${index}-${cityIndex}-${dayIndex}-${activityIndex}`}>
                                                                <h5>{activity["Activity"]}</h5>
                                                                <p>Type: {activity["Type of the activity"]}</p>
                                                                <p>Description: {activity.Description}</p>
                                                                <p>Price: {activity.Price}</p>
                                                            </li>
                                                        ))}
                                                    </ul>
                                                </div>
                                            ))}
                                        </div>
                                    )}
                                </div>
                            ))}
                        </div>
                        <button className={"delete"} onClick={() => handleDeleteTripSuggestion(index)}>
                            <img className="croix-rouge" src={croix} alt="Supprimer"/>
                        </button>
                    </div>
                ))}
            </ul>
        </div>
    );
};

export default TripHistory;
