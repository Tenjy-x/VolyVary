package com.volyVary.controller;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.volyVary.modele.LotPaddyTransforme;
import com.volyVary.service.TransformationService;

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

    @GetMapping("/formulaireAjoutTransformation")
    public ModelAndView afficheFormulaire() {
        ModelAndView m = new ModelAndView("formulaireAjout");
        return m;
    }

    @PostMapping("/traitementAjout")
    public ModelAndView test(@RequestParam("quantite") double quantite,
                            @RequestParam("date") Date date,
                            RedirectAttributes redirectAttributes) {
        ModelAndView m = new ModelAndView("formulaireAjout");
        try{
            transformationService.transformation(quantite, date);
            redirectAttributes.addFlashAttribute("success", "Transformation ajoute");

        } catch(IllegalArgumentException e){
            m.addObject("error", e.getMessage());
        }
        
        m.addObject("listeStock", transformationService.getlisteStockPaddy());
        return m;
    }

    @GetMapping("/formulaireHistorique")
    public ModelAndView afficheFormulaireHistorique(){
        ModelAndView m = new ModelAndView("LotPaddy_transforme");
        m.addObject("transformation", transformationService.getTransformation());
        m.addObject("lotPaddyTransforme", transformationService.listeHistorique());
        m.addObject("total", transformationService.totalPaddyTransformer());
        return m;
    }

    @PostMapping("/traitementFiltre")
    public ModelAndView traitementFiltre(@RequestParam(value = "debut", required = false) String debut,
                                         @RequestParam(value = "fin", required = false) String fin){
        Date dateDebut = (debut == null || debut.isBlank()) ? null : Date.valueOf(debut);
        Date dateFin = (fin == null || fin.isBlank()) ? null : Date.valueOf(fin);

        ModelAndView m = new ModelAndView("LotPaddy_transforme");
        List<LotPaddyTransforme> resultatFiltre = transformationService.filtrePaddyTransforme(dateDebut, dateFin);
        if (dateDebut == null && dateFin == null) {
            m.addObject("message", "aucun resultat trouver");
        } else if (resultatFiltre.isEmpty()) {
            m.addObject("message", "aucun resultat trouver");
        }
        m.addObject("transformation", transformationService.getTransformation());
        m.addObject("lotPaddyTransforme", resultatFiltre);
        m.addObject("total", transformationService.totalPaddyTransformer(resultatFiltre));
        return m;                                
    }

   
}
