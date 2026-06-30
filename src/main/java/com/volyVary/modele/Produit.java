package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private int idProduit;

    public Produit() {
    }

    public int getidProduit() {
        return idProduit;
    }

    public void setidProduit(int idProduit) {
        this.idProduit = idProduit;
    }
}
