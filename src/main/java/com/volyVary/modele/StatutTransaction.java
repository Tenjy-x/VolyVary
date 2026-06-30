package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "statut_transaction")
public class StatutTransaction {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}