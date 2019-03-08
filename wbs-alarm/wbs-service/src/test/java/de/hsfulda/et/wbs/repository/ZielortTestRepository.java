package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.entity.Zielort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ZielortTestRepository extends CrudRepository<Zielort, Long> {

    @Query("select z from Zielort z join z.traeger t where z.name = :name and t.name = :traeger")
    List<Zielort> findByName(@Param("name") String name, @Param("traeger") String traeger);

}