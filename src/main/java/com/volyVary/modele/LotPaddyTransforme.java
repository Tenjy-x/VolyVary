package com.volyVary.modele;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "lot_paddy_transforme")
public class LotPaddyTransforme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lot_paddy_transforme")
    private int idLotPaddyTransforme;

    @Column(name = "reference")
    private String reference;

    @Column(name = "quantite")
    private double quantite;

    @Column(name = "date")
    private Date date;

    @OneToMany(mappedBy = "DetailLotPaddyTransforme")
    private List<DetailLotTransforme> detailLotTransforme;

    public int getidLotPaddyTransforme() {
        return idLotPaddyTransforme;
    }

    public void setidLotPaddyTransforme(int idLotPaddyTransforme) {
        this.idLotPaddyTransforme = idLotPaddyTransforme;
    }
}
