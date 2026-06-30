package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "commune")
public class Commune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commune")
    private int idCommune;

    public Commune() {
    }

    public int getidCommune() {
        return idCommune;
    }

    public void setidCommune(int idCommune) {
        this.idCommune = idCommune;
    }
}
