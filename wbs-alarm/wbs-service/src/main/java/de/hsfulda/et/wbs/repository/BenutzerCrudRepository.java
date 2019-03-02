package de.hsfulda.et.wbs.repository;

import de.hsfulda.et.wbs.entity.Benutzer;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface BenutzerCrudRepository extends CrudRepository<Benutzer, BigInteger> {

    Benutzer findByUsername(String username);
}
