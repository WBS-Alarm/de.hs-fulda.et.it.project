package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.entity.Traeger;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TraegerRepository extends CrudRepository<Traeger, Long> {

    @Query("SELECT t FROM Traeger t")
    List<TraegerData> findAllAsData();

    @Query("SELECT t FROM Traeger t where t.id = :id")
    Optional<TraegerData> findByIdAsData(@Param("id") Long id);

    List<Traeger> findByName(String name);

    @Modifying
    @Query("UPDATE Traeger t SET t.name = :name WHERE t.id = :id")
    void updateName(@Param("id") Long id, @Param("name") String name);
}
