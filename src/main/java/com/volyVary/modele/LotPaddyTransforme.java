package com.volyVary.modele;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "lot_paddy_transforme")
public class LotPaddyTransforme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "reference",unique = true)
    private String reference;

    @Column(name = "quantite")
    private double quantite;

    @Column(name = "date")
    private Date date;

    @OneToMany(mappedBy = "Lot_transforme", cascade = CascadeType.ALL)
    private List<DetailLotTransforme> detailLotTransforme;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<DetailLotTransforme> getDetailLotTransforme() {
        return detailLotTransforme;
    }

    public void setDetailLotTransforme(List<DetailLotTransforme> detailLotTransforme) {
        this.detailLotTransforme = detailLotTransforme;
    }

}
