package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "historique_collecte")
public class HistoriqueCollecte {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}