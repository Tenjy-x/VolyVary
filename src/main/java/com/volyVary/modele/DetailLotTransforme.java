package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "detail_lot_transforme")
public class DetailLotTransforme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detail_lot_transforme")
    private int idDetailLotTransforme;

    public DetailLotTransforme() {
    }

    public int getidDetailLotTransforme() {
        return idDetailLotTransforme;
    }

    public void setidDetailLotTransforme(int idDetailLotTransforme) {
        this.idDetailLotTransforme = idDetailLotTransforme;
    }
}
