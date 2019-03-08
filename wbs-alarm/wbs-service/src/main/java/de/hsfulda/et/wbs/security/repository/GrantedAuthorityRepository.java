package de.hsfulda.et.wbs.security.repository;

import de.hsfulda.et.wbs.security.entity.GrantedAuthority;
import de.hsfulda.et.wbs.security.entity.GrantedAuthorityKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrantedAuthorityRepository extends CrudRepository<GrantedAuthority, GrantedAuthorityKey> {

    @Query("select ga from GrantedAuthority ga where pk.userId = :userId")
    List<GrantedAuthority> findByUserId(@Param("userId") Long userId);

}
