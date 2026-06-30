package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "lot_paddy_transforme")
public class LotPaddyTransforme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lot_paddy_transforme")
    private int idLotPaddyTransforme;

    public LotPaddyTransforme() {
    }

    public int getidLotPaddyTransforme() {
        return idLotPaddyTransforme;
    }

    public void setidLotPaddyTransforme(int idLotPaddyTransforme) {
        this.idLotPaddyTransforme = idLotPaddyTransforme;
    }
}
