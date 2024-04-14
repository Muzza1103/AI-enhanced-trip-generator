import React, { useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from './logo.png';
import './HomePage.css';

const HomePage = () => {
    const navigate = useNavigate();
    const buttonRef = useRef(null);

    const handleButtonClick = () => {
        navigate('/survey');
    };

    const modifyButton = () => {
        const button = buttonRef.current;

        button.style.backgroundColor = "#d3cfa9";
        button.style.color = "white";

        button.style.width = "190px";
        button.style.height = "50px";

        button.style.color = "black";
        button.style.fontWeight = "bold";
        button.style.fontSize = "15px";
    };

    // Call modifyButton function when the component is rendered
    useEffect(() => {
        modifyButton();
    }, []);

    return (
        <div className="homepage-container">
            <img src={logo} alt="Logo" />
            <button ref={buttonRef} id="Let's-go-to-the-survey-button" onClick={handleButtonClick}>Let's go to the survey !</button>
        </div>
    );
};

export default HomePage;