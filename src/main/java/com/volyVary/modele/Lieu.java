package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "lieu")
public class Lieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lieu")
    private int idLieu;

    public Lieu() {
    }

    public int getidLieu() {
        return idLieu;
    }

    public void setidLieu(int idLieu) {
        this.idLieu = idLieu;
    }
}
