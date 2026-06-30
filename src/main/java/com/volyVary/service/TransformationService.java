package com.volyVary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.volyVary.repository.DetailLotTransformeRepository;
import com.volyVary.repository.LotPaddyRepository;
import com.volyVary.repository.LotPaddyTransformeRepository;

@Service
public class TransformationService {
    @Autowired
    private DetailLotTransformeRepository detailLotTransformeRepository;

    @Autowired
    private LotPaddyRepository lotPaddyRepository;

    @Autowired
    private LotPaddyTransformeRepository lotPaddyTransformeRepository;

    public TransformationService(DetailLotTransformeRepository detailLotTransformeRepository,
            LotPaddyRepository lotPaddyRepository, LotPaddyTransformeRepository lotPaddyTransformeRepository) {
        this.detailLotTransformeRepository = detailLotTransformeRepository;
        this.lotPaddyRepository = lotPaddyRepository;
        this.lotPaddyTransformeRepository = lotPaddyTransformeRepository;
    }

   
}
