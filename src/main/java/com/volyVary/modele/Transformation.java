package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "transformation")
public class Transformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idTransformation;

    @Column(name = "prix_unitaire")
    private double prixUnitaire;

    public Transformation(Integer idTransformation, double prixUnitaire) {
        this.idTransformation = idTransformation;
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getIdTransformation() {
        return idTransformation;
    }

    public void setIdTransformation(Integer idTransformation) {
        this.idTransformation = idTransformation;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
  
}
