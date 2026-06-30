package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "lieu")
public class Lieu {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}