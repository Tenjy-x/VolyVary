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

import com.volyVary.modele.DetailLotTransforme;
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
    public ModelAndView afficheDetailLotTransforme(@RequestParam("idLotTransforme") int idLotTransforme) {
        ModelAndView m = new ModelAndView("DetailsLotPaddy_tranforme");
        LotPaddyTransforme lotTransforme = transformationService.getLotPaddyTransformeById(idLotTransforme);

        if (lotTransforme == null) {
            m.addObject("error", "Aucun detail trouve pour cette transformation");
            return m;
        }

        List<DetailLotTransforme> details = transformationService.getDetailsLotTransforme(idLotTransforme);
        m.addObject("details", details);
        m.addObject("saisie", lotTransforme.getQuantite());
        m.addObject("LotTouche", transformationService.getLotPaddyTouche(idLotTransforme));
        m.addObject("lotpaddyTransforme", lotTransforme);
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
