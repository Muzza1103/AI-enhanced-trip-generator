import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './Pages/HomePage';
import Survey from './Pages/Survey';
import Login from "./components/Login";
import AboutUs from "./components/AboutUs";
import ContactUs from "./components/ContactUs";
import History from "./components/History";
import Footer from "./components/Footer";
import Profil from "./components/info";
import TripHistory from "./components/TripHistory";
import PrivacyPolicy from "./components/PrivacyPolicy";
import TermsOfService from "./components/TermsOfService";
import Licences from "./components/Licences";

function App() {
    console.log("LA CLE", process.env.REACT_APP_UNSPLASH_ACCESS_KEY);
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/aboutus" element={<AboutUs />} />
                <Route path="/contactus" element={<ContactUs />} />
                <Route path="/history" element={<History />} />
                <Route path="/triphistory" element={<TripHistory />} />
                <Route path="/" element={<HomePage />} />
                <Route path="/survey" element={<Survey />} />
                <Route path="/info" element={<Profil />} />
                <Route path="/privacypolicy" element={<PrivacyPolicy />} />
                <Route path="/termsofservice" element={<TermsOfService />} />
                <Route path="/licences" element={<Licences />} />
            </Routes>
            <Footer/>
        </Router>
    );
};

export default App;