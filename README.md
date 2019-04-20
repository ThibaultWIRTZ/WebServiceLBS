# Projet le bon sandwich

## Présentation

Dans le cadre de ce projet, nous devions créer un web service contenant les deux services suivants :
- Catalogue : Permet la gestion des sandwichs et des catégories
- Commande : Permet la gestion des commandes que passent les clients

## Initialisation du projet

Ayant utilisé "Eclipse IDE for Java EE Developers", je vous fournirais une description de l'initialisation sur cet IDE.

Afin de pouvoir faire fonctionner les deux services en même temps, j'ai utilisé un serveur Tomcat. 

Les étapes pour l'initialiser sont les suivantes :
- Importer les deux services en tant que "Existing Maven Projects".
- Régler les propriétés des deux projets pour qu'ils se lancent à l'aide de jdk (Properties -> Java Build Path->JRE System Library->Edit...->Alternate JRE->jdk | si jdk non présent l'ajouter : Installed JREs->Add).
- Supprimer le serveur déjà présent dans l'onglet nommé "Servers".
- Ajouter un nouveau serveur (cliquer sur finish directement).
- Faire un "Maven install" pour les deux services.
- Référencer les bons chemins d'accès absolu vers le dossier "/target/CommandeLeBonSandwich" du projet CommandeLebonsandwich pour l'URL "/commande" et "/target/Catalogue" du projet Lebonsandwich pour l'URL "/catalogue" dans l'onglet "Modules" du serveur Tomcat.

## Utilisation

Il vous suffit alors de démarrer le serveur afin de pouvoir accéder aux différentes fonctionnalités des services de commande (<a href="http://localhost:8080/commande/" target="_blank">http://localhost:8080/commande/</a>) et catalogue (<a href="http://localhost:8080/catalogue/" target="_blank">http://localhost:8080/catalogue/</a>).

Afin de voir les différentes fonctionnalitées, j'ai ajouté Swagger. Vous pourrez y accéder <a href="http://localhost:8080/commande/swagger-ui.html" target="_blank">ici</a> pour le service commande et <a href="http://localhost:8080/catalogue/swagger-ui.html" target="_blank">là</a> pour le service catalogue.

Vous pouvez aussi accéder aux bases de données respective des deux webs services <a href="http://localhost:8080/commande/h2-console" target="_blank">ici</a> pour le service commande et <a href="http://localhost:8080/catalogue/h2-console" target="_blank">là</a> pour le service catalogue.