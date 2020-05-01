package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.core.data.KategorieViewData;
import de.hsfulda.et.wbs.entity.view.KategorieViewRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KategorieViewRepository extends CrudRepository<KategorieViewRecord, String> {

    @Query("select b from KategorieViewRecord b where b.traegerId = :traegerId")
    List<KategorieViewData> findByTraegerAsData(@Param("traegerId") Long traegerId);
}
