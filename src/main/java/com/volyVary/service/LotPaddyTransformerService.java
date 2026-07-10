package com.volyVary.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.volyVary.dto.LotStockDto;
import com.volyVary.modele.LotPaddy;
import com.volyVary.modele.LotPaddyMouvement;
import com.volyVary.modele.LotPaddyTransforme;
import com.volyVary.modele.TransformationModel;
import com.volyVary.repository.LotPaddyMouvementRepository;
import com.volyVary.repository.LotPaddyRepository;
import com.volyVary.repository.LotPaddyTransformeRepository;
import com.volyVary.repository.TransformationRepository;

@Service
public class LotPaddyTransformerService {
    @Autowired
    private TransformationRepository transformationRepository;

    @Autowired
    private LotPaddyMouvementRepository lotPaddyMouvementRepository;

    @Autowired
    private LotPaddyTransformeRepository lotPaddyTransformeRepository;

    @Autowired
    private LotPaddyRepository lotPaddyRepository;

    @Autowired
    private DetailLotPaddyTransformeService detailLotPaddyTransformeService;

    public List<LotStockDto> getlisteStockPaddy() {
        return lotPaddyMouvementRepository.getStockReelParLot();
    }

    @Transactional
    public void transformation(Double quantiteSaisie, LocalDateTime date) throws IllegalArgumentException {
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
            if (suivieQuantite <= 0)
                break;

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
        detailLotPaddyTransformeService.insertDetailLotPaddy(t);
    }

    public Page<LotPaddyTransforme> filtrePaddyTransforme(
            LocalDateTime debut,
            LocalDateTime fin,
            Pageable pageable) {

        if (debut != null && fin != null) {
            return lotPaddyTransformeRepository
                    .findByDateBetween(debut, fin, pageable);
        }

        if (debut != null) {
            return lotPaddyTransformeRepository
                    .findByDateGreaterThanEqual(debut, pageable);
        }

        if (fin != null) {
            return lotPaddyTransformeRepository
                    .findByDateLessThanEqual(fin, pageable);
        }

        return lotPaddyTransformeRepository.findAll(pageable);
    }

    public int getLastLotTransformeId() {
        LotPaddyTransforme lastLotTransforme = lotPaddyTransformeRepository.findTopByOrderByIdDesc();
        if (lastLotTransforme != null) {
            return lastLotTransforme.getId();
        } else {
            return -1;
        }
    }

    public LotPaddyTransforme getLotPaddyTransformeById(int id) {
        return lotPaddyTransformeRepository.findById(id);
    }

    public List<LotPaddyMouvement> getLotPaddyTouche(int idLotTransforme) {
        return lotPaddyMouvementRepository.findByLotPaddyTransformeId(idLotTransforme);
    }

    public Page<LotPaddyTransforme> getAll(Pageable pageable) {
        return lotPaddyTransformeRepository.findAll(pageable);
    }

    public List<LotPaddyTransforme> listeHistorique() {
        return lotPaddyTransformeRepository.findAll();
    }

    public Double totalPaddyTransformer() {
        return lotPaddyTransformeRepository.quantiteTotalTransformer();
    }

    public void save(LotPaddyTransforme lotPaddyTransforme) {
        lotPaddyTransformeRepository.save(lotPaddyTransforme);
    }

    public Double totalPaddyTransformer(List<LotPaddyTransforme> liste) {
        double total = 0.0;
        for (LotPaddyTransforme lotPaddyTransforme : liste) {
            total += lotPaddyTransforme.getQuantite();
        }
        return total;
    }

}
