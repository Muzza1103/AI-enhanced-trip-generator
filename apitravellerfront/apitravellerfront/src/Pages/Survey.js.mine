import React, { useState } from 'react';
import './Survey.css';

// Constantes pour les valeurs possibles des attributs
const ACTIVITY_TYPES = [
    'OUTDOOR', 'CULTURAL', 'RELAXATION', 'ADVENTURE', 'GASTRONOMIC', 'ENTERTAINMENT', 'ROMANTIC', 'HISTORICAL', 'OTHER'
];

const CLIMATES = [
    'TROPICAL', 'DESERT', 'POLAR', 'MEDITERRANEAN', 'TEMPERATE', 'ALL', 'OTHER'
];

const LANDSCAPES = [
    'MOUNTAIN', 'BEACH', 'FOREST', 'DESERT', 'VALLEY', 'COASTAL', 'RURAL', 'URBAN', 'ALL', 'OTHER'
];

const TEMPERATURES = [
    'HOT', 'WARM', 'MILD', 'TEMPERATE', 'COOL', 'COLD', 'ALL'
];

// Définition du composant Survey
const Survey = () => {
    // Utilisation de useState pour gérer l'état des filtres et des options sélectionnées
    const [showFilter1, setShowFilter1] = useState(false);
    const [showFilter2, setShowFilter2] = useState(false);
    const [selectedOptions1, setSelectedOptions1] = useState({
        activityType: [], climate: [], landscape: [], temperature: []
    });
    const [selectedOptions2, setSelectedOptions2] = useState({});
    const [localisation, setLocalisation] = useState('');

    // Fonction pour gérer la sélection des options pour le premier ensemble de filtres
    const handleOptionSelect1 = (option, attribute) => {
        setSelectedOptions1(prevState => ({
            ...prevState,
            [attribute]: prevState[attribute].includes(option)
                ? prevState[attribute].filter(item => item !== option)
                : [...prevState[attribute], option]
        }));
    };

    // Fonction pour gérer la sélection des options pour le deuxième ensemble de filtres
    const handleOptionSelect2 = (option, attribute) => {
        setSelectedOptions2(prevState => ({ ...prevState, [attribute]: option }));
    };

    // Fonction pour soumettre le formulaire
    const handleSubmit = (e) => {
        e.preventDefault();
        // Logique pour soumettre le questionnaire
    };

    // Fonction de rendu des options de boutons
    // Dans la fonction renderButtonOptions, ajoutez une classe spécifique aux boutons des options
    const renderButtonOptions = (options, selectedOptions, attribute, handleOptionSelect) => (
        <div>
            <strong>{attribute}</strong>
            {options.map(option => (
                <button
                    key={option}
                    className={`option-button${selectedOptions.includes(option) ? ' active' : ''}`}
                    onClick={() => handleOptionSelect(option, attribute)}
                >
                    {option}
                </button>
            ))}
        </div>
    );


    // Rendu du composant Survey
    return (
        <div className="survey-container" style={{ backgroundColor: 'white' }}>
            <h1>Survey</h1>
            <div className="filter-buttons">
                <button className={`survey-button ${showFilter1 ? 'active' : ''}`} onClick={() => {
                    setShowFilter1(!showFilter1);
                    setShowFilter2(false);
                }}>Survey 1
                </button>
                <button className={`survey-button ${showFilter2 ? 'active' : ''}`} onClick={() => {
                    setShowFilter2(!showFilter2);
                    setShowFilter1(false);
                }}>Survey 2
                </button>

            </div>
            <div className="filter-container">
                {showFilter1 && (
                    <div>
                        <h2>Localisation</h2>
                        <label>Localisation:</label>
                        <input
                            type="text"
                            value={localisation}
                            onChange={(e) => setLocalisation(e.target.value)}
                        />
                        {renderButtonOptions(ACTIVITY_TYPES, selectedOptions1.activityType, 'Activity Type', handleOptionSelect1)}
                        {renderButtonOptions(CLIMATES, selectedOptions1.climate, 'Climate', handleOptionSelect1)}
                        {renderButtonOptions(LANDSCAPES, selectedOptions1.landscape, 'Landscape', handleOptionSelect1)}
                        {renderButtonOptions(TEMPERATURES, selectedOptions1.temperature, 'Temperature', handleOptionSelect1)}
                    </div>
                )}
                {showFilter2 && (
                    <div>
                        <h2>Localisation</h2>
                        <label>Localisation:</label>
                        <input
                            type="text"
                            value={selectedOptions2.localisation || ''}
                            onChange={(e) => handleOptionSelect2(e.target.value, 'localisation')}
                        />
                        {renderButtonOptions(ACTIVITY_TYPES, [selectedOptions2.activityType || ''], 'Activity Type', handleOptionSelect2)}
                        {renderButtonOptions(CLIMATES, [selectedOptions2.climate || ''], 'Climate', handleOptionSelect2)}
                        {renderButtonOptions(LANDSCAPES, [selectedOptions2.landscape || ''], 'Landscape', handleOptionSelect2)}
                        {renderButtonOptions(TEMPERATURES, [selectedOptions2.temperature || ''], 'Temperature', handleOptionSelect2)}
                        <div>
                            <h2>Departure Date</h2>
                            <label>Departure date :</label>
                            <input
                                type="date"
                                value={selectedOptions2.departureDate || ''}
                                onChange={(e) => handleOptionSelect2(e.target.value, 'departureDate')}
                            />
                            <h2>Arrival Date</h2>
                            <label>Arrival date :</label>
                            <input
                                type="date"
                                value={selectedOptions2.arrivalDate || ''}
                                onChange={(e) => handleOptionSelect2(e.target.value, 'arrivalDate')}
                            />
                            <h2>Price</h2>
                            <label>Price :</label>
                            <input
                                type="number"
                                value={selectedOptions2.price || ''}
                                onChange={(e) => handleOptionSelect2(parseFloat(e.target.value), 'price')}
                            />
                        </div>
                    </div>
                )}
            </div>
            <div className="submit-form">
                <h2>Survey Form</h2>
                <form onSubmit={handleSubmit}>
                    <button type="submit">Submit</button>
                </form>
            </div>
        </div>
    );
};

export default Survey;
