package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.core.data.BestandViewData;
import de.hsfulda.et.wbs.entity.view.BestandViewRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BestandViewRepository extends CrudRepository<BestandViewRecord, String> {

    @Query("select b from BestandViewRecord b where b.traegerId = :traegerId")
    List<BestandViewData> findByTraegerAsData(@Param("traegerId") Long traegerId);
}
