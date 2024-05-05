# ProjetL3G1
API Traveller

Installation
Clonez ce dépôt sur votre machine locale.
Assurez-vous d'avoir Java et Node.js installés sur votre système.
Téléchargez le fichier api-0.0.1-SNAPSHOT.jar ainsi que le dossier apiTravellerFront depuis le dépôt et placez-les dans le même répertoire.
Avant de lancer le programme, assurez-vous que les ports 3000 et 9000 de votre ordinateur sont disponibles, car les serveurs seront hébergés sur ces derniers.
Assurez-vous également d'avoir MySQL installé sur votre système.

Lancement du Programme
5.1 Lancement à partir du Script
Avant de lancer le script, ouvrez-le et configurez les paramètres suivants :
Clé API d'IA (API_AI_KEY)
Nombre maximal de requêtes par minute autorisées par la clé (RPM_API_KEY)
Nom d'utilisateur MySQL (user)
Mot de passe MySQL (password)
Port MySQL (par défaut : 3306)
Une fois les paramètres configurés, vous pouvez lancer le script :
Pour Windows : double-cliquez sur le fichier script_L3G1.bat
Pour Linux/Unix et MacOS : accédez au terminal et entrez la commande suivante :
bash
Copy code
chmod +x script_L3G1.sh
./script_L3G1.sh
Une fois le script exécuté avec succès, vous pouvez effectuer des requêtes à partir de logiciels tiers ou intégrer l'API dans votre programme en utilisant l'adresse http://localhost:9000/.
5.2 Lancement Manuel
Créez les variables d'environnement suivantes sur votre ordinateur :
API_AI_KEY : Clé API d'IA
RPM_API_KEY : Nombre de requêtes par minute permises par la clé
Sous Linux/Unix ou MacOS, utilisez la commande suivante dans le terminal :
arduino
Copy code
export MA_VARIABLE=VALEUR
Pour Windows, utilisez la commande suivante :
arduino
Copy code
set MA_VARIABLE=VALEUR
Connectez-vous à MySQL et créez la base de données correspondante :
css
Copy code
mysql -u votre_nom_utilisateur -p
CREATE DATABASE bdd_apitraveller;
Ensuite, lancez les exécutables correspondant au back-end et au front-end dans l'ordre suivant :
Pour le back-end :
bash
Copy code
java -Djdbc.url=jdbc:mysql://localhost:3306/bdd_apitraveller -Djdbc.username=votre_nom_utilisateur -Djdbc.password=votre_mot_de_passe -jar api-0.0.1-SNAPSHOT.jar
Pour le front-end :
bash
Copy code
cd apiTravellerFront/apitravellerfront
npm start
Une fenêtre de navigateur devrait s'ouvrir automatiquement à l'adresse http://localhost:3000. Si ce n'est pas le cas, vous pouvez y accéder en tapant cette adresse dans la barre de recherche de votre navigateur.