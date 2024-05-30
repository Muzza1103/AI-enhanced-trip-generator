import React from 'react';
import palestine from '../assets/support/palestine.png';
import tamil from '../assets/support/eelam.png'
import ukraine from '../assets/support/ukraine.png'
import { Link } from "react-router-dom";
import './css/Footer.css';

const Footer = () => {
    return (
        <div className="footer">
            <div className="top">
                <div>
                    <h1 className="api-h1">Api Traveller</h1>
                    <p>Where journeys begin.</p>
                </div>
                <div>
                    <a href="https://www.instagram.com/apitraveller/">
                        <i className="fa-brands fa-instagram-square"></i>
                    </a>
                </div>
            </div>

            <div className="bottom">
                <div>
                    <h4 className="footer-h4">Community</h4>
                    <a href="https://github.com/Muzza1103/ProjetL3G1">GitHub</a>
                </div>

                <div>
                    <h4 className="footer-h4">Help</h4>
                    <Link to="/contactus">Contact us</Link>
                </div>
                <div>
                    <h4 className="footer-h4">Others</h4>
                    <a href="/termsofservice">Terms of Service</a>
                    <a href="/privacypolicy">Privacy Policy</a>
                    <a href="/licences">License</a>
                </div>

            </div>

            <div className="down-bottom">

                <div className="support-palestine">
                    <Link
                        to="https://donate.unrwa.org/-landing-page/en_EN"
                        target="_blank" rel="noopener noreferrer">
                        <img src={palestine} alt="We support Palestine" className="palestine"/>
                    </Link>
                    <span> We support Palestine.</span>
                </div>
                <div className="support-eelam">
                    <Link
                        to="https://www.crowdjustice.com/case/support-tamil-people/"
                        target="_blank" rel="noopener noreferrer">
                        <img src={tamil} alt="We support Tamil Eelam" className="eelam"/>
                    </Link>
                    <span> We support Tamil Eelam.</span>
                </div>
                <div className="support-ukraine">
                    <Link
                        to="https://riseofukraine.com/?gad_source=1&gclid=Cj0KCQjwltKxBhDMARIsAG8KnqUHexMwMfLWgfkBXeHh82l0QG_0Gc_6DaX7XldWCRhwTVCjLv0umS0aAow6EALw_wcB"
                        target="_blank" rel="noopener noreferrer">
                        <img src={ukraine} alt="We support Ukraine." className="ukraine"/>
                    </Link>
                    <span> We support Ukraine.</span>
                </div>
            </div>

        </div>
    );
}

export default Footer;
