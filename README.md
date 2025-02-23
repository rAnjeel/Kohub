# Kohub - Application de Coworking

Kohub est une application dédiée à la gestion d'espaces de coworking. Elle permet de gérer facilement les réservations, d'associer des options spécifiques à chaque réservation et d'assurer une authentification sécurisée des utilisateurs.

## Fonctionnalités

- **Gestion des Réservations** : Créer, mettre à jour et annuler des réservations pour les espaces de coworking.
- **Options Personnalisées** : Associer des options (services additionnels, équipements, etc.) à chaque réservation.
- **Authentification Sécurisée** : Utilisation de Spring Security pour gérer les accès et protéger l'application.
- **Gestion des Espaces** : Recherche et gestion des espaces de coworking disponibles.
- **API RESTful** : Exposition d'une API pour faciliter l'intégration et la communication avec d'autres systèmes.

## Technologies Utilisées

- **Java 17** : Langage de programmation principal.
- **Spring Boot 3.4.3** : Cadre de développement pour créer des applications robustes.
- **Spring Security** : Gestion de l'authentification et de l'autorisation.
- **Spring Data JPA** : Simplification de l'accès aux données et gestion des entités.
- **PostgreSQL** : Base de données relationnelle utilisée pour stocker les informations.
- **Maven** : Gestionnaire de dépendances et outil d'automatisation du projet.
- **Lombok** : Réduction du code boilerplate dans les entités.
- **Spring Boot DevTools** : Outil facilitant le développement en mode hot-reloading.

## Installation

1. **Cloner le Repository**  
   Clonez le projet depuis GitHub :
   ```bash
   git clone https://github.com/votre-utilisateur/kohub.git
   cd kohub
   ```

2. **Configurer la Base de Données**  
   Dans le fichier `src/main/resources/application.properties`, configurez la connexion à PostgreSQL :
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/coworking
   spring.datasource.username=postgres
   spring.datasource.password=mot_de_passe
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Construire le Projet**  
   Utilisez Maven pour compiler et installer les dépendances :
   ```bash
   mvn clean install
   ```

4. **Lancer l'Application**  
   Démarrez l'application avec Maven :
   ```bash
   mvn spring-boot:run
   ```

## Usage

- **API REST** : Interagissez avec l'application via l'API REST (utilisez Postman ou tout autre client HTTP pour tester les endpoints).
- **Interface Utilisateur** : Si une interface front-end est intégrée, vous pouvez accéder aux fonctionnalités directement depuis votre navigateur.

## Contribuer

Les contributions sont les bienvenues ! N'hésitez pas à forker le projet, créer des branches de fonctionnalités et soumettre vos pull requests pour améliorer Kohub.

## License

Ce projet est sous licence MIT. Consultez le fichier [LICENSE](LICENSE) pour plus d'informations.


