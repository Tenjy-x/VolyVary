package com.volyVary.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.volyVary.dto.HistoriqueDto;
import com.volyVary.modele.LotPaddyTransforme;
import com.volyVary.modele.TransformationModel;

@Repository
public interface LotPaddyTransformeRepository extends JpaRepository<LotPaddyTransforme, Integer>{
    @Query(value = "SELECT nextval('lot_paddy_reference_seq')", nativeQuery = true)
    String getNextReference();

    LotPaddyTransforme findTopByOrderByIdDesc();
    LotPaddyTransforme findById(int id);
    @Query("SELECT SUM(l.quantite) FROM LotPaddyTransforme l")
    double quantiteTotalTransformer();

    List<LotPaddyTransforme> findByDateBetween(Date debut, Date fin);

    List<LotPaddyTransforme> findByDateGreaterThanEqual(Date debut);

    List<LotPaddyTransforme> findByDateLessThanEqual(Date fin);

}
