import React, {useState} from 'react'
import './css/ContactUs.css'
import NavBar from "./NavBar";
import mail_icon from '../assets/email.png'
const ContactUs = () =>{
    return(
        <>
            <NavBar/>
            <h1 className="titre-h1">Contact Us</h1>
            <div className="contact">
                <div className="contact-col">
                    <h3>Send us a message</h3>
                    <p>Feel free to reah out throught contact form. Your feedback, questions, and suggestions are
                        important to us as we strive to provide exceptional service to our users.</p>
                    <ul>
                        <li><img src={mail_icon} alt=""/>ApiTraveller@gmail.com</li>
                    </ul>
                </div>
                <div className="contact-col">
                    <form>
                        <label>Your name</label>
                        <input type="text" name="name" placeholder="Enter your name" required/>
                        <label>Mail</label>
                        <input type="text" name="mail" placeholder="Enter your mail" required/>
                        <label>Write your messages here</label>
                        <textarea name="message" rows="6" placeholder="Enter your message" required></textarea>
                        <button type="submit" className="btn-submit">Submit</button>
                    </form>
                    <span></span>
                </div>
            </div>
        </>
    );
};

export default ContactUs