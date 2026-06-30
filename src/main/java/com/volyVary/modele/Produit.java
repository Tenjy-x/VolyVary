package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "produit")
public class Produit {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}