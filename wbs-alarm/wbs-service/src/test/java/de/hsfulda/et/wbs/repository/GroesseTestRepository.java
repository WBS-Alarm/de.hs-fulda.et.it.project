package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.entity.Groesse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroesseTestRepository extends CrudRepository<Groesse, Long> {

    @Query("select z from Groesse z " +
        "join z.kategorie k " +
        "join k.traeger t " +
        "where z.name = :name and k.name = :kategorie and t.name = :traeger")
    List<Groesse> findByName(
        @Param("name") String name,
        @Param("kategorie") String kategorie,
        @Param("traeger") String traeger);

}