package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "lot_paddy")
public class LotPaddy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lot_paddy")
    private int idLotPaddy;

    public LotPaddy() {
    }

    public int getidLotPaddy() {
        return idLotPaddy;
    }

    public void setidLotPaddy(int idLotPaddy) {
        this.idLotPaddy = idLotPaddy;
    }
}
