package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "distribution")
public class Distribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_distribution")
    private int idDistribution;

    public Distribution() {
    }

    public int getidDistribution() {
        return idDistribution;
    }

    public void setidDistribution(int idDistribution) {
        this.idDistribution = idDistribution;
    }
}
