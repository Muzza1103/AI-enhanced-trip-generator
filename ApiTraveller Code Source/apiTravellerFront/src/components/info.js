import React from "react";
import AuthService from "../services/auth.service";
import NavBar from "./NavBar";

const Profil = () => {
    const currentUser = AuthService.getCurrentUser();
    return (
        <>
            <NavBar />
            <div className="container">
                <header className="jumbotron">
                    <h3>
                        <strong>{currentUser.username}</strong> Informations
                    </h3>
                </header>
                <p>
                    <strong>Email:</strong> {currentUser.email}
                </p>
                <p>
                    <strong>Username:</strong> {currentUser.username}
                </p>
            </div>
        </>
    );
};

export default Profil;
