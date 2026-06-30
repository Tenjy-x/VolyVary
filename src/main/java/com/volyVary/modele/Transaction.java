package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private int idTransaction;

    public Transaction() {
    }

    public int getidTransaction() {
        return idTransaction;
    }

    public void setidTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }
}
