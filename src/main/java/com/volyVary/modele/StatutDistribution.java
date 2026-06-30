package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "statut_distribution")
public class StatutDistribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut_distribution")
    private int idStatutDistribution;

    public StatutDistribution() {
    }

    public int getidStatutDistribution() {
        return idStatutDistribution;
    }

    public void setidStatutDistribution(int idStatutDistribution) {
        this.idStatutDistribution = idStatutDistribution;
    }
}
