package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "detail_distribution")
public class DetailDistribution {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}