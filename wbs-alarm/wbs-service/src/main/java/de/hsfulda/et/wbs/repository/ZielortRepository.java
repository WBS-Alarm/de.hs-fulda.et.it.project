package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.entity.Zielort;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ZielortRepository extends CrudRepository<Zielort, Long> {

    Optional<Zielort> findByIdAndAktivIsTrue(Long id);
}
