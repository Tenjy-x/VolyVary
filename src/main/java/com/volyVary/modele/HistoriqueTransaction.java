package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "historique_transaction")
public class HistoriqueTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historique_transaction")
    private int idHistoriqueTransaction;

    public HistoriqueTransaction() {
    }

    public int getidHistoriqueTransaction() {
        return idHistoriqueTransaction;
    }

    public void setidHistoriqueTransaction(int idHistoriqueTransaction) {
        this.idHistoriqueTransaction = idHistoriqueTransaction;
    }
}
