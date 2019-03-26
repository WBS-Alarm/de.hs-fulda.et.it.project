package de.hsfulda.et.wbs.security.repository;

import de.hsfulda.et.wbs.core.data.TokenConfigData;
import de.hsfulda.et.wbs.security.entity.TokenConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenConfigRepository extends CrudRepository<TokenConfig, String> {

    @Query("select t from TokenConfig t where t.issuer = :id")
    Optional<TokenConfigData> findByIdAsData(@Param("id") String id);

}
