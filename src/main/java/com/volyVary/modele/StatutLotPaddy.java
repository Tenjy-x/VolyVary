package com.volyVary.modele;

import jakarta.persistence.*;

@Entity
@Table(name = "statut_lot_paddy")
public class StatutLotPaddy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut_lot_paddy")
    private int idStatutLotPaddy;

    public StatutLotPaddy() {
    }

    public int getidStatutLotPaddy() {
        return idStatutLotPaddy;
    }

    public void setidStatutLotPaddy(int idStatutLotPaddy) {
        this.idStatutLotPaddy = idStatutLotPaddy;
    }
}
