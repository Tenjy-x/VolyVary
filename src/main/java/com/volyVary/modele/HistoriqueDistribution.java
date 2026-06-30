package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "historique_distribution")
public class HistoriqueDistribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historique_distribution")
    private int idHistoriqueDistribution;

    public HistoriqueDistribution() {
    }

    public int getidHistoriqueDistribution() {
        return idHistoriqueDistribution;
    }

    public void setidHistoriqueDistribution(int idHistoriqueDistribution) {
        this.idHistoriqueDistribution = idHistoriqueDistribution;
    }
}
