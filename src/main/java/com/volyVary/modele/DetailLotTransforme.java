package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "detail_lot_transforme")
public class DetailLotTransforme {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}