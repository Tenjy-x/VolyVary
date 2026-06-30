package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "type_transaction")
public class TypeTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_transaction")
    private int idTypeTransaction;

    public TypeTransaction() {
    }

    public int getidTypeTransaction() {
        return idTypeTransaction;
    }

    public void setidTypeTransaction(int idTypeTransaction) {
        this.idTypeTransaction = idTypeTransaction;
    }
}
