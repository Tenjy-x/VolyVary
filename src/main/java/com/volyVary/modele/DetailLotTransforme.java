package com.volyVary.modele;


import jakarta.persistence.*;
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
    private LotPaddyTransforme Lot_transforme;

    @Column(name = "quantite")
    private Float quantite;

    @Column(name = "date")
    private LocalDate date;

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

    public LotPaddyTransforme getLot_transforme() {
        return Lot_transforme;
    }

    public void setLot_transforme(LotPaddyTransforme lot_transforme) {
        Lot_transforme = lot_transforme;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
  
}
