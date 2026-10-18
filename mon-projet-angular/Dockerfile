# Étape 1 : build Angular

#permet à Docker de préparer un environnement contenant Node.js 20 
#pour npm installer les dépendances et compiler le code Angular en fichiers(html,js,css...) que le navigateur pourra utiliser après le déploiement.
FROM node:20 AS build

#créer le dossier de travail /app dans le conteneur Docker, où les commandes suivantes seront exécutées et les fichiers du projet copiés
WORKDIR /app 

#permet de copier les fichiers package.json et package-lock.json depuis ton projet vers le dossier de travail /app pour installer les dépendances car ces deux fichier cotient les bibliothéques et les dépendances de ce projet 
COPY package*.json ./

#permet d'installer dans le conteneur Docker les dépendances déclarées dans package.json, en respectant les versions précises enregistrées dans package-lock.json
RUN npm ci

#permet de copier tous les fichiers et dossiers du projet Angular depuis ta machine vers le répertoire de travail /app dans le conteneur Docker, à l'exception des fichiers exclus par .dockerignore
COPY . .

#permet de compiler le code Angular en fichiers HTML, JavaScript et CSS que le navigateur pourra utiliser après le déploiement de l'application.
RUN npm run build

# Étape 2 : serveur nginx
#permet à Docker d'utiliser une image légère alpine contenant Nginx ces un serveur web qui servira les fichiers HTML, JavaScript et CSS de l'application Angular aux utilisateurs après le déploiement.
#Nginx est un serveur web.Il sert à envoyer les fichiers de ton application Angular au navigateur pour que l'utilisateur puisse voir le site.
FROM nginxinc/nginx-unprivileged:alpine

#copie le fichier nginx.conf de ton projet vers l'emplacement où Nginx lit sa configuration dans le conteneur Docker
#Le fichier nginx.conf contient les règles qui expliquent à Nginx comment afficher ton application Angular.
COPY nginx.conf /etc/nginx/conf.d/default.conf
#Cette commande copie les fichiers Angular compilés(html,css,js...) vers Nginx pour que les utilisateurs puissent accéder au site.
COPY --from=build /app/dist/mon-projet-angular/browser /usr/share/nginx/html
#Cette ligne indique que ton application utilise le port 80 pour communiquer avec les visiteurs.
#le port utilisé par Nginx à l'intérieur du conteneur Docker
EXPOSE 8080
#On n'ajoute pas forcément CMD ou ENTRYPOINT dans ton Dockerfile Angular parce que l'image Nginx utilisée avec FROM nginx:alpine possède déjà une commande pour démarrer le serveur web.