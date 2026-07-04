<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import ="java.util.List" %>
<%@ page import ="com.volyVary.dto.*" %>
<% List<LotStockDto> listeDto = (List<LotStockDto>) request.getAttribute("listeStock"); %>

<% String error = (String) request.getAttribute("error"); %>
<% String success = (String) request.getAttribute("success");%>

<% if(success != null) {%>
    <p style="color:green"><%= success%></p>
<% } %>
<% if(error != null) {%>
    <p style="color:red"><%= error%></p>
<% } %>
    <h1>Formulaire ajout</h1>
    <form action="/transformation/traitementAjout" method="post">
        <input type="datetime-local" name="date" step="60" required>
        <input type="number" name="quantite" required>
        <input type="submit" value="Valider">
    </form>

    <% if(listeDto != null && !listeDto.isEmpty()) {%>
        <% for(LotStockDto l : listeDto) {%>
            <p><%= l.getIdLot()%></p>
            <p><%= l.getQuantiteReel()%></p>
            <br>
        <% }%>
    <% }%>



