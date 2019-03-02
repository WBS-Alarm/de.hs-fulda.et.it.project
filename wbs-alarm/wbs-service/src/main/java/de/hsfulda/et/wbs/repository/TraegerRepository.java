package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.entity.Traeger;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TraegerRepository extends CrudRepository<Traeger, Long> {

    List<Traeger> findByName(String name);
}
