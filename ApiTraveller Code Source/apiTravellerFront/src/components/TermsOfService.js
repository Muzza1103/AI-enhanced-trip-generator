import React from 'react';
import NavBar from "./NavBar";

const TermsOfService = () => {
    return (
        <div>
            <NavBar/>
            <h2>Terms of Service</h2>
            <p>This terms of service ("terms") will help you understand how DX (domain expert) uses the services provided to you, and how we protect your information.</p>
            <p>When we refer to "Personal Information" in these terms, we are talking about any information that can be used to identify or contact you, such as your name, address, email, and phone number.</p>
            <p>Please note that these terms do not apply to any user information collected by third-party services.</p>
            <h3>Use of Services</h3>
            <p>By using DX's services, you agree to abide by these terms. These terms apply to all users of the services provided by DX.</p>
            <h3>Collection of Personal Information</h3>
            <p>DX collects the following types of personal information from you:</p>
            <ul>
                <li>Information voluntarily provided by you when registering for an account on our website, subscribing to newsletters, or engaging in other similar activities.</li>
                <li>Information collected automatically when you access our website, including IP addresses, browser types, and other standard web log information.</li>
            </ul>
            <h3>Use of Personal Information</h3>
            <p>DX uses the personal information you provide for the following purposes:</p>
            <ul>
                <li>To enhance the security of your account and prevent fraudulent activities.</li>
                <li>To enable communication between you and DX, including communication with respect to services, events, or newsletters.</li>
                <li>To improve the user experience and content of our website, and provide you with relevant content and offers.</li>
            </ul>
            <h3>Security of Personal Information</h3>
            <p>DX is committed to securing your personal information and preventing its unauthorized access, use, or disclosure. DX employs advanced security measures to help ensure the safety of your personal information.</p>
            <h3>Third-Party Sites</h3>
            <p>These terms do not apply to other websites that may be accessed through this website. DX has no control over the terms of service or privacy practices of such websites.</p>
            <h3>Changes to these Terms</h3>
            <p>DX may revise these terms at any time by updating this posting. If we make any changes to the terms, we will provide notice by posting the updated terms on this page.</p>
        </div>
    );
};

export default TermsOfService;
