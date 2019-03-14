package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.entity.Traeger;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TraegerRepository extends CrudRepository<Traeger, Long> {

    List<Traeger> findByName(String name);

    @Query("SELECT COUNT(t) FROM Traeger t " +
            "JOIN t.benutzer b " +
            "where b.id = :userId " +
            "and t.id = (select t2.id from Traeger t2 join t2.benutzer b2 where b2.id = :benutzerId and b2.aktiv = true)")
    Long findTraegerByUsernameAndBenutzerId(@Param("userId") Long userID, @Param("benutzerId") Long benutzerId);

    @Query("SELECT COUNT(t) FROM Traeger t " +
            "JOIN t.benutzer b " +
            "JOIN t.zielorte z " +
            "where b.id = :userId " +
            "and z.id = :zielortId")
    Long findTraegerByUsernameAndZielortId(@Param("userId") Long userID, @Param("zielortId") Long zielortId);

    @Query("SELECT COUNT(t) FROM Traeger t " +
            "JOIN t.benutzer b " +
            "where b.id = :userId " +
            "and t.id = :traegerId")
    Long findTraegerByUsernameAndTraegerId(@Param("userId") Long userID, @Param("traegerId") Long traegerId);
}
