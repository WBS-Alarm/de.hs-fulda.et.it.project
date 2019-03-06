package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.entity.Benutzer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BenutzerRepository extends CrudRepository<Benutzer, Long> {

    @Query("select b from Benutzer b where b.username = :username and b.aktiv = true")
    Benutzer findByUsername(@Param("username") String username);
}
