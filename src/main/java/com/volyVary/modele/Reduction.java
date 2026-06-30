package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "reduction")
public class Reduction {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}