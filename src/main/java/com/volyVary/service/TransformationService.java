package com.volyVary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.volyVary.repository.DetailLotTransformeRepository;
import com.volyVary.repository.LotPaddyRepository;
import com.volyVary.repository.LotPaddyTransformeRepository;
import com.volyVary.repository.ProduitRepository;
import com.volyVary.repository.LotPaddyMouvementRepository;

@Service
public class TransformationService {
    @Autowired
    private DetailLotTransformeRepository detailLotTransformeRepository;

    @Autowired
    private LotPaddyRepository lotPaddyRepository;

    @Autowired
    private LotPaddyTransformeRepository lotPaddyTransformeRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private LotPaddyMouvementRepository lotPaddyMouvementRepository;

    public TransformationService(DetailLotTransformeRepository detailLotTransformeRepository,
            LotPaddyRepository lotPaddyRepository, LotPaddyTransformeRepository lotPaddyTransformeRepository,
            ProduitRepository produitRepository, LotPaddyMouvementRepository lotPaddyMouvementRepository) {
        this.detailLotTransformeRepository = detailLotTransformeRepository;
        this.lotPaddyRepository = lotPaddyRepository;
        this.lotPaddyTransformeRepository = lotPaddyTransformeRepository;
        this.produitRepository = produitRepository;
        this.lotPaddyMouvementRepository = lotPaddyMouvementRepository;
    }

    public Double getTotalQuantiteLotPaddy() {
        return lotPaddyRepository.sommeQuantite();
    }

    public Double getTotalQuantiteLotPaddyMouvement() {
        return lotPaddyMouvementRepository.sommeQuantiteLotPaddyMouvement() != null ? lotPaddyMouvementRepository.sommeQuantiteLotPaddyMouvement() : 0.0;
    }
}
