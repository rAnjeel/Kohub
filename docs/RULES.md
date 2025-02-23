# Règles de Gestion - Projet Coworking

## 1. Utilisateurs (Users)

- Chaque utilisateur doit avoir un nom d'utilisateur unique
- Le numéro de client est unique et obligatoire
- Les rôles possibles sont ROLE_USER et ROLE_ADMIN
- Le mot de passe est obligatoire
- Un utilisateur peut avoir plusieurs réservations

## 2. Espaces

- Chaque espace a un nom unique
- Le prix est obligatoire et doit être positif
- Le nom de l'espace est obligatoire
- Un espace peut être réservé plusieurs fois

## 3. Options

- Chaque option a un code unique (insensible à la casse)
- Le nom et le prix sont obligatoires
- Le prix doit être positif
- Une option peut être associée à plusieurs réservations

## 4. Réservations

- Chaque réservation a une référence unique
- Une réservation doit être associée à :
  - Un utilisateur existant
  - Un espace existant
  - Une date de réservation
  - Une heure de début
  - Une durée
- La durée est exprimée en heures
- Une réservation peut avoir zéro ou plusieurs options
- Une réservation peut avoir zéro ou plusieurs paiements

## 5. Détails des Réservations

- Une option ne peut être ajoutée qu'une seule fois à une réservation
- La suppression d'une réservation entraîne la suppression de ses détails
- La clé primaire est composée de l'ID de la réservation et de l'ID de l'option

## 6. Paiements

- Chaque paiement a une référence unique
- Un paiement doit être associé à une réservation existante
- La date de paiement est obligatoire
- Une réservation peut avoir plusieurs paiements

## 7. Import de Données

### Format des fichiers CSV

- Les fichiers doivent être encodés en UTF-8
- La première ligne est considérée comme un en-tête
- Les champs sont séparés par des virgules

### Formats spécifiques

1. Options :

   ```
   code,nom,prix
   OPT1,Imprimante,50000
   ```

2. Espaces :

   ```
   nom,prix
   rubis,65000
   ```

3. Réservations :

   ```
   ref,espace,client,date,heure_debut,duree,option
   r001,rubis,0349049881,11/01/2025,09:00,1,"opt1- opt3"
   ```

   - Les options multiples sont séparées par "- "
   - Les options peuvent être entre guillemets

4. Paiements :
   ```
   ref_paiement,ref,date
   190029,r001,11/01/2025
   ```

### Règles d'import

- Les données existantes sont mises à jour (pas de doublons)
- Les erreurs sont gérées individuellement (une erreur n'arrête pas l'import)
- Les relations sont vérifiées (références existantes)
- Les formats de date sont vérifiés (dd/MM/yyyy)
- Les formats d'heure sont vérifiés (HH:mm)

## 8. Contraintes Techniques

- Les identifiants sont auto-générés
- Les références externes sont vérifiées (contraintes de clé étrangère)
- Les champs uniques sont indexés
- Les dates sont stockées au format ISO
