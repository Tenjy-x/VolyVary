<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ page import="org.springframework.data.domain.Page" %>
    <%@ page import="com.volyVary.modele.LotPaddyTransforme" %>
      <%@ page import="com.volyVary.modele.TransformationModel" %>

        <% Page<LotPaddyTransforme> listePaddy =(Page<LotPaddyTransforme>) request.getAttribute("lotPaddyTransforme");
            TransformationModel t = (TransformationModel) request.getAttribute("transformation");
            Double totalObj = (Double) request.getAttribute("total");
            double totalPaddyTransforme = totalObj != null ? totalObj : 0;
            String message = (String) request.getAttribute("message");
            String testPaddyTransforme = (String) request.getAttribute("lotPaddyVide");
            String debut = request.getParameter("debut");
            String fin = request.getParameter("fin");
            boolean hasDebut = debut != null && !debut.isBlank();
            boolean hasFin = fin != null && !fin.isBlank();
            boolean hasFiltre = hasDebut || hasFin;
            String exportPdfUrl = hasFiltre
            ? "/transformation/pdfListe?debut=" + (hasDebut ? debut : "") + "&fin=" + (hasFin ? fin : "")
            : "/transformation/pdfListe";
            double depenseTotal = (t != null) ? t.getPrixUnitaire() * totalPaddyTransforme : 0;
            %>
            <!DOCTYPE html>
            <html lang="fr">

            <head>
              <meta charset="UTF-8">
              <meta name="viewport" content="width=device-width, initial-scale=1.0">
              <title>Transformation - VOLY VARY</title>
              <link rel="stylesheet" href="/assets/css/variables.css">
              <link rel="stylesheet" href="/assets/css/base.css">
              <link rel="stylesheet" href="/assets/css/components.css">
              <link rel="stylesheet" href="/assets/css/style.css">
            </head>

            <body>

              <template id="tpl-page">
                <div class="page-heading">
                  <div>
                    <h1>Transformation : liste des lots de paddy transforme</h1>
                    <p>Suivi des lots de transformation</p>
                  </div>
                  <a href="/transformation/formulaireAjoutTransformation" class="btn btn-primary btn-sm">+ Nouvelle
                    transformation</a>
                </div>


                <%-- Stat cards --%>
                  <div class="stat-grid">
                    <div class="stat-card">
                      <div class="stat-top">
                        <div class="stat-icon blue">
                          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M17 2l4 4-4 4M3 12v-2a4 4 0 014-4h14M7 22l-4-4 4-4M21 12v2a4 4 0 01-4 4H3" />
                          </svg>
                        </div>
                      </div>
                      <div class="stat-value">
                        <%= listePaddy !=null ? listePaddy.getTotalElements() : 0 %>
                      </div>
                      <div class="stat-label">Lots transformes</div>
                    </div>
                    <div class="stat-card">
                      <div class="stat-top">
                        <div class="stat-icon green">
                          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M17 2l4 4-4 4M3 12v-2a4 4 0 014-4h14M7 22l-4-4 4-4M21 12v2a4 4 0 01-4 4H3" />
                          </svg>
                        </div>
                      </div>
                      <div class="stat-value">
                        <% if (totalPaddyTransforme !=0) { %>
                          <%= totalPaddyTransforme %>
                            <% } else { %>
                              <%= testPaddyTransforme %>
                                <% } %> kg
                      </div>
                      <div class="stat-label">Quantite de lot de paddy transforme</div>
                    </div>
                    <div class="stat-card">
                      <div class="stat-top">
                        <div class="stat-icon yellow">
                          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M17 2l4 4-4 4M3 12v-2a4 4 0 014-4h14M7 22l-4-4 4-4M21 12v2a4 4 0 01-4 4H3" />
                          </svg>
                        </div>
                      </div>
                      <div class="stat-value">
                        <%= String.format("%,.0f", depenseTotal) %> Ar
                      </div>
                      <div class="stat-label">Depense totale pour la transformation</div>
                    </div>
                  </div>

                  <%-- Filtre --%>
                    <div class="section-card">
                      <form action="/transformation/traitementFiltre" method="get" class="filter-bar">
                        <div class="form-field">
                          <label>Date debut</label>
                          <input type="datetime-local" name="debut" value="<%= hasDebut ? debut : "" %>">
                        </div>
                        <div class="form-field">
                          <label>Date fin</label>
                          <input type="datetime-local" name="fin" value="<%= hasFin ? fin : "" %>">
                        </div>
                        <input type="hidden" name="page" value="0">
                        <button type="submit" class="btn btn-outline btn-sm">Filtrer</button>
                        <a href="<%= exportPdfUrl %>" class="btn btn-primary btn-sm">Exporter PDF</a>
                      </form>
                    </div>

                    <%-- Tableau --%>
                      <div class="section-card">
                        <div id="trf-table">
                          <table class="data-table">
                            <thead>
                              <tr>
                                <th>Reference</th>
                                <th>Date</th>
                                <th>Quantite</th>
                                <th>Prix unitaire</th>
                                <th>Total</th>
                                <th></th>
                              </tr>
                            </thead>
                            <tbody>
                              <% if (listePaddy==null || listePaddy.isEmpty()) { %>
                                <tr>
                                  <td colspan="6" class="table-empty">
                                    <%= message !=null ? message : "Aucun resultat" %>
                                  </td>
                                </tr>
                                <% } else { %>
                                  <% for (LotPaddyTransforme l : listePaddy.getContent()) { %>
                                    <tr>
                                      <td>
                                        <%= l.getReference() %>
                                      </td>
                                      <td>
                                        <%= l.getDate() %>
                                      </td>
                                      <td>
                                        <%= l.getQuantite() %> kg
                                      </td>
                                      <td>
                                        <%= t !=null ? t.getPrixUnitaire() : "-" %> Ar
                                      </td>
                                      <td>
                                        <%= l.getPrixTransformation() %> Ar
                                      </td>
                                      <td>
                                        <a class="action-icon"
                                          href="/transformation/detailsLotPaddyTransforme?idLotTransforme=<%= l.getId() %>"
                                          aria-label="Voir detail">
                                          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" />
                                            <circle cx="12" cy="12" r="3" />
                                          </svg>
                                        </a>
                                      </td>
                                    </tr>
                                    <% } %>
                                      <% } %>
                                      
                            </tbody>
                          </table>
                          <% if(listePaddy !=null) { %>
                         <div class="pagination">

    <% if (listePaddy.hasPrevious()) { %>
        <a class="btn btn-outline btn-sm"
           href="<%= hasFiltre
                ? "/transformation/traitementFiltre?page=" + (listePaddy.getNumber() - 1)
                    + "&debut=" + (hasDebut ? debut : "")
                    + "&fin=" + (hasFin ? fin : "")
                : "/transformation/lotPaddyTransforme?page=" + (listePaddy.getNumber() - 1)
           %>">
            Précédent
        </a>
    <% } %>

    <span>
        Page <%= listePaddy.getNumber() + 1 %> / <%= listePaddy.getTotalPages() %>
    </span>

    <% if (listePaddy.hasNext()) { %>
        <a class="btn btn-outline btn-sm"
           href="<%= hasFiltre
                ? "/transformation/traitementFiltre?page=" + (listePaddy.getNumber() + 1)
                    + "&debut=" + (hasDebut ? debut : "")
                    + "&fin=" + (hasFin ? fin : "")
                : "/transformation/lotPaddyTransforme?page=" + (listePaddy.getNumber() + 1)
           %>">
            Suivant
        </a>
    <% } %>

</div>
                            <% } %>
                        </div>
                      </div>
              </template>

              <script>
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

            <%-- <h1>Liste des lots de paddy</h1>
              <form action="${pageContext.request.contextPath}/transformation/traitementFiltre" method="post">
                <p>date debut :<input type="datetime-l  ocal" name="debut"></p>
                <p>date fin :<input type="datetime-local" name="fin"></p>
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
                      <% if(totalPaddyTransformer !=0) {%>
                        <b>
                          <%= totalPaddyTransformer%>
                        </b>
                        <% } else { %>
                          <b>
                            <%= testPaddyTransformer%>
                          </b>
                          <% } %>
                    </fieldset>
                  </td>

                  <td align="center">
                    <fieldset style="width:300px;height:100px;">
                      <legend>Statistique</legend>

                      <br><br>
                      Dépense total pour la transformation
                      <br><br>

                      <b>
                        <%= t.getPrixUnitaire() * totalPaddyTransformer %> Ar
                      </b>

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
                <% if (listePaddy==null || listePaddy.isEmpty()) { %>
                  <tr>
                    <td colspan="6" align="center">
                      <%= message !=null ? message : "aucun resultat trouver" %>
                    </td>
                  </tr>
                  <% } else { %>
                    <% for(LotPaddyTransforme l : listePaddy) {%>
                      <tr>
                        <td>
                          <%= l.getReference()%>
                        </td>
                        <td>
                          <%= l.getDate()%>
                        </td>
                        <td>
                          <%= l.getQuantite()%>
                        </td>
                        <td>
                          <%= t.getPrixUnitaire()%>
                        </td>
                        <td>
                          <%= l.getPrixTransformation()%>
                        </td>
                        <td>
                          <form action="${pageContext.request.contextPath}/transformation/detailsLotPaddyTransforme"
                            method="get">
                            <input type="hidden" name="idLotTransforme" value="<%= l.getId()%>">
                            <input type="submit" value="Voir détail">
                          </form>
                        </td>
                      </tr>
                      <% }%>
                        <% } %>
              </table>


              </body>

              </html> --%>