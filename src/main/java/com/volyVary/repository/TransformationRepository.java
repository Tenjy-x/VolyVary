package com.volyVary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.volyVary.modele.Transformation;

import jakarta.persistence.Entity;

@Entity
public interface TransformationRepository extends JpaRepository<Transformation,Integer>{    
    @Query("SELECT prix_unitaire FROM transformation LIMIT 1")
    double getTransformationPrix();
}
