package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "lot_paddy")
public class LotPaddy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lot_paddy")
    private Integer idLotPaddy;

    public LotPaddy() {
    }

    public Integer getidLotPaddy() {
        return idLotPaddy;
    }

    public void setidLotPaddy(Integer idLotPaddy) {
        this.idLotPaddy = idLotPaddy;
    }
}
