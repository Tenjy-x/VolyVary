package com.volyVary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.volyVary.modele.LotPaddy;
import java.lang.Double;
@Repository
public interface LotPaddyRepository extends JpaRepository<LotPaddy, Integer>{
    @Query("SELECT SUM(l.quantite) FROM LotPaddy l")
    Double sommeQuantite();

    @Query("SELECT quantite FROM LotPaddy l WHERE l.id =: id_lot")
    Double getLotPaddyByIdLot(@Param("id_lot") int id_lot);
}
