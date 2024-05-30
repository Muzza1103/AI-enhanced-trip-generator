import React, { useState } from "react";
import './css/NavBar.css';
import logo from './../assets/logo.png';
import { Link, useNavigate } from "react-router-dom";
import AuthService from "../services/auth.service";
import poweroff from '../assets/logout.png';

const NavBar = () => {
    const [clicked, setClicked] = useState(false);
    const navigate = useNavigate();

    const handleClick = () => {
        navigate('/survey');

    };

    const handleLogout = () => {
        AuthService.logout();
        window.location.reload();
        navigate('/survey');
        window.location.reload();
    };

    const currentPath = window.location.pathname;

    return (
        <nav className="NavBar">
            <Link to="/">
                <img src={logo} alt="" className="logo" />
            </Link>

            <div>
                <ul id="navbar" className={clicked ? "#navbar active" : "#navbar"}>
                    <li><Link to="/survey" className={currentPath === "/survey" ? "active" : ""} onClick={() => handleClick()}>Home</Link></li>
                    <li><Link to="/aboutus" className={currentPath === "/aboutus" ? "active" : ""}>About us</Link></li>
                    <li><Link to="/contactus" className={currentPath === "/contactus" ? "active" : ""}>Contact</Link>
                    </li>
                    {AuthService.getCurrentUser() ? (
                        <>
                            <li><Link to="/history"
                                      className={currentPath === "/history" ? "active" : ""}>History</Link></li>
                            <li><Link to="/triphistory" className={currentPath === "/triphistory" ? "active" : ""}>Trip
                                History</Link></li>
                            <li><Link to="/info" className={currentPath === "/info" ? "active" : ""}>Account</Link></li>
                            <li>
                                <button onClick={handleLogout}
                                        className={`nav-button ${currentPath === "/survey" ? "active" : ""}`}>
                                    <img src={poweroff} alt="Logout" style={{width: '35px', height: '35px', background : "transparent"}}/>
                                </button>

                            </li>
                        </>
                    ) : (
                        <li><Link to="/login" className={currentPath === "/login" ? "active" : ""}>Login</Link></li>
                    )}
                </ul>
            </div>

            <div id="mobile" onClick={handleClick}>
                <i id="bar" className={clicked ? 'fas fa-times icon-green' : 'fas fa-bars icon-green'}> </i>
            </div>
        </nav>
    );
}

export default NavBar;
