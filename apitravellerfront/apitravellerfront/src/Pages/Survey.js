import React, { useState, useEffect } from 'react';
import './Survey.css';
<<<<<<< .mine
||||||| .r65
import logo from './logo.png';
import Select from 'react-select'
import {Country} from 'country-state-city'
=======
import logo from './logo.png';
import Select from 'react-select'
import {Button} from '@material-ui/core/Button';
>>>>>>> .r75

<<<<<<< .mine
// Constantes pour les valeurs possibles des attributs
||||||| .r65
// Données pour les filtres
=======

// Données pour les filtres
>>>>>>> .r75
const ACTIVITY_TYPES = [
<<<<<<< .mine
    'OUTDOOR', 'CULTURAL', 'RELAXATION', 'ADVENTURE', 'GASTRONOMIC', 'ENTERTAINMENT', 'ROMANTIC', 'HISTORICAL', 'OTHER'
||||||| .r65
    'OUTDOOR 🌳', 'CULTURAL 🏛️', 'RELAXATION 🧘🏽', 'ADVENTURE 🌄', 'GASTRONOMIC 🍽️', 'ENTERTAINMENT 🎭', 'ROMANTIC 💖', 'HISTORICAL 🏰', 'ALL', 'OTHER'
=======
    'OUTDOOR 🌳', 'CULTURAL 🏛️', 'RELAXATION 🧘🏽', 'ADVENTURE 🌄', 'GASTRONOMIC 🍽️', 'ENTERTAINMENT 🎭', 'ROMANTIC 💖', 'HISTORICAL 🏰', 'ALL'
>>>>>>> .r75
];

const CLIMATES = [
<<<<<<< .mine
    'TROPICAL', 'DESERT', 'POLAR', 'MEDITERRANEAN', 'TEMPERATE', 'ALL', 'OTHER'
||||||| .r65
    'TROPICAL 🌴', 'DESERT 🏜️', 'POLAR ❄️', 'MEDITERRANEAN 🏖️', 'TEMPERATE 🌤️', 'ALL', 'OTHER'
=======
    'TROPICAL 🏝️', 'DESERT 🏜️', 'POLAR ❄️', 'MEDITERRANEAN 🏖️', 'TEMPERATE 🌤️', 'NEITHER'
>>>>>>> .r75
];

const LANDSCAPES = [
<<<<<<< .mine
    'MOUNTAIN', 'BEACH', 'FOREST', 'DESERT', 'VALLEY', 'COASTAL', 'RURAL', 'URBAN', 'ALL', 'OTHER'
||||||| .r65
    'MOUNTAIN ⛰️', 'BEACH 🏖️', 'FOREST 🌲', 'DESERT 🏜️', 'VALLEY 🏞️', 'COASTAL 🌊', 'RURAL 🏡', 'URBAN 🏙️', 'ALL', 'OTHER'
=======
    'MOUNTAIN ⛰️', 'BEACH 🏖️', 'FOREST 🌲', 'DESERT 🏜️', 'VALLEY 🏞️', 'COASTAL 🌊', 'RURAL 🏡', 'URBAN 🏙️', 'NEITHER'
>>>>>>> .r75
];

const TEMPERATURES = [
<<<<<<< .mine
    'HOT', 'WARM', 'MILD', 'TEMPERATE', 'COOL', 'COLD', 'ALL'
||||||| .r65
    'HOT 🌞', 'WARM 🌼', 'MILD 🌿', 'TEMPERATE 🌤️', 'COOL 🌬️', 'COLD ❄️', 'ALL'
=======
    'HOT ☀️', 'WARM 🌼', 'MILD 🌿', 'TEMPERATE 🌤️', 'COOL 🌬️', 'COLD ❄️', 'NEITHER'
>>>>>>> .r75
];

<<<<<<< .mine
// Définition du composant Survey
||||||| .r65
// Liste des continents
const CONTINENTS = [
    'Africa 🐘', 'Antarctica 🐧', 'Asia 🐼', 'Europe 🐎', 'North America 🦅', 'Oceania 🦘', 'South America 🦥'
];

const COUNTRIES = Country.getAllCountries().map(country => country.name.toUpperCase());
const countryOptions = COUNTRIES.map(country => ({ value: country, label: country }));

=======
// Liste des continents
const CONTINENTS = [
    'Africa 🐘', 'Antarctica 🐧', 'Asia 🐼', 'Europe 🐎', 'North America 🦅', 'Oceania 🦘', 'South America 🦥'
];
const NEWCONTINENTS =[
    'Africa', 'Antarctica', 'Asia', 'Europe', 'North America', 'Oceania', 'South America'
]

//const COUNTRIES = Country.getAllCountries().map(country => country.name);
const CountriesList = [
    'AFGHANISTAN',
    'ALAND_ISLANDS',
    'ALBANIA',
    'ALGERIA',
    'AMERICAN_SAMOA',
    'ANDORRA',
    'ANGOLA',
    'ANGUILLA',
    'ANTIGUA_AND_BARBUDA',
    'ARGENTINA',
    'ARMENIA',
    'ARUBA',
    'AUSTRALIA',
    'AUSTRIA',
    'AZERBAIJAN',
    'THE_BAHAMAS',
    'BAHRAIN',
    'BANGLADESH',
    'BARBADOS',
    'BELARUS',
    'BELGIUM',
    'BELIZE',
    'BENIN',
    'BERMUDA',
    'BHUTAN',
    'BOLIVIA',
    'BOSNIA_AND_HERZEGOVINA',
    'BOTSWANA',
    'BOUVET_ISLAND',
    'BRAZIL',
    'BRITISH_INDIAN_OCEAN_TERRITORY',
    'BRUNEI',
    'BULGARIA',
    'BURKINA_FASO',
    'BURUNDI',
    'CAMBODIA',
    'CAMEROON',
    'CANADA',
    'CAPE_VERDE',
    'CAYMAN_ISLANDS',
    'CENTRAL_AFRICAN_REPUBLIC',
    'CHAD',
    'CHILE',
    'CHINA',
    'CHRISTMAS_ISLAND',
    'COCOS_KEELING_ISLANDS',
    'COLOMBIA',
    'COMOROS',
    'CONGO',
    'DEMOCRATIC_REPUBLIC_OF_THE_CONGO',
    'COOK_ISLANDS',
    'COSTA_RICA',
    'IVORY_COAST',
    'CROATIA',
    'CUBA',
    'CYPRUS',
    'CZECH_REPUBLIC',
    'DENMARK',
    'DJIBOUTI',
    'DOMINICA',
    'DOMINICAN_REPUBLIC',
    'EAST_TIMOR',
    'ECUADOR',
    'EGYPT',
    'EL_SALVADOR',
    'EQUATORIAL_GUINEA',
    'ERITREA',
    'ESTONIA',
    'ETHIOPIA',
    'FALKLAND_ISLANDS',
    'FAROE_ISLANDS',
    'FIJI_ISLANDS',
    'FINLAND',
    'FRANCE',
    'FRENCH_GUIANA',
    'FRENCH_POLYNESIA',
    'FRENCH_SOUTHERN_TERRITORIES',
    'GABON',
    'THE_GAMBIA',
    'GEORGIA',
    'GERMANY',
    'GHANA',
    'GIBRALTAR',
    'GREECE',
    'GREENLAND',
    'GRENADA',
    'GUADELOUPE',
    'GUAM',
    'GUATEMALA',
    'GUERNSEY_AND_ALDERNEY',
    'GUINEA',
    'GUINEA_BISSAU',
    'GUYANA',
    'HAITI',
    'HEARD_ISLAND_AND_MCDONALD_ISLANDS',
    'HONDURAS',
    'HONG_KONG',
    'HUNGARY',
    'ICELAND',
    'INDIA',
    'INDONESIA',
    'IRAN',
    'IRAQ',
    'IRELAND',
    'ISRAEL',
    'ITALY',
    'JAMAICA',
    'JAPAN',
    'JERSEY',
    'JORDAN',
    'KAZAKHSTAN',
    'KENYA',
    'KIRIBATI',
    'NORTH_KOREA',
    'SOUTH_KOREA',
    'KUWAIT',
    'KYRGYZSTAN',
    'LAOS',
    'LATVIA',
    'LEBANON',
    'LESOTHO',
    'LIBERIA',
    'LIBYA',
    'LIECHTENSTEIN',
    'LITHUANIA',
    'LUXEMBOURG',
    'MACAU',
    'MACEDONIA',
    'MADAGASCAR',
    'MALAWI',
    'MALAYSIA',
    'MALDIVES',
    'MALI',
    'MALTA',
    'ISLE_OF_MAN',
    'MARSHALL_ISLANDS',
    'MARTINIQUE',
    'MAURITANIA',
    'MAURITIUS',
    'MAYOTTE',
    'MEXICO',
    'MICRONESIA',
    'MOLDOVA',
    'MONACO',
    'MONGOLIA',
    'MONTENEGRO',
    'MONTSERRAT',
    'MOROCCO',
    'MOZAMBIQUE',
    'MYANMAR',
    'NAMIBIA',
    'NAURU',
    'NEPAL',
    'BONAIRE',
    'NETHERLANDS',
    'NEW_CALEDONIA',
    'NEW_ZEALAND',
    'NICARAGUA',
    'NIGER',
    'NIGERIA',
    'NIUE',
    'NORFOLK_ISLAND',
    'NORTHERN_MARIANA_ISLANDS',
    'NORWAY',
    'OMAN',
    'PAKISTAN',
    'PALAU',
    'PALESTINE',
    'PANAMA',
    'PAPUA_NEW_GUINEA',
    'PARAGUAY',
    'PERU',
    'PHILIPPINES',
    'PITCAIRN_ISLAND',
    'POLAND',
    'PORTUGAL',
    'PUERTO_RICO',
    'QATAR',
    'REUNION',
    'ROMANIA',
    'RUSSIA',
    'RWANDA',
    'SAINT_HELENA',
    'SAINT_KITTS_AND_NEVIS',
    'SAINT_LUCIA',
    'SAINT_PIERRE_AND_MIQUELON',
    'SAINT_VINCENT_AND_THE_GRENADINES',
    'SAINT_BARTHELEMY',
    'SAINT_MARTIN',
    'SAMOA',
    'SAN_MARINO',
    'SAO_TOME_AND_PRINCIPE',
    'SAUDI_ARABIA',
    'SENEGAL',
    'SERBIA',
    'SEYCHELLES',
    'SIERRA_LEONE',
    'SINGAPORE',
    'SLOVAKIA',
    'SLOVENIA',
    'SOLOMON_ISLANDS',
    'SOMALIA',
    'SOUTH_AFRICA',
    'SOUTH_GEORGIA',
    'SOUTH_SUDAN',
    'SPAIN',
    'SRI_LANKA',
    'SUDAN',
    'SURINAME',
    'SVALBARD_AND_JAN_MAYEN_ISLANDS',
    'SWAZILAND',
    'SWEDEN',
    'SWITZERLAND',
    'SYRIA',
    'TAIWAN',
    'TAJIKISTAN',
    'TANZANIA',
    'THAILAND',
    'TOGO',
    'TOKELAU',
    'TONGA',
    'TRINIDAD_AND_TOBAGO',
    'TUNISIA',
    'TURKEY',
    'TURKMENISTAN',
    'TURKS_AND_CAICOS_ISLANDS',
    'TUVALU',
    'UGANDA',
    'UKRAINE',
    'UNITED_ARAB_EMIRATES',
    'UNITED_KINGDOM',
    'UNITED_STATES',
    'UNITED_STATES_MINOR_OUTLYING_ISLANDS',
    'URUGUAY',
    'UZBEKISTAN',
    'VANUATU',
    'VATICAN_CITY',
    'VENEZUELA',
    'VIETNAM',
    'VIRGIN_ISLANDS_BRITISH',
    'VIRGIN_ISLANDS_US',
    'WALLIS_AND_FUTUNA_ISLANDS',
    'WESTERN_SAHARA',
    'YEMEN',
    'ZAMBIA',
    'ZIMBABWE',
    'KOSOVO',
    'CURACAO',
    'SINT_MAARTEN'
];
const formatCountryName = (countryName) => {
    // Séparer le nom du pays par les underscores
    const words = countryName.split('_');
    // Mettre en majuscule la première lettre de chaque mot
    const formattedWords = words.map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase());
    // Joindre les mots avec un espace
    return formattedWords.join(' ');
};

// Modifier tous les noms de pays dans le tableau COUNTRIES
const COUNTRIES = CountriesList.map(country => formatCountryName(country));
const countryOptions = COUNTRIES.map(country => ({ value: country, label: country }));
>>>>>>> .r75
const Survey = () => {
<<<<<<< .mine
    // Utilisation de useState pour gérer l'état des filtres et des options sélectionnées
||||||| .r65
=======
    let [surveyData, setSurveyData] = useState(null);
>>>>>>> .r75
    const [showFilter1, setShowFilter1] = useState(false);
    const [showFilter2, setShowFilter2] = useState(false);
    const [selectedOptions1, setSelectedOptions1] = useState({
        activityType: [], climate: [], landscape: [], temperature: []
    });
<<<<<<< .mine
    const [selectedOptions2, setSelectedOptions2] = useState({});
    const [localisation, setLocalisation] = useState('');

||||||| .r65

=======
    const [selectedCity, setSelectedCity] = useState(null);
>>>>>>> .r75
<<<<<<< .mine
    // Fonction pour gérer la sélection des options pour le premier ensemble de filtres
||||||| .r65
    const [selectedOptions2, setSelectedOptions2] = useState({
        localisation: '',
        activityType: [],
        climate: '',
        landscape: '',
        temperature: '',
        startingDate: '',
        endingDate: '',
        budget: 0
    });

    // Fonctions pour gérer la sélection des options
=======
    const [selectedOptions2, setSelectedOptions2] = useState({
        localisation: '',
        activityType: [],
        climate: '',
        landscape: '',
        temperature: '',
        startingDate: '',
        endingDate: '',
        budget: 0,
        roadTrip: false
    });

    const filterConstante = (valeur) => {
        // Remplace tous les caractères après un espace par une chaîne vide
        if(valeur === "North America 🦅"){
            return "North America";
        }else if(valeur === "South America 🦥"){
            return "South America";
        }
        return valeur.replace(/ .*/, '');
    };


    // Fonctions pour gérer la sélection des options
>>>>>>> .r75
    const handleOptionSelect1 = (option, attribute) => {
<<<<<<< .mine
        setSelectedOptions1(prevState => ({
            ...prevState,
            [attribute]: prevState[attribute].includes(option)
                ? prevState[attribute].filter(item => item !== option)
                : [...prevState[attribute], option]
        }));
||||||| .r65
        setSelectedOptions1(prevState => {
            const newState = { ...prevState };
            if (option === 'ALL' && attribute !== 'localisation') {
                const newOptions = attribute === 'activityType' ? ACTIVITY_TYPES.filter(item => item !== 'ALL') :
                    attribute === 'climate' ? CLIMATES.filter(item => item !== 'ALL') :
                        attribute === 'landscape' ? LANDSCAPES.filter(item => item !== 'ALL') :
                            attribute === 'temperature' ? TEMPERATURES.filter(item => item !== 'ALL') :
                                prevState[attribute];
                return { ...prevState, [attribute]: newOptions };
            } else if (option === 'ALL' && attribute === 'localisation') {
                newState[attribute] = option;
            } else if (attribute === 'localisation') {
                newState[attribute] = option;
            } else {
                const index = newState[attribute].indexOf(option);
                if (index !== -1) {
                    newState[attribute] = newState[attribute].filter(item => item !== option);
                } else {
                    newState[attribute] = [...newState[attribute], option];
                }
            }
            console.log("Valeurs à jours", newState);
            return newState;
        });
=======
        setSelectedOptions1(prevState => {
            const newState = { ...prevState };
            if (option === 'ALL' && attribute === 'activityType') {
                const newOptions = attribute === 'activityType' ? ACTIVITY_TYPES.filter(item => item !== 'ALL') :
                                prevState[attribute];
                return { ...prevState, [attribute]: newOptions };
            } else if (option === 'ALL' && attribute === 'localisation') {
                newState[attribute] = option;
            } else if (attribute === 'localisation' || attribute === 'climate' || attribute === 'landscape' || attribute === 'temperature') {
                newState[attribute] = filterConstante(option);
            } else if (attribute === 'activityType') {
                if (newState[attribute].includes(filterConstante(option))) {
                    newState[attribute] = newState[attribute].filter(item => item !== filterConstante(option));
                } else {
                    newState[attribute] = [...newState[attribute], filterConstante(option)];
                }
            } else {
                newState[attribute] = option;
            }

            console.log(newState)
            return newState;
        });
>>>>>>> .r75
    };

    // Fonction pour gérer la sélection des options pour le deuxième ensemble de filtres
    const handleOptionSelect2 = (option, attribute) => {
<<<<<<< .mine
        setSelectedOptions2(prevState => ({ ...prevState, [attribute]: option }));
||||||| .r65
        setSelectedOptions2(prevState => {
            const newState = { ...prevState };
            if (option === 'ALL' && attribute !== 'localisation') {
                const newOptions = attribute === 'activityType' ? ACTIVITY_TYPES.filter(item => item !== 'ALL') :
                    attribute === 'climate' ? CLIMATES.filter(item => item !== 'ALL') :
                        attribute === 'landscape' ? LANDSCAPES.filter(item => item !== 'ALL') :
                            attribute === 'temperature' ? TEMPERATURES.filter(item => item !== 'ALL') :
                                prevState[attribute];
                return { ...prevState, [attribute]: newOptions };
            } else if (option === 'ALL' && attribute === 'localisation') {
                newState[attribute] = option;
            } else if (attribute === 'budget' || attribute === 'localisation') {
                newState[attribute] = option;
            } else {
                const index = newState[attribute].indexOf(option);
                if (index !== -1) {
                    newState[attribute] = newState[attribute].filter(item => item !== option);
                } else {
                    newState[attribute] = [...newState[attribute], option];
                }
            }
            console.log("Valeurs à jours", newState);
            return newState;
        });
=======
        setSelectedOptions2(prevState => {
            const newState = { ...prevState };

            if (option === 'ALL' && attribute === 'activityType') {
                const newOptions = ACTIVITY_TYPES.filter(item => item !== 'ALL').map(filterConstante);
                newState[attribute] = newOptions;
                return newState;
            } else if (option === 'ALL' && attribute !== 'activityType') {
                newState[attribute] = option;
                return newState;
            } else if (attribute === 'roadTrip') {
                newState[attribute] = option === 'TRUE';
            } else if (attribute === 'localisation' || attribute === 'climate' || attribute === 'landscape' || attribute === 'temperature') {
                newState[attribute] = filterConstante(option);
            } else if (attribute === 'activityType') {
                if (newState[attribute].includes(filterConstante(option))) {
                    newState[attribute] = newState[attribute].filter(item => item !== filterConstante(option));
                } else {
                    newState[attribute] = [...newState[attribute], filterConstante(option)];
                }
            } else {
                newState[attribute] = option;
            }

            console.log(newState)
            return newState;
        });
>>>>>>> .r75
    };
    // Fonction pour les boutons d'options
    const renderButtonOptions = (options, selectedOptions, attribute, handleOptionSelect) => {
        let title = attribute === "localisation" ? "Continent" : attribute;

<<<<<<< .mine
    // Fonction pour soumettre le formulaire
    const handleSubmit = (e) => {
        e.preventDefault();
        // Logique pour soumettre le questionnaire
    };
||||||| .r65
=======
        return (
            <div>
                <label>
                    <h2>{title}</h2>
                </label>
                {options.map(option => (
                    <button
                        key={option}
                        className={`option-button${selectedOptions && selectedOptions[attribute]?.includes(filterConstante(option)) ? ' active' : ''}`}
                        onClick={() => {
                            handleOptionSelect(option, attribute);
                        }}
                    >
                        {option}
                    </button>
                ))}
            </div>
        );
    };
>>>>>>> .r75

<<<<<<< .mine
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
||||||| .r65
    // Fonction pour les boutons d'options
    const renderButtonOptions = (options, selectedOptions, attribute, handleOptionSelect) => (
        <div>
            <label><h2>{attribute}</h2></label>
            {options.map(option => (
                <button
                    key={option}
                    className={`option-button${selectedOptions && selectedOptions[attribute]?.includes(option) ? ' active' : ''}`}
                    onClick={() => {
                        handleOptionSelect(option, attribute);
                    }}
                >
                    {option}
                </button>
            ))}
        </div>
    );
=======
    const handleClickSurvey1=(e)=> {
        e.preventDefault();
        // Créer une chaîne de requête avec les options sélectionnées
        const queryString = Object.keys(selectedOptions1)
            .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(selectedOptions1[key])}`)
            .join('&');
>>>>>>> .r75

<<<<<<< .mine
||||||| .r65
    // Fonction pour gérer la soumission du formulaire
    const handleSubmit = (e) => {
        e.preventDefault();
        // Handle form submission logic
    };
=======
        // Ajouter la chaîne de requête à l'URL de l'API
        const apiUrl = `http://localhost:9000/Suggestion/getSuggestAI?${queryString}`;
        console.log("tema l'url", apiUrl);
        // Effectuer la requête GET
        fetch(apiUrl, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        })
            .then(response => {
                // Vérifier le code de statut de la réponse
                if (response.ok) {
                    // Si la réponse est OK (200), vérifiez le type de contenu retourné
                    const contentType = response.headers.get("content-type");

                    if (contentType && contentType.includes("application/json")) {
                        // Si le type de contenu est JSON, parlez au serveur
                        return response.json();
                    } else {
                        // Sinon, gérer les autres types de contenu (par exemple, texte brut, HTML, etc.)
                        return response.text();
                    }
                } else {
                    // Si la réponse n'est pas OK, lancer une erreur
                    throw new Error('Network response was not ok');
                }
            })
            .then(data => {
                // Traiter les données renvoyées en fonction de leur type
                console.log(data);
                setSurveyData(data);
                console.log("Survey Data", surveyData);

            })
            .catch(error => {
                // Gérer les erreurs de la requête
                console.error('Error:', error);
            });
    }
    const handleClickTripSurvey = (e) => {
        e.preventDefault();
        // Créer une chaîne de requête avec les options sélectionnées
        const queryString = Object.keys(selectedOptions2)
            .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(selectedOptions2[key])}`)
            .join('&');

        // Ajouter la chaîne de requête à l'URL de l'API
        const apiUrl = `http://localhost:9000/tripSuggestion/getSuggestAI?${queryString}`;
        console.log("tema l'url", apiUrl);
        // Effectuer la requête GET
        fetch(apiUrl, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        })
            .then(response => {
                // Vérifier le code de statut de la réponse
                if (response.ok) {
                    // Si la réponse est OK (200), vérifiez le type de contenu retourné
                    const contentType = response.headers.get("content-type");

                    if (contentType && contentType.includes("application/json")) {
                        // Si le type de contenu est JSON, parlez au serveur
                        return response.json();
                    } else {
                        // Sinon, gérer les autres types de contenu (par exemple, texte brut, HTML, etc.)
                        return response.text();
                    }
                } else {
                    // Si la réponse n'est pas OK, lancer une erreur
                    throw new Error('Network response was not ok');
                }
            })
            .then(data => {
                // Traiter les données renvoyées en fonction de leur type
                console.log(data);
                setSurveyData(data);
                console.log("Survey Data", surveyData);

            })
            .catch(error => {
                // Gérer les erreurs de la requête
                console.error('Error:', error);
            });
    };
>>>>>>> .r75
    const handleCityClick = (cityData) => {
        setSelectedCity(cityData);
    };

<<<<<<< .mine
    // Rendu du composant Survey
||||||| .r65
=======

>>>>>>> .r75
    return (
<<<<<<< .mine
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
||||||| .r65
        <div>
            <div className="background-container"></div>
            <div className="survey-container">
                <img src={logo} alt="Logo" className="survey-logo" />
                <div className="filter-buttons">
                    <button className={`survey-button ${showFilter1 ? 'active' : ''}`} onClick={() => {
                        setShowFilter1(!showFilter1);
                        setShowFilter2(false);
                    }}>Survey
                    </button>
                    <button className={`survey-button ${showFilter2 ? 'active' : ''}`} onClick={() => {
                        setShowFilter2(!showFilter2);
                        setShowFilter1(false);
                    }}>Trip Survey
                    </button>
                </div>
                <div className="filter-container">
                    {showFilter1 && (
                        <div>
                            <h2>Localisation</h2>
                            <select
                                value={selectedOptions1.localisation.toString()}
                                onChange={(e) => handleOptionSelect1(e.target.value, 'localisation')}
                            >
                                <option value="ALL">All</option>
                                <option value="CONTINENT">Continent</option>
                                <option value="COUNTRY">Country</option>
                            </select>
                            {selectedOptions1.localisation === 'CONTINENT' && renderButtonOptions(CONTINENTS, selectedOptions1, 'localisation', handleOptionSelect1)}
                            {CONTINENTS.includes(selectedOptions1.localisation) && renderButtonOptions(CONTINENTS, selectedOptions1, 'localisation', handleOptionSelect1)}
                            {selectedOptions1.localisation === 'COUNTRY' && (
                                <Select
                                    options={countryOptions}
                                    value={countryOptions.find(option => option.value === selectedOptions1.localisation)}
                                    onChange={(selectedOption) => handleOptionSelect1(selectedOption.value, 'localisation')}
                                />
=======
        <div>
            <div className="background-container">
            </div>
            {surveyData && (
                <div className="result-container">
                    <h2>Suggestion</h2>
                    <div>
                        <p><strong>Country</strong></p>
                        <p>{surveyData['Country: ']}</p>
                        <p><strong>Description</strong></p>
                        <p>{surveyData['Description: ']}</p>
                        <p><strong>Continent</strong></p>
                        <p>{surveyData['Continent: ']}</p>
                        <p><strong>Departure date</strong></p>
                        <p>{surveyData['Departure date: ']}</p>
                        <p><strong>Arrival Date</strong></p>
                        <p>{surveyData['Arrival date: ']}</p>
                        <p><strong>Total Cost of activities</strong></p>
                        <p>{surveyData['Total Cost of Activities : ']}</p>
                        {surveyData["Cities"].length > 1 && (
                            <div>
                                {surveyData["Cities"].map((city, index) => (
                                    <button key={index} onClick={() => handleCityClick(city)}>
                                        {city['City: ']}
                                    </button>
                                ))}
                            </div>
                        )}
                        {selectedCity && (
                            <div>
                                <p><strong>City</strong></p>
                                <p>{selectedCity['City: ']}</p>
                                <p><strong>Transport</strong></p>
                                <p>{selectedCity['Transport: ']}</p>
                                <h3>Activities per Day :</h3>
                                {selectedCity['Days'].map((day, index) => (
                                    <div key={index}>
                                        <h4>Day {index + 1}</h4>
                                        {day['activities'].map((activity, idx) => (
                                            <div key={idx}>
                                                <p><strong>Activity:</strong> {activity['Activity']}</p>
                                                <p><strong>Type of
                                                    activity:</strong> {activity['Type of the activity']}</p>
                                                <p><strong>Description:</strong> {activity['Description']}</p>
                                                <p><strong>Price:</strong> {activity['Price']}</p>
                                            </div>
                                        ))}
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                </div>
            )}
            <div className="survey-container">
                <img src={logo} alt="Logo" className="survey-logo"/>
                <div className="filter-buttons">
                    <button className={`survey-button ${showFilter1 ? 'active' : ''}`} onClick={() => {
                        setShowFilter1(!showFilter1);
                        setShowFilter2(false);
                    }}>Survey
                    </button>
                    <button className={`survey-button ${showFilter2 ? 'active' : ''}`} onClick={() => {
                        setShowFilter2(!showFilter2);
                        setShowFilter1(false);
                    }}>Trip Survey
                    </button>
                </div>
                <div className="filter-container">
                    {showFilter1 && (
                        <div>
                            <h2>Localisation</h2>
                            <select
                                value={selectedOptions1.localisation.toString()}
                                onChange={(e) => handleOptionSelect1(e.target.value, 'localisation')}
                            >
                                <option value="ALL">All</option>
                                <option value="CONTINENT">Continent</option>
                                <option value="COUNTRY">Country</option>
                            </select>
                            {selectedOptions1.localisation === 'CONTINENT' && renderButtonOptions(CONTINENTS, selectedOptions1, 'localisation', handleOptionSelect1)}
                            {NEWCONTINENTS.includes(selectedOptions1.localisation) && renderButtonOptions(CONTINENTS, selectedOptions1, 'localisation', handleOptionSelect1)}
                            {selectedOptions1.localisation === 'COUNTRY' && (
                                <Select
                                    options={countryOptions}
                                    value={countryOptions.find(option => option.value === selectedOptions1.localisation)}
                                    onChange={(selectedOption) => handleOptionSelect1(selectedOption.value, 'localisation')}
                                />
>>>>>>> .r75

<<<<<<< .mine
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
||||||| .r65
                            )}
                            {COUNTRIES.includes(selectedOptions1.localisation) && (
                                <Select
                                    options={countryOptions}
                                    value={countryOptions.find(option => option.value === selectedOptions1.localisation)}
                                    onChange={(selectedOption) => handleOptionSelect1(selectedOption.value, 'localisation')}
                                />

                            )}
                            {renderButtonOptions(CONTINENTS, selectedOptions1, 'localisation', handleOptionSelect1)}
                            {renderButtonOptions(ACTIVITY_TYPES, selectedOptions1, 'activityType', handleOptionSelect1)}
                            {renderButtonOptions(CLIMATES, selectedOptions1, 'climate', handleOptionSelect1)}
                            {renderButtonOptions(LANDSCAPES, selectedOptions1, 'landscape', handleOptionSelect1)}
                            {renderButtonOptions(TEMPERATURES, selectedOptions1, 'temperature', handleOptionSelect1)}
                        </div>
                    )}
                    {showFilter2 && (
=======
                            )}
                            {COUNTRIES.includes(selectedOptions1.localisation) && (
                                <Select
                                    options={countryOptions}
                                    value={countryOptions.find(option => option.value === selectedOptions1.localisation)}
                                    onChange={(selectedOption) => handleOptionSelect1(selectedOption.value, 'localisation')}
                                />

                            )}
                            {renderButtonOptions(ACTIVITY_TYPES, selectedOptions1, 'activityType', handleOptionSelect1)}
                            {renderButtonOptions(CLIMATES, selectedOptions1, 'climate', handleOptionSelect1)}
                            {renderButtonOptions(LANDSCAPES, selectedOptions1, 'landscape', handleOptionSelect1)}
                            {renderButtonOptions(TEMPERATURES, selectedOptions1, 'temperature', handleOptionSelect1)}
                            <button className="submit-button-survey1" onClick={handleClickSurvey1}>
                                Submit
                            </button>

                        </div>

                    )}
                    {showFilter2 && (
>>>>>>> .r75
                        <div>
<<<<<<< .mine
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
||||||| .r65
                            <h2>Localisation</h2>
                            <select
                                value={selectedOptions2.localisation.toString()}
                                onChange={(e) => handleOptionSelect2(e.target.value, 'localisation')}
                            >
                                <option value="ALL">All</option>
                                <option value="CONTINENT">Continent</option>
                                <option value="COUNTRY">Country</option>
                            </select>
                            {selectedOptions2.localisation === 'CONTINENT' && renderButtonOptions(CONTINENTS, selectedOptions2, 'localisation', handleOptionSelect2)}
                            {CONTINENTS.includes(selectedOptions2.localisation) && renderButtonOptions(CONTINENTS, selectedOptions2, 'localisation', handleOptionSelect2)}
                            {selectedOptions2.localisation === 'COUNTRY' && (
                                <Select
                                    options={countryOptions}
                                    value={countryOptions.find(option => option.value === selectedOptions2.localisation)}
                                    onChange={(selectedOption) => handleOptionSelect2(selectedOption.value, 'localisation')}
                                />

                            )}
                            {COUNTRIES.includes(selectedOptions2.localisation) && (
                                <Select
                                    options={countryOptions}
                                    value={countryOptions.find(option => option.value === selectedOptions2.localisation)}
                                    onChange={(selectedOption) => handleOptionSelect2(selectedOption.value, 'localisation')}
                                />

                            )}
                            {renderButtonOptions(ACTIVITY_TYPES, selectedOptions2, 'activityType', handleOptionSelect2)}
                            {renderButtonOptions(CLIMATES, selectedOptions2, 'climate', handleOptionSelect2)}
                            {renderButtonOptions(LANDSCAPES, selectedOptions2, 'landscape', handleOptionSelect2)}
                            {renderButtonOptions(TEMPERATURES, selectedOptions2, 'temperature', handleOptionSelect2)}
                            <div>
                                <h2>Start of the journey</h2>
                                <input
                                    className="date-custom"
                                    type="date"
                                    value={selectedOptions2.startingDate || ''}
                                    onChange={(e) => handleOptionSelect2(e.target.value, 'startingDate')}
                                />
                                <div>
                                    <h2>End of the journey</h2>
                                    <input
                                        className="date-custom"
                                        type="date"
                                        value={selectedOptions2.endingDate || ''}
                                        onChange={(e) => handleOptionSelect2(e.target.value, 'endingDate')}
                                    />
                                </div>
                                <h2>Budget</h2>
                                <input
                                    className="budget-bar"
                                    type="text"
                                    value={selectedOptions2.budget || ''}
                                    onChange={(e) => handleOptionSelect2(e.target.value, 'budget')}
                                />
                            </div>
=======
                            <h2>Localisation</h2>
                            <select
                                value={selectedOptions2.localisation.toString()}
                                onChange={(e) => handleOptionSelect2(e.target.value, 'localisation')}
                            >
                                <option value="ALL">All</option>
                                <option value="CONTINENT">Continent</option>
                                <option value="COUNTRY">Country</option>
                            </select>
                            {selectedOptions2.localisation === 'CONTINENT' && renderButtonOptions(CONTINENTS, selectedOptions2, 'localisation', handleOptionSelect2)}
                            {NEWCONTINENTS.includes(selectedOptions2.localisation) && renderButtonOptions(CONTINENTS, selectedOptions2, 'localisation', handleOptionSelect2)}
                            {selectedOptions2.localisation === 'COUNTRY' && (
                                <Select
                                    options={countryOptions}
                                    value={countryOptions.find(option => option.value === selectedOptions2.localisation)}
                                    onChange={(selectedOption) => handleOptionSelect2(selectedOption.value, 'localisation')}
                                />

                            )}
                            {COUNTRIES.includes(selectedOptions2.localisation) && (
                                <Select
                                    options={countryOptions}
                                    value={countryOptions.find(option => option.value === selectedOptions2.localisation)}
                                    onChange={(selectedOption) => handleOptionSelect2(selectedOption.value, 'localisation')}
                                />

                            )}
                            {renderButtonOptions(ACTIVITY_TYPES, selectedOptions2, 'activityType', handleOptionSelect2)}
                            {renderButtonOptions(CLIMATES, selectedOptions2, 'climate', handleOptionSelect2)}
                            {renderButtonOptions(LANDSCAPES, selectedOptions2, 'landscape', handleOptionSelect2)}
                            {renderButtonOptions(TEMPERATURES, selectedOptions2, 'temperature', handleOptionSelect2)}
                            <h2>Road Trip</h2>
                            <div className="road-trip-buttons">
                                <button
                                    className={selectedOptions2.roadTrip ? 'active' : ''}
                                    onClick={() => handleOptionSelect2('TRUE', 'roadTrip')}
                                >
                                    Yes
                                </button>
                                <button
                                    className={!selectedOptions2.roadTrip ? 'active' : ''}
                                    onClick={() => handleOptionSelect2('FALSE', 'roadTrip')}
                                >
                                    No
                                </button>
                            </div>
                            <div>
                                <h2>Start of the journey</h2>
                                <input
                                    className="date-custom"
                                    type="date"
                                    value={selectedOptions2.startingDate || ''}
                                    onChange={(e) => handleOptionSelect2(e.target.value, 'startingDate')}
                                />
                                <div>
                                    <h2>End of the journey</h2>
                                    <input
                                        className="date-custom"
                                        type="date"
                                        value={selectedOptions2.endingDate || ''}
                                        onChange={(e) => handleOptionSelect2(e.target.value, 'endingDate')}
                                    />
                                </div>
                                <h2>Budget</h2>
                                <input
                                    className="budget-bar"
                                    type="text"
                                    value={selectedOptions2.budget || ''}
                                    onChange={(e) => handleOptionSelect2(e.target.value, 'budget')}
                                />
                                <button className="submit-button-survey2" onClick={handleClickTripSurvey}>
                                    Submit
                                </button>

                            </div>
>>>>>>> .r75
                        </div>
<<<<<<< .mine
                    </div>
                )}
||||||| .r65
                    )}
                </div>
                <div className="submit-form">
                    <h2>Survey Form</h2>
                    <form onSubmit={handleSubmit}>
                        <button type="submit">Submit</button>
                    </form>
                </div>
=======
                    )}
                </div>
>>>>>>> .r75
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
