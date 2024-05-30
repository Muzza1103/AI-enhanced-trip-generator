import React, { useEffect, useState } from 'react';
import axios from 'axios';
import NavBar from "./NavBar";
import AuthService from "../services/auth.service";
import "./css/History.css";
import croix from "../assets/croix rouge.png"

const History = () => {
    const [suggestions, setSuggestions] = useState([]);
    const user = AuthService.getCurrentUser();
    const [openIndex, setOpenIndex] = useState(null);

    const fetchSuggestions = async () => {
        try {
            const response = await axios.get('http://localhost:9000/registerSuggestion/getSuggestionHistory', {
                headers: {
                    'Authorization': `Bearer ${user.accessToken}`
                }
            });
            setSuggestions(response.data);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleDeleteSuggestion = async (index) => {
        try {
            const confirmDelete = window.confirm("Are you sure you want to delete this suggestion?");
            if (confirmDelete) {
                await axios.delete(`http://localhost:9000/registerSuggestion/deleteSuggestionInHistory/${index}`, {
                    headers: {
                        'Authorization': `Bearer ${user.accessToken}`
                    }
                });
                // Mettre à jour la liste des suggestions après la suppression
                fetchSuggestions();
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    useEffect(() => {
        fetchSuggestions();
    }, []);

    const toggleOpen = (index) => {
        setOpenIndex(openIndex === index ? null : index);
    };

    return (
        <div>
            <NavBar />
            <h1 className={"titre"}>Travel Suggestions History</h1>
            <ul>
                {suggestions.map((dataSurvey, index) => (
                    <div key={index}>
                        <div className={"text-history"}>
                            {dataSurvey.dataSurvey.Suggestions && dataSurvey.dataSurvey.Suggestions.map((suggestion, innerIndex) => (
                                <div
                                    className={"container-history"}
                                    key={`${index}-${innerIndex}`}
                                    onClick={() => toggleOpen(index)}
                                >
                                    <h2 className="titre-container">{suggestion.Country ? suggestion.Country.Country : (dataSurvey.dataSurvey.Country)}</h2>
                                    {openIndex === index && (
                                        <div>
                                            <p>{suggestion.Country && suggestion.Country.Description}</p>
                                            {suggestion.City && (
                                                <div>
                                                    <h3>{suggestion.City.City}</h3>
                                                    <p>{suggestion.City.Description}</p>
                                                </div>
                                            )}
                                            {suggestion.Activities && (
                                                <div>
                                                    <h3>Activities:</h3>
                                                    <ul>
                                                        {suggestion.Activities.map((activity, activityIndex) => (
                                                            <li key={activityIndex}>
                                                                <h4>{activity.Name}</h4>
                                                                <p>Type: {activity["Type of the activity"]}</p>
                                                                <p>Description: {activity.Description}</p>
                                                                <p>Price: {activity.Price}</p>
                                                            </li>
                                                        ))}
                                                    </ul>
                                                </div>
                                            )}
                                        </div>
                                    )}
                                </div>
                            ))}
                            <button className={"delete"} onClick={() => handleDeleteSuggestion(index)}>
                                <img className="croix-rouge" src={croix} alt="Supprimer"/>
                            </button>
                        </div>
                    </div>
                ))}
            </ul>
        </div>
    );
};

export default History;
