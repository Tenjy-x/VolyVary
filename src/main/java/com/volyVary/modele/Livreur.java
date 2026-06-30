package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "livreur")
public class Livreur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livreur")
    private int idLivreur;

    public Livreur() {
    }

    public int getidLivreur() {
        return idLivreur;
    }

    public void setidLivreur(int idLivreur) {
        this.idLivreur = idLivreur;
    }
}
