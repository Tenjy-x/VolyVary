package com.volyVary.controller;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.volyVary.service.TransformationService;

import jakarta.transaction.Transactional;
import com.volyVary.modele.DetailLotTransforme;
import com.volyVary.modele.LotPaddyTransforme;

import java.util.List;

@Controller
@RequestMapping("/transformation")
public class TransformationController {
    @Autowired
    private TransformationService transformationService;

    public TransformationController(TransformationService transformationService) {
        this.transformationService = transformationService;
    }

    @GetMapping("/detailsLotPaddyTransforme")
    public ModelAndView afficheDetailLotTransforme() {
        ModelAndView m = new ModelAndView("DetailsLotPaddy_tranforme");
        return m;
    }

    @GetMapping("/lotPaddyTransforme")
    public ModelAndView afficheLotTransforme() {
        ModelAndView m = new ModelAndView("LotPaddy_tranforme");
        return m;
    }

    @GetMapping("/formulaire")
    public ModelAndView afficheFormulaire() {
        ModelAndView m = new ModelAndView("formulaireAjout");
        return m;
    }

    @PostMapping("/traitementAjout")
    public ModelAndView test(@RequestParam("quantite") double quantite,
                            @RequestParam("date") Date date,
                            RedirectAttributes redirectAttributes) {
        ModelAndView m = new ModelAndView("DetailsLotPaddy_tranforme");
        ModelAndView retour = new ModelAndView("formulaireAjout");
        try{
            transformationService.transformation(quantite, date);
            redirectAttributes.addFlashAttribute("success", "Transformation ajoute");

        } catch(IllegalArgumentException e){
            retour.addObject("listeStock", transformationService.getlisteStockPaddy());
            retour.addObject("error", e.getMessage());
            return retour;
        }
        LotPaddyTransforme lastLotTransforme = transformationService.getLotPaddyTransformeById(transformationService.getLastLotTransformeId());
        List<DetailLotTransforme> details = transformationService.getDetailsLotTransforme(transformationService.getLastLotTransformeId());
        m.addObject("listeStock", transformationService.getlisteStockPaddy());
        m.addObject("saisie", quantite);
        m.addObject("details", details);
        m.addObject("LotTouche", transformationService.getLotPaddyTouche(lastLotTransforme.getId()));
        m.addObject("lotpaddyTransforme" , lastLotTransforme);
        return m;
    }
}
