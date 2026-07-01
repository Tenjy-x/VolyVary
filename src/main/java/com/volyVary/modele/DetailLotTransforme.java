package com.volyVary.modele;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "detail_lot_transforme")
public class DetailLotTransforme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    private Integer idProduit;
    @ManyToOne
    private Integer id_Lot_transforme;
    @ManyToOne
    private Integer id_lot;

    private Float quantite;
    private LocalDate date;
    public Integer getIdProduit() {

        return idProduit;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }

    public Integer getId_Lot_transforme() {
        return id_Lot_transforme;
    }

    public void setId_Lot_transforme(Integer id_Lot_transforme) {
        this.id_Lot_transforme = id_Lot_transforme;
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

    public DetailLotTransforme() {
    }

    public Integer getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }
}
