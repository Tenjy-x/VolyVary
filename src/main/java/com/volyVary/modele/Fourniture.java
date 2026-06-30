package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "fourniture")
public class Fourniture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fourniture")
    private int idFourniture;

    public Fourniture() {
    }

    public int getidFourniture() {
        return idFourniture;
    }

    public void setidFourniture(int idFourniture) {
        this.idFourniture = idFourniture;
    }
}
