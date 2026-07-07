package com.volyVary.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.Color;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.volyVary.modele.DetailLotTransforme;
import com.volyVary.modele.LotPaddyTransforme;
import com.volyVary.service.TransformationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpServletResponse;

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
        m.addObject("listeStock", transformationService.getlisteStockPaddy());
        return m;
    }

    @PostMapping("/traitementAjout")
    public ModelAndView test(@RequestParam("quantite") double quantite,
            @RequestParam("date") LocalDateTime date,
            RedirectAttributes redirectAttributes) {
        ModelAndView m = new ModelAndView("DetailsLotPaddy_tranforme");
        ModelAndView retour = new ModelAndView("formulaireAjout");
        try {
            transformationService.transformation(quantite, date);
            redirectAttributes.addFlashAttribute("success", "Transformation ajoute");

        } catch (IllegalArgumentException e) {
            retour.addObject("listeStock", transformationService.getlisteStockPaddy());
            retour.addObject("error", e.getMessage());
            return retour;
        }
        LotPaddyTransforme lastLotTransforme = transformationService
                .getLotPaddyTransformeById(transformationService.getLastLotTransformeId());
        List<DetailLotTransforme> details = transformationService
                .getDetailsLotTransforme(transformationService.getLastLotTransformeId());
        m.addObject("listeStock", transformationService.getlisteStockPaddy());
        m.addObject("saisie", quantite);
        m.addObject("details", details);
        m.addObject("LotTouche", transformationService.getLotPaddyTouche(lastLotTransforme.getId()));
        m.addObject("lotpaddyTransforme", lastLotTransforme);
        return m;
    }

    @GetMapping("/formulaireHistorique")
    public ModelAndView afficheFormulaireHistorique() {
        ModelAndView m = new ModelAndView("LotPaddy_transforme");
        m.addObject("transformation", transformationService.getTransformation());
        m.addObject("lotPaddyTransforme", transformationService.listeHistorique());
        Double total = transformationService.totalPaddyTransformer();
        if (total == null) {
            m.addObject("lotPaddyVide", "aucun paddy transformer");
        } else {
            m.addObject("total", transformationService.totalPaddyTransformer());
        }

        return m;
    }

    @PostMapping("/traitementFiltre")
    public ModelAndView traitementFiltre(@RequestParam(value = "debut", required = false) String debut,
            @RequestParam(value = "fin", required = false) String fin) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime dateDebut = (debut == null || debut.isBlank()) ? null : LocalDateTime.parse(debut, formatter);

        LocalDateTime dateFin = (fin == null || fin.isBlank()) ? null : LocalDateTime.parse(fin, formatter);
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

    @GetMapping("/pdf/{id}")
    public void exportPdf(
            @PathVariable int id,
            HttpServletResponse response)
            throws IOException, DocumentException {
        List<DetailLotTransforme> details = transformationService.getDetailsLotTransforme(id);
        LotPaddyTransforme lotpaddyTransforme = transformationService.getLotPaddyTransformeById(id);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=transformation.pdf");

        Document document = new Document();

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        document.open();

        // ===== Les polices =====
        Font titre = new Font(Font.HELVETICA, 18, Font.BOLD, Color.BLUE);
        Font sousTitre = new Font(Font.HELVETICA, 12, Font.BOLD);
        Font normal = new Font(Font.HELVETICA, 11);
        Font gras = new Font(Font.HELVETICA, 11, Font.BOLD);

        // ===== Titre =====
        Paragraph pTitre = new Paragraph("RAPPORT DE TRANSFORMATION", titre);
        pTitre.setAlignment(Element.ALIGN_CENTER);
        document.add(pTitre);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        // ===== Informations =====
        PdfPTable info = new PdfPTable(2);
        info.setWidthPercentage(100);
        info.setWidths(new int[] { 35, 65 });

        info.addCell(new Phrase("Date", gras));
        info.addCell(new Phrase(details.get(0).getDate().toString(), normal));

        info.addCell(new Phrase("Référence", gras));
        info.addCell(new Phrase(details.get(0).getLotTransforme().getReference(), normal));

        info.addCell(new Phrase("Quantité de Paddy", gras));
        info.addCell(new Phrase(lotpaddyTransforme.getQuantite() + " kg", normal));

        document.add(info);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Produits obtenus", sousTitre));
        document.add(new Paragraph(" "));

        // ===== Tableau des produits =====
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80);
        table.setWidths(new int[] { 70, 30 });

        // Entêtes
        PdfPCell cell1 = new PdfPCell(new Phrase("Produit", gras));
        cell1.setBackgroundColor(Color.LIGHT_GRAY);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cell2 = new PdfPCell(new Phrase("Quantité (kg)", gras));
        cell2.setBackgroundColor(Color.LIGHT_GRAY);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell1);
        table.addCell(cell2);

        // Données
        for (DetailLotTransforme d : details) {

            PdfPCell produit = new PdfPCell(new Phrase(d.getProduit().getNomProduit()));
            produit.setPadding(5);

            PdfPCell quantite = new PdfPCell(new Phrase(String.valueOf(d.getQuantite())));
            quantite.setHorizontalAlignment(Element.ALIGN_CENTER);
            quantite.setPadding(5);

            table.addCell(produit);
            table.addCell(quantite);
        }

        document.add(table);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        // ===== Résumé =====
        PdfPTable resume = new PdfPTable(2);
        resume.setWidthPercentage(100);

        resume.addCell(new Phrase("Prix de transformation / kg", gras));
        resume.addCell(new Phrase(lotpaddyTransforme.getPrixTransformation() + " Ar"));

        resume.addCell(new Phrase("Montant total", gras));
        resume.addCell(new Phrase(
                lotpaddyTransforme.getPrixTransformation() * lotpaddyTransforme.getQuantite()
                        + " Ar"));

        document.add(resume);

        document.close();

    }
}
