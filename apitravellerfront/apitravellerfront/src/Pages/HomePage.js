import React, { useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from './logo.png';
import './HomePage.css'; // Importez le fichier CSS

const HomePage = () => {
    const navigate = useNavigate();
    const buttonRef = useRef(null);

    const handleButtonClick = () => {
        navigate('/survey');
    };


    return (
        <div className="homepage-container">
            <img src={logo} alt="Logo" />
            <h1> Where journeys begin</h1>
            <button
                ref={buttonRef} className="HomeToSurveyButton" onClick={handleButtonClick}>Let's go to the survey !
            </button>
        </div>
    );
};

export default HomePage;
