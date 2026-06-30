package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "collecte")
public class Collecte {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}