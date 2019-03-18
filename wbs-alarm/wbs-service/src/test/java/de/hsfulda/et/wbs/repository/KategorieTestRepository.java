package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.entity.Kategorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KategorieTestRepository extends CrudRepository<Kategorie, Long> {

    @Query("select z from Kategorie z join z.traeger t where z.name = :name and t.name = :traeger")
    List<Kategorie> findByName(@Param("name") String name, @Param("traeger") String traeger);

}