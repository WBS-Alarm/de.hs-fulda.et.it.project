package de.hsfulda.et.wbs.security.repository;

import de.hsfulda.et.wbs.core.data.GrantedAuthorityData;
import de.hsfulda.et.wbs.security.entity.GrantedAuthority;
import de.hsfulda.et.wbs.security.entity.GrantedAuthorityKey;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrantedAuthorityRepository extends CrudRepository<GrantedAuthority, GrantedAuthorityKey> {

    @Query("select ga from GrantedAuthority ga where pk.userId = :userId")
    List<GrantedAuthorityData> findByUserId(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query("delete from GrantedAuthority ga  where ga.pk.authorityId = :authorityId and ga.pk.userId = :benutzerId")
    void deleteById(@Param("authorityId") Long authorityId, @Param("benutzerId") Long benutzerId);
}
