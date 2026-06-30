package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "historique_collecte")
public class HistoriqueCollecte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historique_collecte")
    private int idHistoriqueCollecte;

    public HistoriqueCollecte() {
    }

    public int getidHistoriqueCollecte() {
        return idHistoriqueCollecte;
    }

    public void setidHistoriqueCollecte(int idHistoriqueCollecte) {
        this.idHistoriqueCollecte = idHistoriqueCollecte;
    }
}
