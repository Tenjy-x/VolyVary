package com.volyVary.modele;


import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;

import com.volyVary.modele.Produit;

@Entity
@Table(name = "detail_lot_transforme")
public class DetailLotTransforme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_produit")
    private Produit Produit;

    @ManyToOne
    @JoinColumn(name = "id_lot_transforme")
    private LotPaddyTransforme lotTransforme;

    @Column(name = "quantite")
    private double quantite;

    @Column(name = "date")
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produit getProduit() {
        return Produit;
    }

    public void setProduit(Produit produit) {
        Produit = produit;
    }

    public LotPaddyTransforme getLotTransforme() {
        return lotTransforme;
    }

    public void setLotTransforme(LotPaddyTransforme lotTransforme) {
        this.lotTransforme = lotTransforme;
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
    
  
}
