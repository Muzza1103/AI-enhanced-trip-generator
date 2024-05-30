<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
</head>
<body>

<h1>ProjetL3G1 - API Traveller</h1>

<h2>Installation</h2>
<ol>
    <li>Clonez ce dépôt sur votre machine locale.</li>
    <li>Assurez-vous d'avoir Java et Node.js installés sur votre système.</li>
    <li>Téléchargez le fichier api-0.0.1-SNAPSHOT.jar ainsi que le dossier apiTravellerFront depuis le dépôt et placez-les dans le même répertoire.</li>
    <li>Avant de lancer le programme, assurez-vous que les ports 3000 et 9000 de votre ordinateur sont disponibles, car les serveurs seront hébergés sur ces derniers.</li>
    <li>Assurez-vous également d'avoir MySQL installé sur votre système.</li>
</ol>

<h2>Lancement du Programme</h2>

<h3>5.1 Lancement à partir du Script</h3>
<p>Avant de lancer le script, ouvrez-le et configurez les paramètres suivants :</p>
<ul>
    <li>Clé API d'IA (API_AI_KEY)</li>
    <li>Nombre maximal de requêtes par minute autorisées par la clé (RPM_API_KEY)</li>
    <li>Nom d'utilisateur MySQL (user)</li>
    <li>Mot de passe MySQL (password)</li>
    <li>Port MySQL (par défaut : 3306)</li>
</ul>
<p>Une fois les paramètres configurés, vous pouvez lancer le script :</p>
<ul>
    <li>Pour Windows : double-cliquez sur le fichier <code>script_L3G1.bat</code></li>
    <li>Pour Linux/Unix et MacOS : accédez au terminal et entrez la commande suivante :
        <pre><code>chmod +x script_L3G1.sh
./script_L3G1.sh</code></pre></li>
</ul>
<p>Une fois le script exécuté avec succès, vous pouvez effectuer des requêtes à partir de logiciels tiers ou intégrer l'API dans votre programme en utilisant l'adresse <a href="http://localhost:9000/">http://localhost:9000/</a>.</p>

<h3>5.2 Lancement Manuel</h3>
<p>Créez les variables d'environnement suivantes sur votre ordinateur :</p>
<ul>
    <li>API_AI_KEY : Clé API d'IA</li>
    <li>RPM_API_KEY : Nombre de requêtes par minute permises par la clé</li>
</ul>
<p>Sous Linux/Unix ou MacOS, utilisez la commande suivante dans le terminal :</p>
<pre><code>export MA_VARIABLE=VALEUR</code></pre>
<p>Pour Windows, utilisez la commande suivante :</p>
<pre><code>set MA_VARIABLE=VALEUR</code></pre>
<p>Connectez-vous à MySQL et créez la base de données correspondante :</p>
<pre><code>mysql -u votre_nom_utilisateur -p
CREATE DATABASE bdd_apitraveller;</code></pre>
<p>Ensuite, lancez les exécutables correspondant au back-end et au front-end dans l'ordre suivant :</p>
<ul>
    <li>Pour le back-end :
        <pre><code>java -Djdbc.url=jdbc:mysql://localhost:3306/bdd_apitraveller -Djdbc.username=votre_nom_utilisateur -Djdbc.password=votre_mot_de_passe -jar api-0.0.1-SNAPSHOT.jar</code></pre></li>
    <li>Pour le front-end :
        <pre><code>cd apiTravellerFront/apitravellerfront
npm start</code></pre></li>
</ul>
<p>Une fenêtre de navigateur devrait s'ouvrir automatiquement à l'adresse <a href="http://localhost:3000/">http://localhost:3000/</a>. Si ce n'est pas le cas, vous pouvez y accéder en tapant cette adresse dans la barre de recherche de votre navigateur.</p>

</body>
</html>
