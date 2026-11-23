# API-Sport-Frontend

Frontend web d'une application de gestion de matchs de sport (R4.01).

L'application permet de :
- consulter un tableau de bord (victoires, nuls, défaites, statistiques joueurs) ;
- gérer les joueurs (ajouter, modifier, supprimer, commenter) ;
- gérer les rencontres (ajouter, modifier, feuille de match, évaluations).

## Lancer le projet

Le projet est en PHP pur, aucun `composer install` n'est nécessaire.

Depuis ce dossier (`TP1/src/main/java/com/example/Frontend`) :

```bash
php -S localhost:8000 index.php
```

Puis ouvrez <http://localhost:8000> dans votre navigateur.

L'API Spring Boot doit tourner en parallèle sur le port 8080, avec la base MySQL `dbr505` démarrée :

```bash
cd TP1
./mvnw spring-boot:run
```

> L'URL du backend est définie dans `constants.php` (`BACKEND_BASE_URL`), par défaut `http://localhost:8080/`.
> Le module cURL n'est pas nécessaire : `functions.php` utilise les flux HTTP natifs de PHP.
> Le routeur `index.php` est indispensable pour que les URL comme `/joueur` fonctionnent.

## Organisation

| Dossier | Rôle |
| --- | --- |
| `Controleur/` | Contrôleurs : appeellent l'API et formatent les données |
| `Modele/` | Objets métier (Joueur, Rencontre, Commentaire, Performance…) |
| `Vue/` | Fichiers PHP contenant le HTML des pages |
| `functions.php` | Helpers d'appel HTTP (cURL) vers l'API |
| `Psr4AutoloaderClass.php` | Autoloader pour le namespace `r401_frontend` |
| `index.php` | Point d'entrée, affiche la Vue correspondant à l'URL |
