TP 5 : Requêtes pour "salles disponibles par créneau", recherche multi-critères, pagination d'un listing
1. Objectif

L’objectif de ce TP est de développer une application de gestion des réservations de salles en utilisant JPA et Hibernate.
Le projet permet de gérer les salles, les utilisateurs, les équipements et les réservations avec des fonctionnalités avancées comme la recherche multi-critères, la recherche des salles disponibles et la pagination.

2. Technologies utilisées

Dans ce projet nous avons utilisé :

Java

Maven

JPA (Java Persistence API)

Hibernate

Hibernate Validator

Base de données H2

JUnit pour les tests

3. Structure du projet

Le projet est organisé en plusieurs packages :

Package model

Contient les entités :

Utilisateur : informations des utilisateurs

Salle : informations des salles

Reservation : gestion des réservations

Equipement : équipements disponibles dans les salles

Package repository

Contient :

SalleRepository

SalleRepositoryImpl

Il permet l’accès aux données et l’exécution des requêtes JPQL et Criteria.

Package service

Contient :

SalleService

SalleServiceImpl

Il gère la logique métier et les transactions.

Package util

Contient :

PaginationResult

Cette classe facilite la gestion de la pagination.

4. Fonctionnalités implémentées
1. Recherche des salles disponibles

Une requête JPQL permet de trouver les salles libres pour un créneau donné en vérifiant les réservations existantes.

2. Recherche multi-critères

L’utilisateur peut rechercher des salles selon plusieurs critères :

nom

capacité minimum

capacité maximum

bâtiment

étage

équipement

Cette fonctionnalité utilise Criteria API.

3. Pagination

La pagination permet d’afficher les salles par pages pour éviter de charger toutes les données en même temps.

Elle utilise :

setFirstResult()

setMaxResults()
<img width="1920" height="935" alt="Capture d&#39;écran 2026-02-24 202105" src="https://github.com/user-attachments/assets/4362035a-e99d-4f0e-9c6b-eb3ee148b7b4" />
les resultats
Test 1 : Recherche des salles disponibles

Le système affiche les salles disponibles pour un créneau donné.

Test 2 : Recherche multi-critères

Recherche des salles selon :

capacité

bâtiment

étage

Test 3 : Pagination

Affichage des salles par pages avec le nombre total de pages et d’éléments.
**<img width="1803" height="908" alt="Capture d&#39;écran 2026-02-24 202128" src="https://github.com/user-attachments/assets/0bebc7d7-e809-4f1f-8207-1f92bfbdb262" />
**<img width="1920" height="998" alt="Capture d&#39;écran 2026-02-24 202139" src="https://github.com/user-attachments/assets/f82b694c-b7e9-4e09-9b91-54e01df71c69" />
<img width="1920" height="960" alt="Capture d&#39;écran 2026-02-24 202148" src="https://github.com/user-attachments/assets/d4f02275-ebd9-4ffe-af3a-992151ca88f9" />
<img width="1920" height="962" alt="Capture d&#39;écran 2026-02-24 202243" src="https://github.com/user-attachments/assets/6bedf959-607c-4060-9eb3-ba9a359938b0" />
<img width="1920" height="894" alt="Capture d&#39;écran 2026-02-24 202256" src="https://github.com/user-attachments/assets/91473653-269a-4b73-b837-5befd3df7b03" />
<img width="1920" height="974" alt="Capture d&#39;écran 2026-02-24 202306" src="https://github.com/user-attachments/assets/5da6275f-be7d-4fb6-86c2-e527322e926f" />
<img width="1920" height="962" alt="Capture d&#39;écran 2026-02-24 202315" src="https://github.com/user-attachments/assets/b8e1b001-24f8-4339-93b1-78c139354232" />
