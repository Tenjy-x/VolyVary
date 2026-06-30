package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "categorie_fourniture")
public class CategorieFourniture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie_fourniture")
    private int idCategorieFourniture;

    public CategorieFourniture() {
    }

    public int getidCategorieFourniture() {
        return idCategorieFourniture;
    }

    public void setidCategorieFourniture(int idCategorieFourniture) {
        this.idCategorieFourniture = idCategorieFourniture;
    }
}
