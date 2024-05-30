import React, {useEffect, useState} from 'react';
import './css/Survey.css';
import Select from 'react-select'
import NavBar from './../components/NavBar'
import axios from 'axios';
import authService from "../services/auth.service";
import AuthService from "../services/auth.service";
import Colysee from "../assets/SeptMerveilles/Colysée.jpg"
import Chicken from "../assets/SeptMerveilles/Chichen Itza.jpg"
import Rio from "../assets/SeptMerveilles/Rio.jpg"
import Machu from "../assets/SeptMerveilles/Machu Picchu.jpg"
import Muraille from "../assets/SeptMerveilles/Muraille de Chine.jpg"
import TajMahal from "../assets/SeptMerveilles/TajMahal.jpg"
import Petra from "../assets/SeptMerveilles/Petra.jpg"
import Bejaia from "../assets/SeptMerveilles/Bejaia.jpg"
import Chennai from "../assets/SeptMerveilles/Chennai.jpg"
import Paris from "../assets/SeptMerveilles/Paris.jpg"
import Akabane from "../assets/SeptMerveilles/Akabane.jpg"


// Données pour les filtres
const ACTIVITY_TYPES = [
    'OUTDOOR 🌳', 'CULTURAL 🏛️', 'RELAXATION 🧘🏽', 'ADVENTURE 🌄', 'GASTRONOMIC 🍽️', 'ENTERTAINMENT 🎭', 'ROMANTIC 💖', 'HISTORICAL 🏰', 'ALL'
];
const CLIMATES = [
    'TROPICAL 🏝️', 'DESERT 🏜️', 'POLAR ❄️', 'MEDITERRANEAN 🏖️', 'TEMPERATE 🌤️', 'NEITHER'
];
const LANDSCAPES = [
    'MOUNTAIN ⛰️', 'BEACH 🏖️', 'FOREST 🌲', 'DESERT 🏜️', 'VALLEY 🏞️', 'COASTAL 🌊', 'RURAL 🏡', 'URBAN 🏙️', 'NEITHER'
];
const TEMPERATURES = [
    'HOT ☀️', 'WARM 🌼', 'MILD 🌿', 'COOL 🌬️', 'COLD ❄️', 'NEITHER'
];
const CONTINENTS = [
    'Africa 🐘', 'Antarctica 🐧', 'Asia 🐼', 'Europe 🐎', 'North America 🦅', 'Oceania 🦘', 'South America 🦥'
];
const NEWCONTINENTS =[
    'Africa', 'Antarctica', 'Asia', 'Europe', 'NORTH_AMERICA', 'Oceania', 'SOUTH_AMERICA'
];
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
const COUNTRIES = CountriesList.map(country => formatCountryName(country));
const countryOptions = COUNTRIES.map(country => ({ value: country, label: country }));
const images = [
    { title: 'Colysée', src: Colysee },
    { title: 'Chichen Itza', src: Chicken },
    { title: 'Rio', src: Rio },
    { title: 'Machu Picchu', src: Machu },
    { title: 'Muraille de Chine', src: Muraille },
    { title: 'Taj Mahal', src: TajMahal },
    { title: 'Petra', src: Petra },
    { title: 'Chennai', src: Chennai },
    { title: 'Bejaia', src: Bejaia },
    { title: 'Paris', src: Paris },
    { title: 'Akabane', src: Akabane }
];
const randomIndex = Math.floor(Math.random() * images.length);
const Survey = () => {


    let [dataTripSurvey, setDataTripSurvey] = useState(null);
    let [dataSurvey, setDataSurvey] = useState(null);
    const [surveyOrTrip, setSurveyOrTrip] = useState(null);
    const [surveyContinentOrCountry, setSurveyContinentOrCountry] = useState(null);
    const [showFilter1, setShowFilter1] = useState(false);
    const [showFilter2, setShowFilter2] = useState(false);

    const [selectedOptions1, setSelectedOptions1] = useState({
        localisation: 'ALL',
        activityType: [],
        climate: 'ALL',
        landscape: 'ALL',
        temperature: 'ALL'
    });
    const [selectedCityTripSurvey, setSelectedCityTripSurvey] = useState(null);
    const [selectedCitySurvey, setSelectedCitySurvey] = useState(null);
    const [selectedCountry, setSelectedCountry] = useState(null);
    const [selectedOptions2, setSelectedOptions2] = useState({
        localisation: 'ALL',
        activityType: [],
        climate: 'ALL',
        landscape: 'ALL',
        temperature: 'ALL',
        startingDate: '',
        endingDate: '',
        budget: 0,
        roadTrip: false
    });
    const handleSaveSuggestion = async () => {
        try {
            const user = AuthService.getCurrentUser();

            if (!user) {
                // Affichez un message d'erreur si l'utilisateur n'est pas connecté
                console.error("User not logged in");
                return;
            }

            const response = await axios.post('http://localhost:9000/registerSuggestion/putDestSuggest', {dataSurvey}, {
                headers: {
                    'Authorization': `Bearer ${user.accessToken}`
                }
            });

            // Vérifiez le statut de la réponse pour confirmer que la suggestion a été envoyée avec succès
            if (response.status === 200) {
                setSuccess(true);
                // Cacher le message après 3 secondes
                setTimeout(() => {setSuccess(false);
                }, 1000);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };
    const handleSaveTripSuggestion = async () => {
        try {
            const user = authService.getCurrentUser();

            if (!user) {
                // Affichez un message d'erreur si l'utilisateur n'est pas connecté
                return;
            }
            const response = await axios.post('http://localhost:9000/registerSuggestion/putTripSuggest', {dataTripSurvey},
                {
                    headers: {
                        'Authorization': `Bearer ${user.accessToken}`
                    }
                });
            if (response.status === 200) {
                setSuccess(true);
                // Cacher le message après 3 secondes
                setTimeout(() => {setSuccess(false);
                }, 1000);
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    useEffect(() => {
        if (
            selectedOptions1.localisation === 'CONTINENT' ||
            selectedOptions1.localisation === 'ALL' ||
            NEWCONTINENTS.includes(selectedOptions1.localisation)
        ) {
            setSurveyContinentOrCountry(true);
        } else if (
            selectedOptions1.localisation === 'COUNTRY' ||
            CountriesList.includes(selectedOptions1.localisation)
        ) {
            setSurveyContinentOrCountry(false);
        }else {
            setSurveyContinentOrCountry(null);
        }
    }, [selectedOptions1.localisation]);
    const filterConstante = (valeur) => {
        // Remplace tous les caractères après un espace par une chaîne vide
        if(valeur === "North America 🦅"){
            return "NORTH_AMERICA";
        }else if(valeur === "South America 🦥"){
            return "SOUTH_AMERICA";
        }
        return valeur.replace(/ .*/, '');
    };
    const handleOptionSelectSurvey = (option, attribute) => {
        setSelectedOptions1(prevState => {
            const newState = { ...prevState };
            if(option ==='NEITHER'){
                option ="ALL"
                newState[attribute] = option;
            }
            if (option === 'ALL' && attribute === 'activityType') {
                const newOptions = ACTIVITY_TYPES.filter(item => item !== 'ALL').map(filterConstante);
                newState[attribute] = newOptions;
                return newState;
            } else if (option === 'ALL' && attribute !== 'activityType') {
                newState[attribute] = option;
                return newState;
            } else if (attribute === 'localisation' || attribute === 'climate' || attribute === 'landscape' || attribute === 'temperature') {
                newState[attribute] = filterConstante(option);
            } else if (attribute === 'activityType') {
                if (newState[attribute].includes(filterConstante(option))) {
                    newState[attribute] = newState[attribute].filter(item => item !== filterConstante(option));
                } else {
                    newState[attribute] = [...newState[attribute], filterConstante(option)];
                }
            }else {
                newState[attribute] = option;
            }

            console.log(newState)
            return newState;
        });
    };
    const handleOptionSelectTripSurvey = (option, attribute) => {
        setSelectedOptions2(prevState => {
            const newState = { ...prevState };
            if(option ==='NEITHER'){
                option ="ALL"
                newState[attribute] = option;
            }
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
            }else {
                newState[attribute] = option;
            }

            console.log(newState)
            return newState;
        });
    };
    // Fonction pour les boutons d'options
    const renderButtonOptions = (options, selectedOptions, attribute, handleOptionSelect) => {
        // Cloner l'objet selectedOptions pour éviter les effets de bord
        let tempOptions = { ...selectedOptions };

        // Vérifier si l'attribut est "landscape", "climate" ou "temperature" et que tempOptions[attribute] est "ALL"
        if ((attribute === "landscape" || attribute === "climate" || attribute === "temperature") && tempOptions[attribute] === "ALL") {
            tempOptions[attribute] = "NEITHER";
        }

        let title = attribute === "localisation" ? "Continent" : attribute;
        if (attribute === "activityType") {
            title = "Activity to do";
        } else if (attribute === "climate") {
            title = "Preferential climate";
        } else if (attribute === "landscape") {
            title = "Landscape";
        } else if (attribute === "temperature") {
            title = "Temperature";
        }

        return (
            <div>
                <label>
                    <h3>{title}</h3>
                </label>
                {options.map(option => (
                    <button
                        key={option}
                        className={`option-button${tempOptions[attribute]?.includes(filterConstante(option)) ? ' active' : ''}`}
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
    const handleClickSurvey = (e) => {
        e.preventDefault();
        setSurveyOrTrip(true);
        setLoading(true); // Début de la requête, afficher la barre de chargement

        // Créer une chaîne de requête avec les options sélectionnées
        const queryString = Object.keys(selectedOptions1)
            .map((key) => `${encodeURIComponent(key)}=${encodeURIComponent(selectedOptions1[key])}`)
            .join('&');

        // Ajouter la chaîne de requête à l'URL de l'API
        const apiUrl = `http://localhost:9000/suggestion/getSuggestAI?${queryString}`;
        console.log("tema l'url", apiUrl);

        // Effectuer la requête GET
        fetch(apiUrl, {
            method: "GET",
            headers: { "Content-Type": "application/json" },
        })
            .then((response) => {
                // Masquer la barre de chargement après la réception de la réponse
                setLoading(false);

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
                    throw new Error("Network response was not ok");
                }
            })
            .then((data) => {
                setDataSurvey(data);
                console.log("Survey Data", data);
                console.log("Survey Data avec dataSurvey", dataSurvey);
            })
            .catch((error) => {
                // Masquer la barre de chargement en cas d'erreur
                setLoading(false);
                // Gérer les erreurs de la requête
                console.error("Error:", error);
            });
    };

    const handleClickTripSurvey = (e) => {
        e.preventDefault();
        setSurveyOrTrip(false);
        setLoading(true); // Afficher la barre de chargement au début de la requête

        // Créer une chaîne de requête avec les options sélectionnées
        const queryString = Object.keys(selectedOptions2)
            .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(selectedOptions2[key])}`)
            .join('&');

        // Ajouter la chaîne de requête à l'URL de l'API
        const apiUrl = `http://localhost:9000/tripSuggestion/getTripSuggestAI?${queryString}`;
        console.log("tema l'url", apiUrl);
        // Effectuer la requête GET
        fetch(apiUrl, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        })
            .then(response => {
                // Masquer la barre de chargement après la réception de la réponse
                setLoading(false);

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
                setDataTripSurvey(data);
                console.log("Trip Survey Data", dataTripSurvey);
            })
            .catch(error => {
                // Gérer les erreurs de la requête
                console.error('Error:', error);
            });
    };

    const handleClickRegenerate = (e) => {
        e.preventDefault();
        let apiUrl;
        let destinationUrl;
        setLoading(true);

        if (surveyOrTrip) {
            // Si surveyOrTrip est true, construire l'URL pour la suggestion de destination
            const queryString = Object.keys(selectedOptions1)
                .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(selectedOptions1[key])}`)
                .join('&');
            destinationUrl = `http://localhost:9000/suggestion/getNewSuggestAI?${queryString}`;
            fetch(destinationUrl, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            })
                .then(response => {
                    setLoading(false);
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
                    setDataSurvey(data);
                    console.log("Survey Data", dataSurvey);
                    console.log();

                })
                .catch(error => {
                    // Gérer les erreurs de la requête
                    console.error('Error:', error);
                });
        } else {
            // Si surveyOrTrip est false, construire l'URL pour la suggestion de voyage
            const queryString = Object.keys(selectedOptions2)
                .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(selectedOptions2[key])}`)
                .join('&');
            destinationUrl = `http://localhost:9000/tripSuggestion/getNewTripSuggestionAI?${queryString}`;
            fetch(destinationUrl, {
                method: "GET",
                headers: { "Content-Type": "application/json" }
            })
                .then(response => {
                    setLoading(false);
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
                    setDataTripSurvey(data);
                    console.log("Trip Survey Data", dataTripSurvey);
                    console.log();

                })
                .catch(error => {
                    // Gérer les erreurs de la requête
                    console.error('Error:', error);
                });
        }

        console.log("URL de suggestion :", apiUrl);
        console.log("URL de destination :", destinationUrl);

    };
    const handleCityClickTripSurvey = (cityData) => {
        setSelectedCityTripSurvey(cityData);
    };
    const handleCityClickSurvey = (cityData) => {
        setSelectedCitySurvey(cityData);
    };
    const handleCountryClick = (country) => {
        setSelectedCountry(country);

    };
    const getDayNumber = (activity) => {
        const activityKey = Object.keys(activity)[0];
        return activityKey ? parseInt(activityKey) : null;
    };

    const [loading, setLoading] = useState(false);

    const [success, setSuccess] = useState(false); // New state for success message

    return (
        <>
            <div className="image-container" style={{position: 'relative', overflow: 'hidden'}}>
                <div style={{position: 'relative', width: '100%', paddingBottom: '75%'}}>
                    <img src={images[randomIndex].src} alt="Selected" style={{
                        position: 'center',
                        backgroundSize: 'cover',
                        top: '0',
                        left: '0',
                        width: '100%',
                        height: '100%',
                        objectFit: 'cover'
                    }}/>
                    <div style={{ position: 'absolute', top: '0', left: '0', width: '100%', zIndex: '2' }}>
                        <NavBar />
                    </div>

                </div>
                {((surveyContinentOrCountry && surveyOrTrip && dataSurvey)||(!surveyOrTrip && dataTripSurvey && dataTripSurvey['Country'])||(surveyOrTrip && !surveyContinentOrCountry && dataSurvey)) && (
                    <div className="country-text-container">
                    {surveyContinentOrCountry && surveyOrTrip && dataSurvey && dataSurvey.Suggestions && dataSurvey.Suggestions.map((suggestion, index) => (
                        suggestion.Country &&
                        <button className="country-button" key={index} onClick={() => handleCountryClick(suggestion)}>
                            {suggestion.Country.Country}
                        </button>
                    ))}
                    <h2>{surveyOrTrip ? surveyContinentOrCountry && selectedCountry && selectedCountry.Country.Country : dataTripSurvey && dataTripSurvey['Country']}</h2>
                    <h2>{surveyOrTrip && !surveyContinentOrCountry && dataSurvey && dataSurvey.Country}</h2>
                    <h4 className="country-description">{surveyOrTrip ? surveyContinentOrCountry && selectedCountry && selectedCountry.Country.Description : dataTripSurvey && dataTripSurvey['Description']}</h4>
                    <h4 className="country-description">{surveyOrTrip && !surveyContinentOrCountry && dataSurvey && dataSurvey.Description}</h4>
                    </div>
                )}

                <div className="button-line-container">
                    <button className={`survey-button ${showFilter1 ? 'active' : ''}`} onClick={() => {
                        setShowFilter1(!showFilter1);
                        setShowFilter2(false);
                    }}>Survey
                    </button>
                    <button className="regenerate-button" onClick={handleClickRegenerate}>Regenerate</button>
                    <button className={`survey-button ${showFilter2 ? 'active' : ''}`} onClick={() => {
                        setShowFilter2(!showFilter2);
                        setShowFilter1(false);
                    }}>Trip Survey
                    </button>
                    {loading && <div className="loader"></div>}
                </div>
            </div>

            <div className="city-activities-container">
                <div className="city-text-container">
                    {surveyOrTrip && surveyContinentOrCountry && selectedCountry && (
                        <div>
                            <h2 className="city-name">{selectedCountry.City.City}</h2>
                            <h4 className="city-description">{selectedCountry.City.Description}</h4>
                            <h4 className="city-climate">{selectedCountry.City.Climate.charAt(0).toUpperCase() + selectedCountry.City.Climate.slice(1).toLowerCase()} climate
                                will await you for this amazing journey.</h4>
                            <h4 className="city-temperature">Temperatures you may encounter during your
                                trip: {selectedCountry.City.Temperature.toLowerCase()}</h4>
                            <h4 className="city-transport">Transport
                                available: {selectedCountry.City.Transport}</h4>

                        </div>
                    )}

                    {surveyOrTrip && !surveyContinentOrCountry && dataSurvey && (
                        <div>
                            {dataSurvey.Suggestions.map((suggestion, index) => (
                                <button className="city-button" key={index}
                                        onClick={() => handleCityClickSurvey(suggestion)}>
                                    {suggestion.City.City}
                                </button>
                            ))}
                            {selectedCitySurvey && selectedCitySurvey.City && (
                                <div>
                                    <h2 className="city-name">{selectedCitySurvey.City.City}</h2>
                                    <h4 className="city-description">{selectedCitySurvey.City.Description}</h4>
                                    <h4 className="city-temperature">Temperatures you may encounter during your
                                        trip: {selectedCitySurvey.City.Temperature.toLowerCase()}</h4>
                                    <h4 className="city-transport">Transport
                                        available: {selectedCitySurvey.City.Transport}</h4>
                                </div>
                            )}
                        </div>
                    )}

                    {!surveyOrTrip && dataTripSurvey && dataTripSurvey["Cities"].length > 1 ? (
                        <div>
                            {dataTripSurvey["Cities"].map((city, index) => (
                                <button className="city-button" key={index}
                                        onClick={() => handleCityClickTripSurvey(city)}>
                                    {city["City"]}
                                </button>
                            ))}
                            {selectedCityTripSurvey && (
                                <div>
                                    <h2 className="city-name">{selectedCityTripSurvey["City"]}</h2>
                                    <h4 className="city-description">{selectedCityTripSurvey["Description"]}</h4>
                                    <h4 className="city-temperature">Temperatures you may encounter during your
                                        trip: {selectedCityTripSurvey["Temperature"].toLowerCase()}</h4>
                                    <h4 className="city-transport">Transport
                                        available: {selectedCityTripSurvey["Transport"]}</h4>
                                </div>
                            )}
                        </div>
                    ) : (
                        dataTripSurvey && dataTripSurvey["Cities"].length === 1 && (
                            <div>
                                <h2 className="city-name">{dataTripSurvey["Cities"][0]["City"]}</h2>
                                <h3 className="city-description">{dataTripSurvey["Cities"][0]['Description']}</h3>
                                <h3 className="city-temperature">Temperatures you may encounter during your
                                    trip: {dataTripSurvey["Cities"][0]['Temperature'].toLowerCase()}</h3>
                                <h3 className="city-transport">Transport
                                    available: {dataTripSurvey["Cities"][0]['Transport']}</h3>
                            </div>
                        )
                    )}

                    <h2 className="Lets-do-activities">{surveyOrTrip ? "Let's do some activities" :
                        (selectedCountry ? `Let's do some activities` :
                            (dataTripSurvey ? `Let's do some activities (${dataTripSurvey["Total Cost of Activities"]}$)` : ""))}</h2>
                    <h3 className="trip-text-description">{!surveyOrTrip && dataTripSurvey && `From ${dataTripSurvey["Departure date"].split("-").reverse().join("-")} to ${dataTripSurvey["Arrival date"].split("-").reverse().join("-")}`}</h3>
                    <h3 className="trip-text-description">{selectedCityTripSurvey && ` ${selectedCityTripSurvey["Total Cost of Activities for this city"]}$ is enough to enjoy your time in this city`}</h3>
                </div>
                <div className="square-line-container">
                    {surveyOrTrip && surveyContinentOrCountry && selectedCountry && selectedCountry.Activities && (
                        <div className="square-line-container">
                            {selectedCountry.Activities.map((activity, index) => (
                                <div key={index} className="square">
                                    <p className="activity-name">{activity.Name}</p>
                                    <p className="activity-description">{activity.Description}</p>
                                    <p>{activity["Type of the activity"]}</p>
                                    <p>Price: {activity.Price}$</p>
                                </div>
                            ))}
                            {AuthService.getCurrentUser() && (
                                <button className="save-button" onClick={handleSaveSuggestion}>
                                    Save suggestion
                                </button>
                        )}
                            {success && <div style={{ color: "green" }}>Suggestion saved successfully!</div>} {/* Success message */}
                        </div>
                    )}
                    {surveyOrTrip && !surveyContinentOrCountry && selectedCitySurvey && (
                        <div>
                            <div className="square-line-container">
                                {selectedCitySurvey.Activities && selectedCitySurvey.Activities.map((activity, index) => (
                                    <div key={index} className="square">
                                        <p className="activity-name">{activity["Name"]}</p>
                                        <p className="activity-description">{activity["Description"]}</p>
                                        <p>{activity["Type of the activity"]}</p>
                                        <p>Price: {activity["Price"]}$</p>
                                    </div>
                                ))}
                            </div>
                            {AuthService.getCurrentUser() && (
                                <button className="save-button" onClick={handleSaveSuggestion}>
                                    Save suggestion
                                </button>
                            )}
                            {success && <div style={{ color: "green" }}>Suggestion saved successfully!</div>} {/* Success message */}
                        </div>
                    )}

                    {!surveyOrTrip && dataTripSurvey && dataTripSurvey["Cities"].length === 1 && (
                        <div className="square-line-container">
                            {dataTripSurvey["Cities"][0]['Days'].map((day, index) => (
                                <div key={index} className="square">
                                    <p className="day-name">Day {index + 1}</p>
                                    {day.activities.map((activity, activityIndex) => (
                                        <div key={activityIndex}>
                                            {activityIndex > 0 && (
                                                <div>
                                                    <p className="activity-name">Activity {activityIndex}: {activity["Activity"]}</p>
                                                    <p className="activity-description">{activity["Description"]}</p>
                                                    <p>{activity["Type of the activity"]}</p>
                                                    <p>Price: {activity["Price"]}$</p>
                                                </div>
                                            )}
                                        </div>
                                    ))}
                                </div>
                            ))}
                            {AuthService.getCurrentUser() && (
                                <button className="save-button" onClick={handleSaveTripSuggestion}>
                                    Save suggestion
                                </button>
                            )}
                            {success && <div style={{ color: "green" }}>Suggestion saved successfully!</div>} {/* Success message */}
                        </div>
                    )}
                    {!surveyOrTrip && dataTripSurvey && selectedCityTripSurvey && dataTripSurvey["Cities"].length > 1 && (
                        <div className="square-line-container">
                            {selectedCityTripSurvey["Days"].map((day, index) => (
                                <div key={index} className="square">
                                    <h3>Day {getDayNumber(day.activities[0])}</h3>
                                    {day.activities.map((activity, activityIndex) => (
                                        <div key={activityIndex}>
                                            {activityIndex > 0 && (
                                                <div>
                                                    <p className="activity-name">Activity {activityIndex}: {activity["Activity"]}</p>
                                                    <p className="activity-description">{activity["Description"]}</p>
                                                    <p>{activity["Type of the activity"]}</p>
                                                    <p>Price: {activity["Price"]}$</p>
                                                </div>
                                            )}
                                        </div>
                                    ))}
                                </div>
                            ))}
                            {AuthService.getCurrentUser() && (
                                <button className="save-button" onClick={handleSaveTripSuggestion}>
                                    Save suggestion
                                </button>
                            )}
                            {success && <div style={{ color: "green" }}>Suggestion saved successfully!</div>} {/* Success message */}
                        </div>
                    )}
                </div>
            </div>

            <div className={`survey-container ${showFilter1 || showFilter2 ? 'visible' : ''}`}>
                {showFilter1 && (
                    <div>
                        <h2>Survey</h2>
                        <h3>Localisation</h3>
                        <select className="select-localisation"
                                value={
                                    (selectedOptions1.localisation === "CONTINENT" || NEWCONTINENTS.includes(selectedOptions1.localisation))
                                        ? "CONTINENT"
                                        : (selectedOptions1.localisation === "COUNTRY" || CountriesList.includes(selectedOptions1.localisation))
                                            ? "COUNTRY"
                                            : "ALL"
                                }
                                onChange={(e) => handleOptionSelectSurvey(e.target.value, 'localisation')}
                        >
                            <option value="ALL">All</option>
                            <option value="CONTINENT">Continent</option>
                            <option value="COUNTRY">Country</option>
                        </select>
                        {console.log("selectedOptions 1 dans Survey", selectedOptions1)}
                        {console.log("selectedOptions 2 dans Survey", selectedOptions2)}
                        {selectedOptions1.localisation === 'CONTINENT' && renderButtonOptions(CONTINENTS, selectedOptions1, 'localisation', handleOptionSelectSurvey)}
                        {NEWCONTINENTS.includes(selectedOptions1.localisation) && renderButtonOptions(CONTINENTS, selectedOptions1, 'localisation', handleOptionSelectSurvey)}
                        {(selectedOptions1.localisation === 'COUNTRY'||CountriesList.includes(selectedOptions1.localisation)) && (
                            <div>
                                <h3>Country</h3>
                                <Select
                                    className="country-select"
                                    options={countryOptions}
                                    value={countryOptions.find(option => option.value === selectedOptions1.localisation)}
                                    onChange={(selectedOption) => {
                                        {
                                            console.log("selectedOption.value", selectedOption.value)
                                        }
                                        const index = countryOptions.findIndex(option => option.value === selectedOption.value);
                                        const selectedLocalisation = CountriesList[index];
                                        handleOptionSelectSurvey(selectedLocalisation, 'localisation');
                                    }}
                                />
                            </div>
                        )}

                        {renderButtonOptions(ACTIVITY_TYPES, selectedOptions1, 'activityType', handleOptionSelectSurvey)}
                        {renderButtonOptions(CLIMATES, selectedOptions1, 'climate', handleOptionSelectSurvey)}
                        {renderButtonOptions(LANDSCAPES, selectedOptions1, 'landscape', handleOptionSelectSurvey)}
                        {renderButtonOptions(TEMPERATURES, selectedOptions1, 'temperature', handleOptionSelectSurvey)}
                        <h3>Let's generate the journey !</h3>
                        <button className="submit-button" onClick={handleClickSurvey}>
                            Submit
                        </button>
                    </div>
                )}

                {showFilter2 && (
                    <div>
                        <h2>Trip Survey</h2>
                        <h3>Localisation</h3>
                        <select className="select-localisation"
                                value={
                                    (selectedOptions2.localisation === "CONTINENT" || NEWCONTINENTS.includes(selectedOptions2.localisation))
                                        ? "CONTINENT"
                                        : (selectedOptions2.localisation === "COUNTRY" || CountriesList.includes(selectedOptions2.localisation))
                                            ? "COUNTRY"
                                            : "ALL"
                                }
                                onChange={(e) => handleOptionSelectTripSurvey(e.target.value, 'localisation')}
                        >
                            <option value="ALL">All</option>
                            <option value="CONTINENT">Continent</option>
                            <option value="COUNTRY">Country</option>
                        </select>
                        {console.log("selectedOptions 1 dans TripSurvey", selectedOptions1)}
                        {console.log("selectedOptions 2 dans TripSurvey", selectedOptions2)}
                        {selectedOptions2.localisation === 'CONTINENT' && renderButtonOptions(CONTINENTS, selectedOptions2, 'localisation', handleOptionSelectTripSurvey)}
                        {NEWCONTINENTS.includes(selectedOptions2.localisation) && renderButtonOptions(CONTINENTS, selectedOptions2, 'localisation', handleOptionSelectTripSurvey)}
                        {(selectedOptions2.localisation === 'COUNTRY' ||CountriesList.includes(selectedOptions2.localisation)) && (
                            <div>
                                <h3>Country</h3>
                                <Select
                                    className="country-select"
                                    options={countryOptions}
                                    value={countryOptions.find(option => option.value === selectedOptions2.localisation)}
                                    onChange={(selectedOption) => {
                                        const index = countryOptions.findIndex(option => option.value === selectedOption.value);
                                        const selectedLocalisation = CountriesList[index];
                                        handleOptionSelectTripSurvey(selectedLocalisation, 'localisation');
                                    }}
                                />
                            </div>
                        )}

                        {renderButtonOptions(ACTIVITY_TYPES, selectedOptions2, 'activityType', handleOptionSelectTripSurvey)}
                        {renderButtonOptions(CLIMATES, selectedOptions2, 'climate', handleOptionSelectTripSurvey)}
                        {renderButtonOptions(LANDSCAPES, selectedOptions2, 'landscape', handleOptionSelectTripSurvey)}
                        {renderButtonOptions(TEMPERATURES, selectedOptions2, 'temperature', handleOptionSelectTripSurvey)}
                        <h3>Want to travel different cities</h3>
                        <div className="road-trip-buttons">
                            <button
                                className={selectedOptions2.roadTrip ? 'active' : ''}
                                onClick={() => handleOptionSelectTripSurvey('TRUE', 'roadTrip')}
                            >
                                Yes
                            </button>
                            <button
                                className={!selectedOptions2.roadTrip ? 'active' : ''}
                                onClick={() => handleOptionSelectTripSurvey('FALSE', 'roadTrip')}
                            >
                                No
                            </button>
                        </div>
                        <div>
                            <h3>Start of the journey</h3>
                            <input
                                className="date-custom"
                                type="date"
                                value={selectedOptions2.startingDate || ''}
                                onChange={(e) => handleOptionSelectTripSurvey(e.target.value, 'startingDate')}
                            />
                            <div>
                                <h3>End of the journey</h3>
                                <input
                                    className="date-custom"
                                    type="date"
                                    value={selectedOptions2.endingDate || ''}
                                    onChange={(e) => handleOptionSelectTripSurvey(e.target.value, 'endingDate')}
                                />
                            </div>
                            <h3>Budget</h3>
                            <input
                                className="budget-bar"
                                type="text"
                                value={selectedOptions2.budget || ''}
                                onChange={(e) => handleOptionSelectTripSurvey(e.target.value, 'budget')}
                            />
                            <h3>Let's generate the journey !</h3>
                            <button className="submit-button" onClick={handleClickTripSurvey}>
                                Submit
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </>
    );
};

export default Survey;
