<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.volyVary.modele.DetailLotTransforme" %>
<%@ page import="com.volyVary.modele.LotPaddyTransforme" %>
<%@ page import="com.volyVary.modele.LotPaddyMouvement" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détail lot de Paddy transformé</title>
</head>
<body>

<% 
List<DetailLotTransforme> details = (List<DetailLotTransforme>) request.getAttribute("details");
LotPaddyTransforme lotpaddyTransforme = (LotPaddyTransforme) request.getAttribute("lotpaddyTransforme");
%>
<h2>Transformation : Détail lot de Paddy transformé</h2>

<hr>

<table cellpadding="8">

    <tr>
        <td><b>Date :</b></td>
        <td><%= details.get(0).getDate() %></td>
    </tr>

    <tr>
        <td><b>Référence :</b></td>
        <td><%= details.get(0).getLotTransforme().getReference() %></td>
    </tr>

</table>

<br><br>

<b>Quantité de Paddy :</b> <%= request.getAttribute("saisie") %> kg

<br><br>

<b>Obtenues :</b>

<br><br>

<table border="1" cellpadding="10" cellspacing="0" width="350">
    <%
        for (DetailLotTransforme detail : details) {
    %>
    <tr>
        <td><%= detail.getProduit().getNomProduit() %></td>
        <td align="center"><%= detail.getQuantite() %></td>
    </tr>

    <%
        }
    %>

</table>

<br><br>

<b>Prix/kg pour la transformation :</b> <%= lotpaddyTransforme.getPrixTransformation() %> Ar

<br><br>

<b>Montant total :</b> <%= lotpaddyTransforme.getPrixTransformation() * (double) request.getAttribute("saisie") %> Ar

<br><br><br>

<b>Lots de Paddy :</b>

<br><br>

<table border="1" cellpadding="10" cellspacing="0" width="350">

    <tr>
        <th>Référence</th>
        <th>Quantité</th>
    </tr>
    <%
        List<LotPaddyMouvement> lotTouche = (List<LotPaddyMouvement>) request.getAttribute("LotTouche");
        for (LotPaddyMouvement lot : lotTouche) {
    %>
    <tr>
        <td><%= lot.getLotPaddy().getReference() %></td>
        <td align="center"><%= lot.getQuantite() %></td>
    </tr>

<% } %>

</table>

<br><br>

<form action="/transformation/formulaire">
    <input type="submit" value="Retour">
</form>

</body>
</html>