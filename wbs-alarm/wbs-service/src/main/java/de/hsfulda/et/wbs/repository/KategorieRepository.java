package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.entity.Kategorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KategorieRepository extends CrudRepository<Kategorie, Long> {

    @Query("select z from Kategorie z join z.traeger t where t.id = :traegerId")
    List<Kategorie> findAllByTraegerId(@Param("traegerId") Long traegerId);

    Optional<Kategorie> findByIdAndAktivIsTrue(Long id);
}
