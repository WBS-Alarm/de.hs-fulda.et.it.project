package de.hsfulda.et.wbs.security.repository;

import de.hsfulda.et.wbs.core.data.AuthorityData;
import de.hsfulda.et.wbs.security.entity.Authority;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    List<AuthorityData> findAllByAktivTrueOrderById();
}
