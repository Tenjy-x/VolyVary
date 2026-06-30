package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "transaction")
public class Transaction {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}