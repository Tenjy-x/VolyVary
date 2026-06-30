package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "detail_distribution")
public class DetailDistribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detail_distribution")
    private int idDetailDistribution;

    public DetailDistribution() {
    }

    public int getidDetailDistribution() {
        return idDetailDistribution;
    }

    public void setidDetailDistribution(int idDetailDistribution) {
        this.idDetailDistribution = idDetailDistribution;
    }
}
