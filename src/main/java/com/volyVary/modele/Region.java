package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "region")
public class Region {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}