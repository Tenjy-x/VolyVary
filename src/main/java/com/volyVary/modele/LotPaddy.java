package com.volyVary.modele;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "lot_paddy")
public class LotPaddy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String reference;
    private Float quantite;
    private Float taux_humidite;
    private LocalDate date;
    private Float prix_collecte;
    public Integer getid() {
        return id;
    }


    public void setid(Integer id) {
        this.id = id;
    }



    public String getReference() {
        return reference;
    }


    public void setReference(String reference) {
        this.reference = reference;
    }


    public Float getQuantite() {
        return quantite;
    }


    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }


    public Float getTaux_humidite() {
        return taux_humidite;
    }


    public void setTaux_humidite(Float taux_humidite) {
        this.taux_humidite = taux_humidite;
    }


    public LocalDate getDate() {
        return date;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }


    public Float getPrix_collecte() {
        return prix_collecte;
    }


    public void setPrix_collecte(Float prix_collecte) {
        this.prix_collecte = prix_collecte;
    }


    public LotPaddy() {
    }


}
