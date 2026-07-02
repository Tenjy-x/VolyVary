package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "collecte")
public class Collecte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int idCollecte;

    public Collecte() {
    }

    public int getidCollecte() {
        return idCollecte;
    }

    public void setidCollecte(int idCollecte) {
        this.idCollecte = idCollecte;
    }
}
