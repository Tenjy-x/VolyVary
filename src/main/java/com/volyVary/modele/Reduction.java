package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "reduction")
public class Reduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reduction")
    private int idReduction;

    public Reduction() {
    }

    public int getidReduction() {
        return idReduction;
    }

    public void setidReduction(int idReduction) {
        this.idReduction = idReduction;
    }
}
