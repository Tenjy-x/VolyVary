package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    private int idRegion;

    public Region() {
    }

    public int getidRegion() {
        return idRegion;
    }

    public void setidRegion(int idRegion) {
        this.idRegion = idRegion;
    }
}
