package com.volyVary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.volyVary.modele.TransformationModel;

@Repository
public interface TransformationRepository extends JpaRepository<TransformationModel,Integer>{    
    TransformationModel findTopByOrderByIdTransformationDesc();
}
