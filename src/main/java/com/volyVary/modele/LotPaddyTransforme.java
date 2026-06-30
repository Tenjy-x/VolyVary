package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "lot_paddy_transforme")
public class LotPaddyTransforme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}