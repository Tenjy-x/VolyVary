package com.volyVary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.volyVary.modele.TransformationModel;
import com.volyVary.repository.TransformationRepository;
@Service
public class TransformationService {
    @Autowired
    private TransformationRepository transformationRepository;

    public TransformationModel getTransformation() {
        return transformationRepository.findTopByOrderByIdTransformationDesc();
    }


}
