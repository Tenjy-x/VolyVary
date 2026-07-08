package com.volyVary.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.volyVary.modele.DetailLotTransforme;
import com.volyVary.modele.LotPaddyTransforme;
import com.volyVary.modele.TransformationModel;
import com.volyVary.service.TransformationService;

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

    // @GetMapping("/lotPaddyTransforme")
    // public ModelAndView afficheLotTransforme() {
    // ModelAndView m = new ModelAndView("LotPaddy_tranforme");
    // return m;
    // }

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

    @GetMapping("/lotPaddyTransforme")
    public ModelAndView afficheFormulaireHistorique(
            @RequestParam(defaultValue = "0") int page) {
        ModelAndView m = new ModelAndView("LotPaddy_transforme");
        Pageable pageable = PageRequest.of(page, 9);

        Page<LotPaddyTransforme> lots = transformationService.getAll(pageable);

        m.addObject("transformation", transformationService.getTransformation());
        m.addObject("lotPaddyTransforme", lots);

        Double total = transformationService.totalPaddyTransformer();
        if (total == null) {
            m.addObject("lotPaddyVide", "aucun paddy transformer");
        } else {
            m.addObject("total", transformationService.totalPaddyTransformer());
        }

        return m;
    }

    @GetMapping("/traitementFiltre")
    public ModelAndView traitementFiltre(
            @RequestParam(required = false) String debut,
            @RequestParam(required = false) String fin,
            @RequestParam(defaultValue = "0") int page) {

        ModelAndView m = new ModelAndView("LotPaddy_transforme");
        int safePage = Math.max(page, 0);
        Pageable pageable = PageRequest.of(safePage, 10);

        LocalDateTime debutFiltre = parseDateTimeOrNull(debut);
        LocalDateTime finFiltre = parseDateTimeOrNull(fin);

        if ((debut != null && !debut.isBlank() && debutFiltre == null)
                || (fin != null && !fin.isBlank() && finFiltre == null)) {
            m.addObject("error", "Format de date invalide pour le filtre");
        }

        Page<LotPaddyTransforme> lots = transformationService.filtrePaddyTransforme(
                debutFiltre,
                finFiltre,
                pageable);

        m.addObject("lotPaddyTransforme", lots);
        m.addObject("debut", debut != null ? debut : "");
        m.addObject("fin", fin != null ? fin : "");

        m.addObject("transformation",
                transformationService.getTransformation());

        Double total = transformationService.totalPaddyTransformer();

        if (total == null) {

            m.addObject("lotPaddyVide",
                    "aucun paddy transformer");

        } else {

            m.addObject("total", total);

        }

        return m;
    }

    private LocalDateTime parseDateTimeOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/pdfListe")
    public void exportPdfListe(
            @RequestParam(required = false) String debut,
            @RequestParam(required = false) String fin,
            HttpServletResponse response)
            throws IOException, DocumentException {
        LocalDateTime debutFiltre = parseDateTimeOrNull(debut);
        LocalDateTime finFiltre = parseDateTimeOrNull(fin);

        Page<LotPaddyTransforme> lotsPage = transformationService.filtrePaddyTransforme(
                debutFiltre,
                finFiltre,
                Pageable.unpaged());
        List<LotPaddyTransforme> lots = lotsPage.getContent();

        TransformationModel transformation = transformationService.getTransformation();
        double prixUnitaire = transformation != null ? transformation.getPrixUnitaire() : 0.0;
        double totalQuantite = transformationService.totalPaddyTransformer(lots);
        double totalMontant = lots.stream().mapToDouble(LotPaddyTransforme::getPrixTransformation).sum();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=liste-transformations.pdf");

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titre = new Font(Font.HELVETICA, 16, Font.BOLD, Color.BLUE);
        Font normal = new Font(Font.HELVETICA, 10);
        Font gras = new Font(Font.HELVETICA, 10, Font.BOLD);

        Paragraph pTitre = new Paragraph("LISTE DES TRANSFORMATIONS DE PADDY", titre);
        pTitre.setAlignment(Element.ALIGN_CENTER);
        document.add(pTitre);
        document.add(new Paragraph(" "));

        String periode = "Toutes les dates";
        if (debutFiltre != null && finFiltre != null) {
            periode = "Du " + debutFiltre + " au " + finFiltre;
        } else if (debutFiltre != null) {
            periode = "A partir de " + debutFiltre;
        } else if (finFiltre != null) {
            periode = "Jusqu'au " + finFiltre;
        }
        document.add(new Paragraph("Periode: " + periode, normal));
        document.add(new Paragraph("Nombre de lots: " + lots.size(), normal));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new int[] { 20, 28, 14, 18, 20 });

        PdfPCell h1 = new PdfPCell(new Phrase("Reference", gras));
        PdfPCell h2 = new PdfPCell(new Phrase("Date", gras));
        PdfPCell h3 = new PdfPCell(new Phrase("Quantite (kg)", gras));
        PdfPCell h4 = new PdfPCell(new Phrase("Prix unitaire (Ar)", gras));
        PdfPCell h5 = new PdfPCell(new Phrase("Total (Ar)", gras));

        h1.setBackgroundColor(Color.LIGHT_GRAY);
        h2.setBackgroundColor(Color.LIGHT_GRAY);
        h3.setBackgroundColor(Color.LIGHT_GRAY);
        h4.setBackgroundColor(Color.LIGHT_GRAY);
        h5.setBackgroundColor(Color.LIGHT_GRAY);

        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h5.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(h1);
        table.addCell(h2);
        table.addCell(h3);
        table.addCell(h4);
        table.addCell(h5);

        if (lots.isEmpty()) {
            PdfPCell empty = new PdfPCell(new Phrase("Aucune transformation trouvee", normal));
            empty.setColspan(5);
            empty.setHorizontalAlignment(Element.ALIGN_CENTER);
            empty.setPadding(8);
            table.addCell(empty);
        } else {
            for (LotPaddyTransforme lot : lots) {
                PdfPCell cRef = new PdfPCell(new Phrase(lot.getReference(), normal));
                PdfPCell cDate = new PdfPCell(new Phrase(String.valueOf(lot.getDate()), normal));
                PdfPCell cQty = new PdfPCell(new Phrase(String.valueOf(lot.getQuantite()), normal));
                PdfPCell cUnit = new PdfPCell(new Phrase(String.format("%,.2f", prixUnitaire), normal));
                PdfPCell cTotal = new PdfPCell(new Phrase(String.format("%,.2f", lot.getPrixTransformation()), normal));

                cRef.setPadding(5);
                cDate.setPadding(5);
                cQty.setPadding(5);
                cUnit.setPadding(5);
                cTotal.setPadding(5);

                cQty.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cUnit.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);

                table.addCell(cRef);
                table.addCell(cDate);
                table.addCell(cQty);
                table.addCell(cUnit);
                table.addCell(cTotal);
            }
        }

        document.add(table);
        document.add(new Paragraph(" "));

        PdfPTable resume = new PdfPTable(2);
        resume.setWidthPercentage(50);
        resume.setHorizontalAlignment(Element.ALIGN_RIGHT);
        resume.setWidths(new int[] { 55, 45 });

        PdfPCell r1 = new PdfPCell(new Phrase("Total quantite (kg)", gras));
        PdfPCell r2 = new PdfPCell(new Phrase(String.format("%,.2f", totalQuantite), normal));
        PdfPCell r3 = new PdfPCell(new Phrase("Total montant (Ar)", gras));
        PdfPCell r4 = new PdfPCell(new Phrase(String.format("%,.2f", totalMontant), normal));

        r2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        r4.setHorizontalAlignment(Element.ALIGN_RIGHT);

        resume.addCell(r1);
        resume.addCell(r2);
        resume.addCell(r3);
        resume.addCell(r4);

        document.add(resume);
        document.close();
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
