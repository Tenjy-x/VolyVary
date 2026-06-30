package com.volyVary.modele;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "fourniture")
public class Fourniture {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}