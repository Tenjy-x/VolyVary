package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "transformation")
public class Transformation {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}