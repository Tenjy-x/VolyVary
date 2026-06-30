package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "statut_lot_paddy")
public class StatutLotPaddy {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}