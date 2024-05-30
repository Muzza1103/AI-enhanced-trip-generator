import React from 'react';
import NavBar from "./NavBar";

const Licenses = () => {
    return (
        <div>
            <NavBar/>
            <h2>Licenses</h2>
            <p>Here are the licenses for the software and content used by DX:</p>
            <h3>Software Licenses</h3>
            <ul>
                <li><strong>React:</strong> Licensed under the MIT License. You can find the license <a href="https://opensource.org/licenses/MIT" target="_blank" rel="noopener noreferrer">here</a>.</li>
                {/* Add more software licenses as needed */}
            </ul>
            <h3>Content Licenses</h3>
            <ul>
                <li><strong>Images:</strong> Licensed under Creative Commons CC0.</li>
                <li><strong>Text Content:</strong> All rights reserved by DX.</li>
                {/* Add more content licenses as needed */}
            </ul>
            <p>Please review the individual licenses for more information.</p>
        </div>
    );
};

export default Licenses;
