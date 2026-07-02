package com.volyVary.service;

import java.sql.Date;

import org.apache.tomcat.websocket.Transformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.volyVary.modele.LotPaddyTransforme;
import com.volyVary.repository.DetailLotTransformeRepository;
import com.volyVary.repository.LotPaddyRepository;
import com.volyVary.repository.LotPaddyTransformeRepository;
import com.volyVary.repository.ProduitRepository;
import com.volyVary.repository.TransformationRepository;

import jakarta.transaction.Transactional;

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
    private TransformationRepository transformationRepository;

    public TransformationService(DetailLotTransformeRepository detailLotTransformeRepository,
            LotPaddyRepository lotPaddyRepository, LotPaddyTransformeRepository lotPaddyTransformeRepository,
            ProduitRepository produitRepository,TransformationRepository transformationRepository) {
        this.detailLotTransformeRepository = detailLotTransformeRepository;
        this.lotPaddyRepository = lotPaddyRepository;
        this.lotPaddyTransformeRepository = lotPaddyTransformeRepository;
        this.produitRepository = produitRepository;
        this.transformationRepository = transformationRepository;
    }

    // @Transactional
    // public void transformation(double quantiteSaisie , Date date){
    //     double prixTransformation = transformationRepository.getTransformationPrix();
    //     String reference = lotPaddyTransformeRepository.getNextReference();

    //     LotPaddyTransforme lotPaddyTransforme = new LotPaddyTransforme();
    //     lotPaddyTransforme.setDate(date);
    //     lotPaddyTransforme.setQuantite(quantiteSaisie);
    //     lotPaddyTransforme.setReference(String.format("LTP%4", reference));
    //     lotPaddyTransforme.setPrixTransformation(prixTransformation * quantiteSaisie);

    //     //Save de la transformation
    //     lotPaddyTransformeRepository.save(lotPaddyTransforme);

    //     //total stock paddy
    

    // }
    
}
