package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "transformation")
public class Transformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transformation")
    private Integer idTransformation;

    public Transformation() {
    }

    public int getidTransformation() {
        return idTransformation;
    }

    public void setidTransformation(int idTransformation) {
        this.idTransformation = idTransformation;
    }
}
