<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import ="java.util.List" %>
<%@ page import ="com.volyVary.modele.LotPaddyTransforme" %>
<%@ page import ="com.volyVary.modele.TransformationModel" %>

<% List<LotPaddyTransforme> listePaddy = ( List<LotPaddyTransforme>) request.getAttribute("lotPaddyTransforme");%>
<% TransformationModel t = (TransformationModel) request.getAttribute("transformation");%>
<% double totalPaddyTransformer = (double) request.getAttribute("total");%>
<% String message = (String) request.getAttribute("message");%>
<% String testPaddyTransformer = (String) request.getAttribute("lotPaddyVide");%>

<!DOCTYPE html>
<html>
<head>
    <title>Liste des lots de paddy</title>
</head>
<body>
<h1>Liste des lots de paddy</h1>
<form action="${pageContext.request.contextPath}/transformation/traitementFiltre" method="post">
    <p>date debut :<input type="datetime-local" name="debut" ></p>
    <p>date fin :<input type="datetime-local" name="fin" ></p>
    <input type="submit" value="Filtrer">
</form>
    <table width="80%">
        <tr>
            <td align="center">
                <fieldset style="width:300px;height:100px;">
                    <legend>Statistique</legend>

                    <br><br>
                        Quantité de lot de paddy transformé
                    <br><br>
                    <% if(totalPaddyTransformer != 0) {%>
                        <b><%= totalPaddyTransformer%></b>
                    <% } else { %>
                        <b><%= testPaddyTransformer%></b>
                     <% } %>
                </fieldset>
            </td>

            <td align="center">
                <fieldset style="width:300px;height:100px;">
                    <legend>Statistique</legend>

                    <br><br>
                    Dépense total pour la transformation
                    <br><br>

                    <b><%= t.getPrixUnitaire() * totalPaddyTransformer %> Ar</b> 

                </fieldset>
            </td>
        </tr>
    </table>

    <br><br>

    <table border="1" cellpadding="10" cellspacing="0" width="80%">
        <tr>
            <th>Référence</th>
            <th>Date</th>
            <th>Quantité</th>
            <th>Prix unitaire</th>
            <th>Total</th>
            <th>Action</th>
        </tr>
    <% if (listePaddy == null || listePaddy.isEmpty()) { %>
        <tr>
            <td colspan="6" align="center"><%= message != null ? message : "aucun resultat trouver" %></td>
        </tr>
    <% } else { %>
    <% for(LotPaddyTransforme l : listePaddy) {%>
        <tr>
            <td><%= l.getReference()%></td>
            <td><%= l.getDate()%></td>
            <td><%= l.getQuantite()%></td>
            <td><%= t.getPrixUnitaire()%></td>
            <td><%= l.getPrixTransformation()%></td>
            <td>
                <form action="${pageContext.request.contextPath}/transformation/detailsLotPaddyTransforme" method="get">
                    <input type="hidden" name="idLotTransforme" value="<%= l.getId()%>">
                    <input type="submit" value="Voir détail">
                </form>
            </td>
        </tr>
    <% }%>
    <% } %>
    </table>


</body>
</html>