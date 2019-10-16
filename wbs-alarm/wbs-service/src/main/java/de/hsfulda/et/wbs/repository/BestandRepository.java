package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.entity.Bestand;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BestandRepository extends CrudRepository<Bestand, Long> {

    @Query("select b from Bestand b where b.id = :id")
    Optional<BestandData> findByIdAsData(@Param("id") Long id);

    @Query("select b from Bestand b join b.zielort z where z.id = :zielortId")
    List<BestandData> findAllByZielortId(@Param("zielortId") Long zielortId);

    @Query("select b from Bestand b join b.zielort z join b.groesse g where z.id = :zielortId and g.id = :groesseId")
    Optional<BestandData> findByZielortIdAndGroesseIdAsData(@Param("zielortId") Long zielortId,
            @Param("groesseId") Long groesseId);

    @Query("select b from Bestand b join b.zielort z join b.groesse g where z.id = :zielortId and g.id = :groesseId")
    Optional<Bestand> findByZielortIdAndGroesseId(@Param("zielortId") Long zielortId,
            @Param("groesseId") Long groesseId);

    @Modifying(clearAutomatically = true)
    @Query("update Bestand b set b.anzahl = :anzahl where b.id = :id")
    void updateAnzahl(@Param("id") Long id, @Param("anzahl") Long anzahl);

    @Query("select z.erfasst from Bestand b join b.zielort z where b.id = :id")
    boolean isZielortErfasst(@Param("id") Long id);
}
