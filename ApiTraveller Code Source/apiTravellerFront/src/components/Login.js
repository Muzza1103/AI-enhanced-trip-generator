import React, { useState } from 'react';
import './css/Login.css';
import user_icon from '../assets/person.png';
import email_icon from '../assets/email.png';
import password_icon from '../assets/password.png';
import NavBar from "./NavBar";
import AuthService from '../services/auth.service';
import {useNavigate} from "react-router-dom";

const Login = () => {
    const [action, setAction] = useState("Login");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false); // New state for success message
    const isEmailValid = (email) => {
        // Expression régulière pour vérifier si l'e-mail est valide
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    };
    let navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        if (!username || !password) {
            setError("Username and password are required.");
            return; // Stop further execution if fields are empty
        }
        try {
            const response = await AuthService.login(username, password);
            console.log(response);
            // Redirect to protected route or update state to show logged in user
            navigate("/survey");
        } catch (error) {
            if (error.response) {
                setError(error.response.data.error);
            } else {
                setError("An unexpected error occurred.");
            }
        }
    };

    const handleRegister = async (e) => {
        e.preventDefault();
        if (!username || !email || !password) {
            setError("Username, email, and password are required.");
            return; // Stop further execution if fields are empty
        }
        if (password.length < 6) {
            setError("Password must contain 6 characters.");
            return;
        }
        if (!isEmailValid(email)) {
            setError("Your email is not valid.");
            return;
        }
        try {
            const response = await AuthService.register(username, email, password);
            console.log(response.data); // Affichez la réponse du backend dans la console
            setSuccess(true); // Set success to true upon successful registration
            setError(""); // Réinitialisez l'erreur à une chaîne vide
            navigate("/login");
        } catch (error) {
            if (error.response) {
                setError(error.response.data.message); // Récupérez le message d'erreur de la réponse du backend
            } else {
                setError("An unexpected error occurred.");
            }
        }
    };


    return (
        <>
            <NavBar />
            <div className="container">
                <div className="header">
                    <div className="text">{action}</div>
                    <div className="underline"></div>
                </div>
                <div className="inputs">
                    {action === "Login"? (
                        <div>
                            <div className="input">
                                <img src={user_icon} alt="" />
                                <input
                                    type="text"
                                    placeholder="Username"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                />
                            </div>
                            <div className="input">
                                <img src={password_icon} alt="" />
                                <input
                                    type="password"
                                    placeholder="Password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                            </div>
                        </div>
                    ) : (
                        <div>
                            <div className="input">
                                <img src={email_icon} alt="" />
                                <input
                                    type="email"
                                    placeholder="Email"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                            </div>
                            <div className="input">
                                <img src={user_icon} alt="" />
                                <input
                                    type="text"
                                    placeholder="Username"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                />
                            </div>
                            <div className="input">
                                <img src={password_icon} alt="" />
                                <input
                                    type="password"
                                    placeholder="Password"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />

                            </div>
                            {success && <div style={{ color: "green" }}>Registration successful!</div>} {/* Success message */}
                        </div>

                )}
                </div>
                <div className="submit-container">
                    {action === "Login"? (
                        <div>
                            <div className="submit" onClick={handleLogin}>
                                Login
                            </div>
                            <div
                                className="submit gray"
                                onClick={() => setAction("Sign Up")}
                            >
                                Sign Up
                            </div>
                        </div>
                    ) : (
                        <div>
                            <div className="submit" onClick={handleRegister}>
                                Sign Up
                            </div>
                            <div
                                className="submit gray"
                                onClick={() => setAction("Login")}
                            >
                                Login
                            </div>
                        </div>
                    )}
                </div>
                {error && <div style={{ color: "red" }}>{error}</div>}
            </div>
        </>
    );
};

export default Login;
