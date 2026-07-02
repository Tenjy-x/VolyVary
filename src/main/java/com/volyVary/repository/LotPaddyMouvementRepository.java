package com.volyVary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.volyVary.modele.LotPaddyMouvement;

@Repository
public interface LotPaddyMouvementRepository extends JpaRepository<LotPaddyMouvement , Integer>{
    @Query("SELECT SUM(l.quantite) FROM lot_paddy_mouvement l")
    Double sommeQuantiteLotPaddyMouvement();
}
