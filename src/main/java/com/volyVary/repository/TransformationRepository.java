package com.volyVary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.volyVary.modele.Transformation;

@Repository
public interface TransformationRepository extends JpaRepository<Transformation,Integer>{    
    @Query("SELECT prixUnitaire FROM Transformation LIMIT 1")
    Double getTransformationPrix();
}
