package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.core.data.ZielortData;
import de.hsfulda.et.wbs.entity.Zielort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ZielortRepository extends CrudRepository<Zielort, Long> {

    @Query("select z from Zielort z join z.traeger t where t.id = :traegerId")
    List<ZielortData> findAllByTraegerId(@Param("traegerId") Long traegerId);

    Optional<Zielort> findByIdAndAktivIsTrue(Long id);
}
