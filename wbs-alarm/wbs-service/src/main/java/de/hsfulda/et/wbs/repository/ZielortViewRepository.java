package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.core.data.ZielortViewData;
import de.hsfulda.et.wbs.entity.view.ZielortViewRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ZielortViewRepository extends CrudRepository<ZielortViewRecord, String> {

    @Query("select b from ZielortViewRecord b where b.traegerId = :traegerId")
    List<ZielortViewData> findByTraegerAsData(@Param("traegerId") Long traegerId);
}
