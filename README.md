<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ProjetL3G1 - API Traveller</title>
</head>
<body>

<h1>ProjetL3G1 - API Traveller</h1>

<h2>Installation</h2>
<ol>
    <li>Clone the repository to your local machine.</li>
    <li>Ensure that Java and Node.js are installed on your system.</li>
    <li>Download the <code>api-0.0.1-SNAPSHOT.jar</code> file and the <code>apiTravellerFront</code> folder from the repository and place them in the same directory.</li>
    <li>Before launching the program, ensure that ports 3000 and 9000 on your computer are available, as the servers will be hosted on these ports.</li>
    <li>Make sure MySQL is installed on your system.</li>
</ol>

<h2>Running the Program</h2>

<h3>5.1 Running from the Script</h3>
<p>Before running the script, open it and configure the following parameters:</p>
<ul>
    <li>AI API Key (<code>API_AI_KEY</code>)</li>
    <li>Maximum requests per minute allowed by the key (<code>RPM_API_KEY</code>)</li>
    <li>MySQL username (<code>user</code>)</li>
    <li>MySQL password (<code>password</code>)</li>
    <li>MySQL port (default: 3306)</li>
</ul>
<p>Once the parameters are configured, you can run the script:</p>
<ul>
    <li>For Windows: double-click the <code>script_L3G1.bat</code> file.</li>
    <li>For Linux/Unix and MacOS: open the terminal and enter the following command:
        <pre><code>chmod +x script_L3G1.sh
./script_L3G1.sh</code></pre></li>
</ul>
<p>After successfully executing the script, you can make requests from third-party software or integrate the API into your program using the address <a href="http://localhost:9000/">http://localhost:9000/</a>.</p>

<h3>5.2 Manual Launch</h3>
<p>Create the following environment variables on your computer:</p>
<ul>
    <li><code>API_AI_KEY</code>: AI API Key</li>
    <li><code>RPM_API_KEY</code>: Number of requests per minute allowed by the key</li>
</ul>
<p>On Linux/Unix or MacOS, use the following command in the terminal:</p>
<pre><code>export VARIABLE_NAME=VALUE</code></pre>
<p>For Windows, use the following command:</p>
<pre><code>set VARIABLE_NAME=VALUE</code></pre>
<p>Log in to MySQL and create the corresponding database:</p>
<pre><code>mysql -u your_username -p
CREATE DATABASE bdd_apitraveller;</code></pre>
<p>Next, launch the executables for the back-end and front-end in the following order:</p>
<ul>
    <li>For the back-end:
        <pre><code>java -Djdbc.url=jdbc:mysql://localhost:3306/bdd_apitraveller -Djdbc.username=your_username -Djdbc.password=your_password -jar api-0.0.1-SNAPSHOT.jar</code></pre></li>
    <li>For the front-end:
        <pre><code>cd apiTravellerFront/apitravellerfront
npm start</code></pre></li>
</ul>
<p>A browser window should automatically open at <a href="http://localhost:3000/">http://localhost:3000/</a>. If it doesn't, you can access it by typing this address into your browser's search bar.</p>

</body>
</html>
