package com.volyVary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.volyVary.modele.DetailLotTransforme;
import com.volyVary.modele.LotPaddyTransforme;
import com.volyVary.modele.Produit;
import com.volyVary.repository.DetailLotTransformeRepository;
import com.volyVary.repository.ProduitRepository;

@Service
public class DetailLotPaddyTransformeService {
    @Autowired
    private DetailLotTransformeRepository detailLotTransformeRepository;

    @Autowired
    private ProduitRepository produitRepository;

    public List<DetailLotTransforme> getDetailsLotTransforme(int idLotTransforme) {
        return detailLotTransformeRepository.findByLotTransformeId(idLotTransforme);
    }

    public void insertDetailLotPaddy(LotPaddyTransforme lotPaddyTransforme) {
        List<Produit> listeProduit = produitRepository.findAll();
        for (Produit produit : listeProduit) {
            DetailLotTransforme d = new DetailLotTransforme();
            d.setProduit(produit);
            d.setLotTransforme(lotPaddyTransforme);

            double quantite = (produit.getRendement() * lotPaddyTransforme.getQuantite()) / 100;
            d.setQuantite(quantite);
            d.setDate(lotPaddyTransforme.getDate());

            detailLotTransformeRepository.save(d);
        }
    }

}
