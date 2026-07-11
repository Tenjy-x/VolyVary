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
            String referenceParam = request.getParameter("reference");
            boolean hasDebut = debut != null && !debut.isBlank();
            boolean hasFin = fin != null && !fin.isBlank();
            boolean hasFiltre = hasDebut || hasFin;
            boolean hasReferenceSearch = referenceParam != null && !referenceParam.isBlank();
            String exportPdfUrl = hasFiltre
            ? "/transformation/pdfListe?debut=" + (hasDebut ? debut : "") + "&fin=" + (hasFin ? fin : "")
            : "/transformation/pdfListe";
            double depenseTotal = (t != null) ? t.getPrixUnitaire() * totalPaddyTransforme : 0;
            long pageStart = (listePaddy != null && listePaddy.getTotalElements() > 0)
            ? ((long) listePaddy.getNumber() * listePaddy.getSize()) + 1
            : 0;
            long pageEnd = (listePaddy != null && listePaddy.getTotalElements() > 0)
            ? pageStart + listePaddy.getNumberOfElements() - 1
            : 0;
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
              <style>
                .filter-panel {
                  padding: var(--space-5);
                }

                .filter-panel-head {
                  display: flex;
                  align-items: flex-start;
                  justify-content: space-between;
                  gap: var(--space-4);
                  margin-bottom: var(--space-4);
                  flex-wrap: wrap;
                }

                .filter-panel-head h2 {
                  font-size: var(--fs-lg);
                  color: var(--color-gray-900);
                  margin-bottom: 4px;
                }

                .filter-panel-head p {
                  color: var(--color-gray-500);
                  font-size: var(--fs-sm);
                }

                .filter-bar {
                  display: grid;
                  grid-template-columns: repeat(2, minmax(190px, 1fr)) auto;
                  gap: var(--space-4);
                  align-items: end;
                }

                .filter-actions {
                  display: flex;
                  align-items: center;
                  gap: var(--space-2);
                  flex-wrap: wrap;
                }

                .filter-chip {
                  display: inline-flex;
                  align-items: center;
                  gap: 6px;
                  padding: 8px 12px;
                  border-radius: 999px;
                  background: var(--color-blue-light);
                  color: var(--color-blue);
                  font-size: var(--fs-xs);
                  font-weight: 700;
                }

                .filter-chip.off {
                  background: var(--color-gray-100);
                  color: var(--color-gray-500);
                }

                .filter-input {
                  position: relative;
                }

                .filter-input input {
                  background: linear-gradient(180deg, #ffffff 0%, #f8fafc 100%);
                }

                .table-meta {
                  display: flex;
                  align-items: center;
                  gap: var(--space-3);
                  flex-wrap: wrap;
                }

                .table-search-hint {
                  font-size: var(--fs-xs);
                  color: var(--color-gray-500);
                }

                .table-result-badge {
                  display: inline-flex;
                  align-items: center;
                  justify-content: center;
                  min-width: 40px;
                  padding: 7px 12px;
                  border-radius: 999px;
                  background: var(--color-gray-100);
                  color: var(--color-gray-700);
                  font-size: var(--fs-xs);
                  font-weight: 700;
                }

                .dt-search.active {
                  background: var(--color-white);
                  box-shadow: 0 0 0 3px var(--color-blue-light);
                }

                .sort-link {
                  display: inline-flex;
                  align-items: center;
                  gap: 6px;
                  color: inherit;
                  font: inherit;
                  text-transform: inherit;
                  letter-spacing: inherit;
                  background: none;
                  border: none;
                  padding: 0;
                  cursor: pointer;
                }

                .sort-link:hover {
                  color: var(--color-blue);
                }

                .sort-link:focus-visible {
                  outline: 2px solid var(--color-blue);
                  outline-offset: 2px;
                  border-radius: 4px;
                }

                .sort-arrow {
                  display: inline-block;
                  min-width: 10px;
                  opacity: 0.35;
                  font-size: 10px;
                  transition: opacity var(--transition), color var(--transition);
                }

                th.sorted .sort-arrow {
                  opacity: 1;
                  color: var(--color-blue);
                }

                @media (max-width: 900px) {
                  .filter-bar {
                    grid-template-columns: 1fr;
                  }

                  .filter-actions {
                    width: 100%;
                  }

                  .filter-actions .btn {
                    flex: 1;
                    justify-content: center;
                  }
                }
              </style>
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
                      <div class="filter-panel">
                        <div class="filter-panel-head">
                          <div>
                            <h2>Filtrer les transformations</h2>
                            <p>Affinez la liste par plage de dates avant export ou consultation detaillee.</p>
                          </div>
                          <span class="filter-chip <%= hasFiltre ? "" : "off" %>">
                            <%= hasFiltre ? "Filtre actif" : "Aucun filtre actif" %>
                          </span>
                        </div>
                        <form action="/transformation/traitementFiltre" method="get" class="filter-bar">
                          <div class="form-field filter-input">
                            <label for="filter-debut">Date debut</label>
                            <input id="filter-debut" type="datetime-local" name="debut" value="<%= hasDebut ? debut : "" %>">
                          </div>
                          <div class="form-field filter-input">
                            <label for="filter-fin">Date fin</label>
                            <input id="filter-fin" type="datetime-local" name="fin" value="<%= hasFin ? fin : "" %>">
                          </div>
                          <div class="filter-actions">
                            <input type="hidden" name="page" value="0">
                            <button type="submit" class="btn btn-primary btn-sm">Appliquer</button>
                            <a href="/transformation/lotPaddyTransforme" class="btn btn-outline btn-sm">Reinitialiser</a>
                          </div>
                        </form>
                      </div>
                    </div>

                    <%-- Tableau --%>
                      <div class="section-card">
                        <div id="trf-table">
                          <div class="dt-toolbar">
                            <form action="/transformation/searchReference" method="get" class="dt-toolbar-left">
                              <div class="dt-search" id="trf-search-box">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                  <circle cx="11" cy="11" r="8" />
                                  <path d="M21 21l-4.35-4.35" />
                                </svg>
                                <input type="text" id="trf-search-input" name="reference" value="<%= referenceParam != null ? referenceParam : "" %>" placeholder="Rechercher une reference...">
                              </div>
                              <div class="table-meta">
                                <span class="table-result-badge" id="trf-search-count"><%= listePaddy != null ? listePaddy.getNumberOfElements() : 0 %></span>
                                <button type="submit" class="btn btn-primary btn-sm">Rechercher</button>
                              </div>
                            </form>
                            <div class="dt-toolbar-right">
                              <a href="<%= exportPdfUrl %>" class="btn btn-outline btn-sm">PDF</a>
                              <a href="/transformation/formulaireAjoutTransformation" class="btn btn-primary btn-sm">
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                  <path d="M12 5v14M5 12h14" />
                                </svg>
                                Nouvelle transformation
                              </a>
                            </div>
                          </div>
                          <div class="table-wrap">
                            <table class="dt" id="trf-list-table">
                              <thead>
                                <tr>
                                  <th data-sort-key="reference">
                                    <button type="button" class="sort-link">
                                      Reference
                                      <span class="sort-arrow">▲</span>
                                    </button>
                                  </th>
                                  <th data-sort-key="date">
                                    <button type="button" class="sort-link">
                                      Date
                                      <span class="sort-arrow">▲</span>
                                    </button>
                                  </th>
                                  <th data-sort-key="quantite">
                                    <button type="button" class="sort-link">
                                      Quantite
                                      <span class="sort-arrow">▲</span>
                                    </button>
                                  </th>
                                  <th data-sort-key="prix-unitaire">
                                    <button type="button" class="sort-link">
                                      Prix unitaire
                                      <span class="sort-arrow">▲</span>
                                    </button>
                                  </th>
                                  <th data-sort-key="total">
                                    <button type="button" class="sort-link">
                                      Total
                                      <span class="sort-arrow">▲</span>
                                    </button>
                                  </th>
                                  <th>Actions</th>
                                </tr>
                              </thead>
                              <tbody>
                                <% if (listePaddy==null || listePaddy.isEmpty()) { %>
                                  <tr>
                                    <td colspan="6" class="dt-empty">
                                      <%= message !=null ? message : "Aucun resultat" %>
                                    </td>
                                  </tr>
                                  <% } else { %>
                                    <% for (LotPaddyTransforme l : listePaddy.getContent()) { %>
                                      <tr class="trf-row"
                                        data-reference="<%= l.getReference() != null ? l.getReference().toLowerCase() : "" %>"
                                        data-date="<%= l.getDate() != null ? l.getDate().toString() : "" %>"
                                        data-quantite="<%= l.getQuantite() %>"
                                        data-prix-unitaire="<%= t != null ? t.getPrixUnitaire() : 0 %>"
                                        data-total="<%= l.getPrixTransformation() %>">
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
                                          <div class="row-actions">
                                            <a class="action-icon"
                                              href="/transformation/detailsLotPaddyTransforme?idLotTransforme=<%= l.getId() %>"
                                              aria-label="Voir detail">
                                              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                                                <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" />
                                                <circle cx="12" cy="12" r="3" />
                                              </svg>
                                            </a>
                                          </div>
                                        </td>
                                      </tr>
                                      <% } %>
                                <% } %>
                              </tbody>
                            </table>
                          </div>
                          <% if(listePaddy !=null) { %>
                            <div class="dt-pagination">
                              <span class="dt-info"><%= pageStart %>-<%= pageEnd %> sur <%= listePaddy.getTotalElements() %></span>
                              <div class="dt-pages">
                                <% if (listePaddy.hasPrevious()) { %>
                                  <a class="dt-page-btn"
                                   href="<%= hasReferenceSearch
                                      ? "/transformation/searchReference?page=" + (listePaddy.getNumber() - 1)
                                        + "&reference=" + referenceParam
                                      : (hasFiltre
                                        ? "/transformation/traitementFiltre?page=" + (listePaddy.getNumber() - 1)
                                          + "&debut=" + (hasDebut ? debut : "")
                                          + "&fin=" + (hasFin ? fin : "")
                                        : "/transformation/lotPaddyTransforme?page=" + (listePaddy.getNumber() - 1))
                                     %>">&laquo;</a>
                                <% } else { %>
                                  <span class="dt-page-btn" style="cursor:not-allowed;opacity:.35">&laquo;</span>
                                <% } %>

                                <span class="dt-page-btn active"><%= listePaddy.getNumber() + 1 %></span>

                                <% if (listePaddy.hasNext()) { %>
                                  <a class="dt-page-btn"
                                   href="<%= hasReferenceSearch
                                      ? "/transformation/searchReference?page=" + (listePaddy.getNumber() + 1)
                                        + "&reference=" + referenceParam
                                      : (hasFiltre
                                        ? "/transformation/traitementFiltre?page=" + (listePaddy.getNumber() + 1)
                                          + "&debut=" + (hasDebut ? debut : "")
                                          + "&fin=" + (hasFin ? fin : "")
                                        : "/transformation/lotPaddyTransforme?page=" + (listePaddy.getNumber() + 1))
                                     %>">&raquo;</a>
                                <% } else { %>
                                  <span class="dt-page-btn" style="cursor:not-allowed;opacity:.35">&raquo;</span>
                                <% } %>
                              </div>
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
