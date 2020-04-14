package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.entity.Groesse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroesseRepository extends CrudRepository<Groesse, Long> {

    @Query("select z from Groesse z join z.kategorie t where t.id = :groesseId")
    List<GroesseData> findAllByKategorieId(@Param("groesseId") Long groesseId);

    Optional<GroesseData> findByIdAndAktivIsTrue(Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Groesse g set g.name = :name, g.bestandsgrenze = :bestandsgrenze where g.id = :id")
    void updateNameAndBestandsgrenze(@Param("id") Long id, @Param("name") String name,
            @Param("bestandsgrenze") int bestandsgrenze);

    @Modifying
    @Query("update Groesse g set g.aktiv = false where g.id = :id")
    void deactivate(@Param("id") Long id);
}