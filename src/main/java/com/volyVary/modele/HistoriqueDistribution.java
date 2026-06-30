package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "historique_distribution")
public class HistoriqueDistribution {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}