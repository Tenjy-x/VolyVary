package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "detail_transaction")
public class DetailTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detail_transaction")
    private int idDetailTransaction;

    public DetailTransaction() {
    }

    public int getidDetailTransaction() {
        return idDetailTransaction;
    }

    public void setidDetailTransaction(int idDetailTransaction) {
        this.idDetailTransaction = idDetailTransaction;
    }
}
