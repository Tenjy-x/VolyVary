package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "distribution")
public class Distribution {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}