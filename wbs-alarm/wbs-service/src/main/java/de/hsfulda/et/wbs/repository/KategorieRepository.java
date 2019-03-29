package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.entity.Kategorie;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KategorieRepository extends CrudRepository<Kategorie, Long> {

    @Query("select z from Kategorie z join z.traeger t where t.id = :traegerId")
    List<KategorieData> findAllByTraegerId(@Param("traegerId") Long traegerId);

    Optional<KategorieData> findByIdAndAktivIsTrue(Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Kategorie k set k.name = :name where k.id = :id")
    void updateName(@Param("id") Long id, @Param("name") String name);

    @Modifying
    @Query("update Kategorie k set k.aktiv = false where k.id = :id")
    void deactivate(@Param("id") Long id);
}
