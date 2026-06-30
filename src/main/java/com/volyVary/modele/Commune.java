package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "commune")
public class Commune {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}