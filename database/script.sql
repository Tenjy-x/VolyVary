CREATE DATABASE voly_vary;
\c voly_vary;
-- ============================================================================
-- 1. TABLES INDÉPENDANTES (Aucune clé étrangère)
-- ============================================================================

CREATE TABLE client (
    id SERIAL PRIMARY KEY,
    reference VARCHAR(50),
    nom VARCHAR(100),
    prenom VARCHAR(100),
    telephone VARCHAR(20),
    date DATE
);

CREATE TABLE collecte (
    id SERIAL PRIMARY KEY,
    prix_unitaire NUMERIC(10, 2)
);

CREATE TABLE statut_lot_paddy (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100),
    sigle VARCHAR(10)
);

CREATE TABLE reduction (
    id SERIAL PRIMARY KEY,
    humidite1 NUMERIC(5, 2),
    humidite2 NUMERIC(5, 2),
    reduction NUMERIC(5, 2)
);

CREATE TABLE transformation (
    id SERIAL PRIMARY KEY,
    prix_unitaire NUMERIC(10, 2)
);

CREATE TABLE produit (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    rendement NUMERIC(5, 2),
    prix_unitaire NUMERIC(10, 2)
);

-- ============================================================================
-- 2. TABLES AVEC CLÉS ÉTRANGÈRES EXPLICITES
-- ============================================================================

CREATE TABLE lot_paddy (
    id SERIAL PRIMARY KEY,
    reference VARCHAR(50),
    quantite NUMERIC(12, 3),
    taux_humidite NUMERIC(5, 2),
    date TIMESTAMP,
    prix_collecte NUMERIC(10, 2),
    id_collecte INT,

    CONSTRAINT fk_lot_paddy_collecte 
        FOREIGN KEY (id_collecte) 
        REFERENCES collecte(id) 
        ON DELETE SET NULL
);

CREATE TABLE historique_collecte (
    id SERIAL PRIMARY KEY,
    id_client INT,
    id_lot_paddy INT,
    id_statut INT,
    date TIMESTAMP,

    CONSTRAINT fk_historique_client 
        FOREIGN KEY (id_client) 
        REFERENCES client(id) 
        ON DELETE CASCADE,
        
    CONSTRAINT fk_historique_lot_paddy 
        FOREIGN KEY (id_lot_paddy) 
        REFERENCES lot_paddy(id) 
        ON DELETE CASCADE,
        
    CONSTRAINT fk_historique_statut 
        FOREIGN KEY (id_statut) 
        REFERENCES statut_lot_paddy(id) 
        ON DELETE RESTRICT
);

CREATE TABLE lot_paddy_transforme (
    id SERIAL PRIMARY KEY,
    reference VARCHAR(50),
    quantite NUMERIC(12, 3),
    date TIMESTAMP,
    prix_transformation NUMERIC(10, 2),
    id_transformation INT,

    CONSTRAINT fk_lot_transforme_transformation 
        FOREIGN KEY (id_transformation) 
        REFERENCES transformation(id) 
        ON DELETE SET NULL
);

CREATE TABLE detail_lot_transforme (
    id SERIAL PRIMARY KEY,
    id_lot_transforme INT,
    id_produit INT,
    quantite NUMERIC(12, 3),
    date TIMESTAMP,

    CONSTRAINT fk_detail_lot_transforme 
        FOREIGN KEY (id_lot_transforme) 
        REFERENCES lot_paddy_transforme(id) 
        ON DELETE CASCADE,
        
    CONSTRAINT fk_detail_produit 
        FOREIGN KEY (id_produit) 
        REFERENCES produit(id) 
        ON DELETE RESTRICT
);

CREATE TABLE lot_paddy_mouvement(
    id SERIAL PRIMARY KEY,
    id_lot_paddy INT,
    id_lot_transforme INT,
    quantite NUMERIC(12, 3),
    date TIMESTAMP,
    
    FOREIGN KEY (id_lot_paddy) REFERENCES lot_paddy(id),
    FOREIGN KEY (id_lot_transforme) REFERENCES lot_paddy_transforme(id)
);

CREATE SEQUENCE lot_paddy_reference_seq
START WITH 1
INCREMENT BY 1;