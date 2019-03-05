package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.entity.Benutzer;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface BenutzerRepository extends CrudRepository<Benutzer, Long> {

    Benutzer findByUsername(String username);
}
