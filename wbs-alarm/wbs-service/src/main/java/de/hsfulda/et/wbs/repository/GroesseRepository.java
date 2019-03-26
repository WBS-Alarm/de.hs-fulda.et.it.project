package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.entity.Groesse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroesseRepository extends CrudRepository<Groesse, Long> {

    @Query("select z from Groesse z join z.kategorie t where t.id = :groesseId")
    List<GroesseData> findAllByKategorieId(@Param("groesseId") Long groesseId);

    Optional<Groesse> findByIdAndAktivIsTrue(Long id);

}