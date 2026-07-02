package com.volyVary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.volyVary.modele.LotPaddyTransforme;

@Repository
public interface LotPaddyTransformeRepository extends JpaRepository<LotPaddyTransforme, Integer>{
    @Query(value = "SELECT nextval('lot_paddy_reference_seq')", nativeQuery = true)
    String getNextReference();
}
