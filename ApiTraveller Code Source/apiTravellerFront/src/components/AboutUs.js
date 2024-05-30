import React from 'react';
import './css/AboutUs.css';
import NavBar from './NavBar';
import Tharsi from '../assets/team/tharsi.jpg';
import Thibhan from '../assets/team/thibhan.jpg';
import Muzza from '../assets/team/muzza.jpg';
import Rayan from '../assets/team/rayan.jpg';

function AboutUs() {
    return (
        <div className={"test"}>
            <NavBar />
            <div className="about-api-container">
                <h1 className="titre-h1">What is Api Traveller ?</h1>
                <div className="text-container">
                    <h3 className="description-api">
                        Api Traveller is an innovative API designed to revolutionize the way people plan their
                        travels. Built to seamlessly integrate into web interfaces, Api Traveller caters to individuals
                        seeking a hassle-free approach to trip organization. Whether you're overwhelmed by the logistics
                        or simply in need of inspiration, Api Traveller has you covered.<br /><br />

                        The premise is simple: instead of spending hours scouring the internet for destination ideas and
                        activity recommendations, users can now streamline the entire process with just a few clicks. By
                        completing a brief survey outlining their preferences, budget, and travel aspirations, users
                        empower Api Traveller to curate personalized travel itineraries tailored specifically to their
                        needs.<br /><br />

                        From suggesting exotic countries to pinpointing vibrant cities and compiling a diverse array of
                        activities, Api Traveller takes the guesswork out of travel planning. By leveraging advanced
                        algorithms and a vast database of destinations and attractions, Api Traveller ensures that every
                        journey is not just memorable, but also uniquely tailored to suit the individual
                        traveler.<br /><br />

                        Gone are the days of tedious research and decision fatigue. With Api Traveller, embark on your
                        next adventure with confidence, knowing that every aspect of your trip has been expertly curated
                        to deliver an unforgettable experience.
                    </h3>
                </div>
            </div>

            <section className="section-white">
                <h1 className="titre-h1">The team behind Api Traveller</h1>
                <div className="team-container">
                    <div className="team-item">
                        <img src={Muzza} className="team-img" alt="pic"/>
                        <h3>Muzzammil Mougamadou</h3>
                        <div className="team-info">
                            <p>Computer Science Student</p>
                            <p>Muzzammil, 21 years old, is a 3rd year computer science degree student at Paris-Cité University.</p>
                            <ul className="team-icon">
                                <li className="social-icon"><a href="/" className="twitter">
                                    <i className="fa-brands fa-square-x-twitter"></i>
                                </a></li>
                                <li className="social-icon"><a href="https://www.instagram.com/muzza1103"
                                                               className="instagram">
                                    <i className="fab fa-instagram-square"></i>
                                </a></li>
                                <li className="social-icon"><a
                                    href="https://fr.linkedin.com/in/muzzammil-mougamadou-021a082ba"
                                    className="linkedIn">
                                    <i className="fab fa-linkedin"></i>
                                </a></li>
                                <li className="social-icon"><a href="https://github.com/Muzza1103" className="github">
                                    <i className="fab fa-github-square"></i>
                                </a></li>
                            </ul>
                        </div>
                    </div>
                    <div className="team-item">
                        <img src={Rayan} className="team-img" alt="pic"/>
                        <h3>Rayan Zaidi</h3>
                        <div className="team-info">
                            <p>Computer Science Student</p>
                            <p>Rayan, 22 years old, is a 3rd year computer science degree student at
                                Paris-Cité University.</p>
                            <ul className="team-icon">
                                <li className="social-icon"><a href="/" className="twitter">
                                    <i className="fa-brands fa-square-x-twitter"></i>
                                </a></li>
                                <li className="social-icon"><a href="/" className="instagram">
                                    <i className="fab fa-instagram-square"></i>
                                </a></li>
                                <li className="social-icon"><a href="https://fr.linkedin.com/in/rayan-zaidi-827044234"
                                                               className="linkedIn">
                                    <i className="fab fa-linkedin"></i>
                                </a></li>
                                <li className="social-icon"><a href="https://github.com/Fuso11" className="github">
                                    <i className="fab fa-github-square"></i>
                                </a></li>
                            </ul>
                        </div>
                    </div>
                    <div className="team-item">
                        <img src={Tharsi} className="team-img" alt="pic"/>
                        <h3>Tharsikan Vallipuram</h3>
                        <div className="team-info">
                            <p>Computer Science Student</p>
                            <p>Tharsikan, 20 years old, is a 3rd year computer science degree student at Paris-Cité
                                University.</p>
                            <ul className="team-icon">
                                <li className="social-icon"><a href="https://twitter.com/TharsikanV"
                                                               className="twitter">
                                    <i className="fa-brands fa-square-x-twitter"></i>
                                </a></li>
                                <li className="social-icon"><a href="https://www.instagram.com/tharsi.94"
                                                               className="instagram">
                                    <i className="fab fa-instagram-square"></i>
                                </a></li>
                                <li className="social-icon"><a
                                    href="https://fr.linkedin.com/in/tharsikan-vallipuram-4743872bb"
                                    className="linkedIn">
                                    <i className="fab fa-linkedin"></i>
                                </a></li>
                                <li className="social-icon"><a href="https://github.com/tharsi12" className="github">
                                    <i className="fab fa-github-square"></i>
                                </a></li>
                            </ul>
                        </div>
                    </div>
                    <div className="team-item">
                        <img src={Thibhan} className="team-img" alt="pic"/>
                        <h3>Thibhan Ponnampalam</h3>
                        <div className="team-info">
                            <p>Computer Science Student</p>
                            <p>Thibhan, 24 years old, is a 3rd year computer science degree student at Paris-Cité
                                University.</p>
                            <ul className="team-icon">
                                <li className="social-icon"><a href="/" className="twitter">
                                    <i className="fa-brands fa-square-x-twitter"></i>
                                </a></li>
                                <li className="social-icon"><a href="https://www.instagram.com/thibhan_31"
                                                               className="instagram">
                                    <i className="fab fa-instagram-square"></i>
                                </a></li>
                                <li className="social-icon"><a
                                    href="https://fr.linkedin.com/in/thibhan-ponnampalam-9b03902bb"
                                    className="linkedIn">
                                    <i className="fab fa-linkedin"></i>
                                </a></li>
                                <li className="social-icon"><a href="https://github.com/Thibhan" className="github">
                                    <i className="fab fa-github-square"></i>
                                </a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    );
}

export default AboutUs;
