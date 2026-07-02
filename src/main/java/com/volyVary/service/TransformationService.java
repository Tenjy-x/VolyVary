package com.volyVary.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.websocket.Transformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.volyVary.dto.LotStockDto;
import com.volyVary.modele.LotPaddy;
import com.volyVary.modele.LotPaddyMouvement;
import com.volyVary.modele.LotPaddyTransforme;
import com.volyVary.modele.TransformationModel;
import com.volyVary.repository.DetailLotTransformeRepository;
import com.volyVary.repository.LotPaddyMouvementRepository;
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

    @Autowired
    private LotPaddyMouvementRepository lotPaddyMouvementRepository;

    public TransformationService(DetailLotTransformeRepository detailLotTransformeRepository,
            LotPaddyRepository lotPaddyRepository, LotPaddyTransformeRepository lotPaddyTransformeRepository,
            ProduitRepository produitRepository, TransformationRepository transformationRepository,
            LotPaddyMouvementRepository lotPaddyMouvementRepository) {
        this.detailLotTransformeRepository = detailLotTransformeRepository;
        this.lotPaddyRepository = lotPaddyRepository;
        this.lotPaddyTransformeRepository = lotPaddyTransformeRepository;
        this.produitRepository = produitRepository;
        this.transformationRepository = transformationRepository;
        this.lotPaddyMouvementRepository = lotPaddyMouvementRepository;
    }

    public void save(LotPaddyTransforme lotPaddyTransforme) {
        lotPaddyTransformeRepository.save(lotPaddyTransforme);
    }

    public List<LotStockDto> listeStockPaddy(){
        return lotPaddyMouvementRepository.getStockReelParLot();
    }

    @Transactional
    public void transformation(Double quantiteSaisie, Date date) {
        TransformationModel transfomration = transformationRepository.findTopByOrderByIdTransformationDesc();
        String reference = lotPaddyTransformeRepository.getNextReference();

        LotPaddyTransforme lotPaddyTransforme = new LotPaddyTransforme();
        lotPaddyTransforme.setDate(date);
        lotPaddyTransforme.setQuantite(quantiteSaisie);
        lotPaddyTransforme.setReference(
                String.format("LTP%04d", Integer.parseInt(reference)));
        lotPaddyTransforme.setPrixTransformation(transfomration.getPrixUnitaire() * quantiteSaisie);

        // Save de la transformation
        // lotPaddyTransformeRepository.save(lotPaddyTransforme);

        // total stock paddy
        LotPaddyTransforme t = lotPaddyTransformeRepository.save(lotPaddyTransforme);
        double total = lotPaddyRepository.sommeQuantite() - lotPaddyMouvementRepository.getStockPaddy();

        if (quantiteSaisie >= total) {
            
        }
        List<LotStockDto> listeDto = lotPaddyMouvementRepository.getStockReelParLot();
        double suivieQuantite = quantiteSaisie;

        
        for (LotStockDto l : listeDto) {
            LotPaddyMouvement m = new LotPaddyMouvement();
            m.setDate(date);
    
            m.setLotPaddyTransforme(t);
    
            LotPaddy lotPaddy = new LotPaddy();
            lotPaddy.setIdLotPaddy(l.getIdLot());

            m.setLotPaddy(lotPaddy);

            if (suivieQuantite != 0) {
                if (l.getQuantiteReel() < suivieQuantite) {
                    suivieQuantite -= l.getQuantiteReel();
                    m.setQuantite(l.getQuantiteReel());
                    // l.setQuantiteReel(suivieQuantite);
                    lotPaddyMouvementRepository.save(m);
                }

                if (l.getQuantiteReel() >= suivieQuantite) {
                    double value = l.getQuantiteReel();
                    value -= suivieQuantite;
                    m.setQuantite(suivieQuantite);
                    lotPaddyMouvementRepository.save(m);
                    // l.setQuantiteReel(value);
                }
            }
        }

      

    }
}
