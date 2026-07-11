package com.volyVary.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.volyVary.modele.LotPaddyTransforme;

@Repository
public interface LotPaddyTransformeRepository extends JpaRepository<LotPaddyTransforme, Integer>{
    @Query(value = "SELECT nextval('lot_paddy_reference_seq')", nativeQuery = true)
    String getNextReference();

    LotPaddyTransforme findTopByOrderByIdDesc();
    LotPaddyTransforme findById(int id);
    @Query("SELECT SUM(l.quantite) FROM LotPaddyTransforme l")
    double quantiteTotalTransformer();

    List<LotPaddyTransforme> findByDateBetween(LocalDateTime debut, LocalDateTime fin);

    List<LotPaddyTransforme> findByDateGreaterThanEqual(LocalDateTime debut);

    List<LotPaddyTransforme> findByDateLessThanEqual(LocalDateTime fin);

    List<LotPaddyTransforme> findByDate(LocalDateTime date);

    Page<LotPaddyTransforme> findByDateBetween(
            LocalDateTime debut,
            LocalDateTime fin,
            Pageable pageable
    );


    Page<LotPaddyTransforme> findByDateGreaterThanEqual(
            LocalDateTime debut,
            Pageable pageable
    );


    Page<LotPaddyTransforme> findByDateLessThanEqual(
            LocalDateTime fin,
            Pageable pageable
    );


    @Query("SELECT l FROM LotPaddyTransforme l WHERE LOWER(l.reference) LIKE LOWER(CONCAT('%', :reference, '%'))")
        Page<LotPaddyTransforme> rechercherParReference(@Param("reference") String reference, Pageable pageable);
}   
