CREATE DATABASE coworking;
\ c coworking;
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    number VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);
CREATE TABLE options (
    id_option SERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL UNIQUE,
    option_nom VARCHAR(255) NOT NULL,
    prix DECIMAL(10, 2) NOT NULL
);
CREATE TABLE espace (
    id_espace SERIAL PRIMARY KEY,
    nom_espace VARCHAR(255) NOT NULL,
    prix_espace DECIMAL(10, 2) NOT NULL
);
CREATE TABLE reservation (
    id_reservation SERIAL PRIMARY KEY,
    ref VARCHAR(255) NOT NULL UNIQUE,
    id_espace INT NOT NULL,
    id_user INT NOT NULL,
    date_reservation DATE NOT NULL,
    heure_debut TIME NOT NULL,
    duree INT,
    FOREIGN KEY (id_espace) REFERENCES Espace(id_espace),
    FOREIGN KEY (id_user) REFERENCES users(id)
);
CREATE TABLE reservation_detail (
    id_reservation INT NOT NULL,
    id_option INT NOT NULL,
    FOREIGN KEY (id_reservation) REFERENCES Reservation(id_reservation),
    FOREIGN KEY (id_option) REFERENCES Options(id_option)
);
CREATE TABLE paiement (
    id_paiement SERIAL PRIMARY KEY,
    ref_paiement VARCHAR(255) NOT NULL UNIQUE,
    ref_reservation VARCHAR(255) NOT NULL,
    date_paiement DATE NOT NULL,
    FOREIGN KEY (ref_reservation) REFERENCES Reservation(ref)
);