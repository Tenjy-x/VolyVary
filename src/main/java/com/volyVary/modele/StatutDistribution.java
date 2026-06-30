package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "statut_distribution")
public class StatutDistribution {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}