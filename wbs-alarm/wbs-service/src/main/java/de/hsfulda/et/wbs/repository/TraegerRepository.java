package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.entity.Traeger;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface TraegerRepository extends CrudRepository<Traeger, Long> {

    List<Traeger> findByName(String name);

    @Query("SELECT t FROM Traeger t " +
            "JOIN t.benutzer b " +
            "where b.username = :username " +
            "and t.id = (select t2.id from Traeger t2 join t2.benutzer b2 where b2.id = :benutzerId)")
    Optional<Traeger> findTraegerByUsernameAndBenutezrId(@Param("username") String username, @Param("benutzerId") Long benutzerId);
}
