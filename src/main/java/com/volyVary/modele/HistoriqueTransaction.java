package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "historique_transaction")
public class HistoriqueTransaction {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}