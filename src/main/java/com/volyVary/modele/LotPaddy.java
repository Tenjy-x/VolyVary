package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "lot_paddy")
public class LotPaddy {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}