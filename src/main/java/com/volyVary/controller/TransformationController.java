package com.volyVary.controller;

import java.sql.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/formulaire")
    public ModelAndView afficheFormulaire() {
        ModelAndView m = new ModelAndView("formulaireAjout");
        return m;
    }

    @PostMapping("/traitementAjout")
    public ModelAndView test(@RequestParam("quantite") double quantite,@RequestParam("date") Date date,Model model){
        transformationService.transformation(quantite, date);
        
         ModelAndView m = new ModelAndView("formulaireAjout");
        m.addObject("listeStock", transformationService.listeStockPaddy());
        return m;
    }
}
