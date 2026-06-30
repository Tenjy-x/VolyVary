package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "statut_transaction")
public class StatutTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut_transaction")
    private int idStatutTransaction;

    public StatutTransaction() {
    }

    public int getidStatutTransaction() {
        return idStatutTransaction;
    }

    public void setidStatutTransaction(int idStatutTransaction) {
        this.idStatutTransaction = idStatutTransaction;
    }
}
