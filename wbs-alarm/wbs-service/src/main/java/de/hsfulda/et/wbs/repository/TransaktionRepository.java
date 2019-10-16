package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.entity.Transaktion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransaktionRepository extends CrudRepository<Transaktion, Long> {

    @Query("SELECT t FROM Transaktion t")
    List<TransaktionData> findAllAsData();

    @Query("SELECT t FROM Transaktion t where t.id = :id")
    Optional<TransaktionData> findByIdAsData(@Param("id") Long id);

    @Query("SELECT t FROM Transaktion t JOIN t.von v JOIN v.traeger s where s.id = :traegerId")
    List<TransaktionData> findAllAsDataByTraegerId(@Param("traegerId") Long traegerId);
}
