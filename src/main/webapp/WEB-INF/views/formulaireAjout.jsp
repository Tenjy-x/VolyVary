<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.volyVary.dto.*" %>
<%
  List<LotStockDto> listeDto = (List<LotStockDto>) request.getAttribute("listeStock");
  String error   = (String) request.getAttribute("error");
  String success = (String) request.getAttribute("success");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Nouvelle transformation - VOLY VARY</title>
  <link rel="stylesheet" href="/assets/css/variables.css">
  <link rel="stylesheet" href="/assets/css/base.css">
  <link rel="stylesheet" href="/assets/css/components.css">
  <link rel="stylesheet" href="/assets/css/style.css">
  <style>
    /* Styles pour améliorer l'affichage des lots */
    .lots-container {
      background: #f8f9fa;
      border-radius: 6px;
      border: 1px solid #dee2e6;
      max-height: 300px;
      overflow-y: auto;
    }
    
    .lots-header {
      display: grid;
      grid-template-columns: 100px 1fr 120px;
      padding: 10px 16px;
      background: #e9ecef;
      font-weight: 600;
      font-size: 13px;
      color: #495057;
      border-bottom: 2px solid #dee2e6;
      position: sticky;
      top: 0;
      z-index: 1;
    }
    
    .lot-item {
      display: grid;
      grid-template-columns: 100px 1fr 120px;
      padding: 10px 16px;
      border-bottom: 1px solid #e9ecef;
      transition: background 0.2s;
      align-items: center;
    }
    
    .lot-item:hover {
      background: #f1f3f5;
    }
    
    .lot-item:last-child {
      border-bottom: none;
    }
    
    .lot-item .lot-id {
      font-weight: 500;
      color: #212529;
      font-size: 14px;
    }
    
    .lot-item .lot-reference {
      color: #495057;
      font-size: 14px;
    }
    
    .lot-item .lot-quantity {
      text-align: right;
      font-weight: 600;
      color: #0d6efd;
      font-size: 14px;
    }
    
    .lot-item .lot-quantity::after {
      content: " kg";
      font-weight: 400;
      color: #6c757d;
      font-size: 12px;
    }
    
    .lots-footer {
      display: flex;
      justify-content: flex-end;
      padding: 10px 16px;
      background: #e9ecef;
      font-weight: 600;
      font-size: 14px;
      color: #495057;
      border-top: 2px solid #dee2e6;
    }
    
    .lots-footer .total {
      color: #0d6efd;
      font-size: 16px;
    }
    
    .lots-footer .total::after {
      content: " kg";
      font-weight: 400;
      color: #6c757d;
      font-size: 13px;
    }
    
    .empty-lots {
      padding: 30px 16px;
      text-align: center;
      color: #6c757d;
      font-size: 15px;
    }
    
    /* Amélioration du champ quantité */
    #in-quantite {
      font-weight: 600;
      color: #0d6efd;
      background: #f8f9fa;
    }
    
    #in-quantite:read-only {
      cursor: not-allowed;
    }
  </style>
</head>
<body>

<template id="tpl-page">
  <div class="page-heading">
    <div><h1>Transformation</h1><p>Transformer un ou plusieurs lots de paddy</p></div>
    <a href="/transformation/lotPaddyTransforme" class="btn btn-outline btn-sm">Retour a la liste</a>
  </div>

  <% if (success != null) { %>
    <div class="alert alert-success"><%= success %></div>
  <% } %>
  <% if (error != null) { %>
    <div class="alert alert-error"><%= error %></div>
  <% } %>

  <div class="card flow-card">
    <div class="card-body">
      <form id="form-transformation" action="/transformation/traitementAjout" method="post">
        <div class="form-field full">
          <label>Date et heure</label>
          <input type="datetime-local" name="date" id="in-date" required>
        </div>
        <div class="form-field full">
          <label>Lots de paddy disponibles</label>
          <div class="lots-container">
            <div class="lots-header">
              <span>ID</span>
              <span>Référence</span>
              <span style="text-align: right;">Quantité</span>
            </div>
            
            <% if(listeDto != null && !listeDto.isEmpty()) { 
              double totalGeneral = 0;
              for(LotStockDto l : listeDto) { 
                totalGeneral += l.getQuantiteReel();
            %>
              <div class="lot-item">
                <span class="lot-id">#<%= l.getIdLot() %></span>
                <span class="lot-reference"><%= l.getReference() %></span>
                <span class="lot-quantity"><%= String.format("%.1f", l.getQuantiteReel()) %></span>
              </div>
            <% } 
            } else { %>
              <div class="empty-lots">Aucun lot disponible</div>
            <% } %>
            
    
          </div>
        </div>

        <div class="form-field full">
          <label>Quantite totale de Paddy (kg)</label>
          <input type="number" 
                 name="quantite" 
                 id="in-quantite" 
                 step="0.1" 
                 required
                 placeholder="Entrez la quantité totale">
        </div>

        <div class="flow-actions">
          <button type="submit" class="btn btn-primary">Transformer</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
  // Injection de l'utilisateur Spring dans sessionStorage pour le shell JS
  if (!sessionStorage.getItem('vv_user')) {
    sessionStorage.setItem('vv_user', JSON.stringify({
      email: 'user@volyVary.mg',
      name: 'Utilisateur',
      role: 'Responsable Transformation'
    }));
  }
  window.APP_DEPTH = 1;
</script>
<script src="/assets/js/toast.js"></script>
<script src="/assets/js/modal.js"></script>
<script src="/assets/js/store.js"></script>
<script src="/assets/js/auth.js"></script>
<script src="/assets/js/sidebar.js"></script>
<script>
  const pageBody = renderShell('transformation');
  if (pageBody) {
    pageBody.appendChild(document.getElementById('tpl-page').content.cloneNode(true));
  }
</script>
</body>
</html>