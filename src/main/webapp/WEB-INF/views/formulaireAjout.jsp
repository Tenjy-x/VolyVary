<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import ="java.util.List" %>
<%@ page import ="com.volyVary.dto.*" %>
<% List<LotStockDto> listeDto = (List<LotStockDto>) request.getAttribute("listeStock"); %>

    <h1>Formulaire ajout</h1>
    <form action="/transformation/traitementAjout" method="post">
        <input type="date" name="date">
        <input type="number" name="quantite">
        <input type="submit" value="Valider">
    </form>

    <% if(listeDto != null && !listeDto.isEmpty()) {%>
        <% for(LotStockDto l : listeDto) {%>
            <p><%= l.getIdLot()%></p>
            <p><%= l.getQuantiteReel()%></p>
            <br>
        <% }%>
    <% }%>



