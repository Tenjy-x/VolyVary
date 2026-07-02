package com.volyVary.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.volyVary.dto.LotStockDto;
import com.volyVary.modele.DetailLotTransforme;
import com.volyVary.modele.LotPaddy;
import com.volyVary.modele.LotPaddyMouvement;
import com.volyVary.modele.LotPaddyTransforme;
import com.volyVary.modele.Produit;
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

    public List<LotStockDto> getlisteStockPaddy(){
        return lotPaddyMouvementRepository.getStockReelParLot();
    }

    public List<Produit> listeProduit(){
        return produitRepository.findAll();
    }

    public void insertDetailLotPaddy(Integer idLotTransformer , Date date , double quantiteSaisie){
        List<Produit> listeProduit =produitRepository.findAll();
        for(Produit produit : listeProduit){
            DetailLotTransforme d = new DetailLotTransforme();
            d.setProduit(produit);

            LotPaddyTransforme lotPaddyTransforme = new LotPaddyTransforme();
            lotPaddyTransforme.setId(idLotTransformer);
            d.setLot_transforme(lotPaddyTransforme);

            double quantite = (produit.getRendement() * quantiteSaisie)/100;
            d.setQuantite(quantite);
            d.setDate(date);
        
            detailLotTransformeRepository.save(d);
        }
    }

    @Transactional
    public void transformation(Double quantiteSaisie, Date date)throws IllegalArgumentException {
        if (quantiteSaisie == null || quantiteSaisie <= 0) {
            throw new IllegalArgumentException("quantiteSaisie invalide");
        }

        TransformationModel transformation = transformationRepository.findTopByOrderByIdTransformationDesc();

        String referenceSeq = lotPaddyTransformeRepository.getNextReference();
        int seq = 0;
        try {
            seq = Integer.parseInt(referenceSeq);
        } catch (NumberFormatException e) {
            seq = 0;
        }

        LotPaddyTransforme lotPaddyTransforme = new LotPaddyTransforme();
        lotPaddyTransforme.setDate(date);
        lotPaddyTransforme.setQuantite(quantiteSaisie);
       
        lotPaddyTransforme.setReference(String.format("LPT%04d", seq));

        double prixUnitaire = 0.0;
        if (transformation != null && transformation.getPrixUnitaire() != 0.0) {
            prixUnitaire = transformation.getPrixUnitaire();
        } else {
            throw new IllegalArgumentException("prix Unitaire dans la base est introuvable");
        }
        lotPaddyTransforme.setPrixTransformation(prixUnitaire * quantiteSaisie);

        LotPaddyTransforme t = lotPaddyTransformeRepository.save(lotPaddyTransforme);

        Double somme = lotPaddyRepository.sommeQuantite();
        Double stockMouv = lotPaddyMouvementRepository.getStockPaddy();
        double total = (somme != null ? somme : 0.0) - (stockMouv != null ? stockMouv : 0.0);

        if (quantiteSaisie > total) {
            throw new IllegalArgumentException("Stock insuffisant pour la transformation demandee");
        }

        List<LotStockDto> listeDto = lotPaddyMouvementRepository.getStockReelParLot();
        double suivieQuantite = quantiteSaisie;

        for (LotStockDto l : listeDto) {
            if (suivieQuantite <= 0) break;

            LotPaddyMouvement m = new LotPaddyMouvement();
            m.setDate(date);
            m.setLotPaddyTransforme(t);

          
            LotPaddy lotPaddy = lotPaddyRepository.findById(l.getIdLot()).orElse(null);
            if (lotPaddy == null) {
                continue;
            }
            m.setLotPaddy(lotPaddy);

            double quantiteReel = l.getQuantiteReel();

            if (quantiteReel <= 0) {
                continue;
            }

            if (quantiteReel < suivieQuantite) {
                m.setQuantite(quantiteReel);
                suivieQuantite -= quantiteReel;
                lotPaddyMouvementRepository.save(m);
            } else {
                m.setQuantite(suivieQuantite);
                lotPaddyMouvementRepository.save(m);
                suivieQuantite = 0;
                break;
            }
        }
        insertDetailLotPaddy(t.getId(), date, quantiteSaisie);
    }

    
}
