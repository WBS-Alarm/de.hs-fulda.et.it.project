package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.entity.Bestand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BestandTestRepository extends CrudRepository<Bestand, Long> {

    @Query("select b from Bestand b " +
        "join b.zielort z " +
        "join z.traeger t " +
        "join b.groesse g " +
        "where t.name = :traeger " +
        "and g.name = :groesse " +
        "and z.name = :zielort")
    List<Bestand> findByName(@Param("traeger") String traeger, @Param("zielort") String zielort, @Param("groesse") String groesse);

}