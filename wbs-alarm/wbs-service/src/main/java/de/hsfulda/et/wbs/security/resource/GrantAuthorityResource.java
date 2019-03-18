package de.hsfulda.et.wbs.security.resource;

import de.hsfulda.et.wbs.core.HalJsonResource;
import de.hsfulda.et.wbs.entity.Benutzer;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.security.entity.Authority;
import de.hsfulda.et.wbs.security.entity.GrantedAuthority;
import de.hsfulda.et.wbs.security.repository.AuthorityRepository;
import de.hsfulda.et.wbs.security.repository.GrantedAuthorityRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static de.hsfulda.et.wbs.Application.CONTEXT_ROOT;
import static de.hsfulda.et.wbs.core.HalJsonResource.HAL_JSON;

/**
 * Über diese Resource werden die Rechte an einen Benutzer vergeben.
 */
@RestController
@RequestMapping(GrantAuthorityResource.PATH)
public class GrantAuthorityResource {

    public static final String PATH = CONTEXT_ROOT + "/authority/{authorityId}/grant/{benutzerId}";

    private final GrantedAuthorityRepository grantedAuthorityRepository;
    private final AuthorityRepository authorityRepository;
    private final BenutzerRepository benutzerRepository;

    public GrantAuthorityResource(
            GrantedAuthorityRepository grantedAuthorityRepository,
            AuthorityRepository authorityRepository,
            BenutzerRepository benutzerRepository) {
        this.grantedAuthorityRepository = grantedAuthorityRepository;
        this.authorityRepository = authorityRepository;
        this.benutzerRepository = benutzerRepository;
    }

    @PostMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> post(
            @PathVariable("authorityId") Long authorityId,
            @PathVariable("benutzerId") Long benutzerId) {
        Optional<Benutzer> benutzer = benutzerRepository.findById(benutzerId);
        Optional<Authority> authority = authorityRepository.findById(authorityId);

        if (benutzer.isPresent() && authority.isPresent()) {

            List<GrantedAuthority> granted = grantedAuthorityRepository.findByUserId(benutzerId);
            boolean alreadyGranted = granted.stream().anyMatch(g -> authorityId.equals(g.getAuthorityId()));
            if (!alreadyGranted) {
                GrantedAuthority toGrant = new GrantedAuthority();
                toGrant.setAuthorityId(authorityId);
                toGrant.setUserId(benutzerId);
                grantedAuthorityRepository.save(toGrant);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping(produces = HAL_JSON)
    @PreAuthorize("hasAuthority('TRAEGER_MANAGER')")
    HttpEntity<HalJsonResource> delete(
            @PathVariable("authorityId") Long authorityId,
            @PathVariable("benutzerId") Long benutzerId) {
        Optional<Benutzer> benutzer = benutzerRepository.findById(benutzerId);
        Optional<Authority> authority = authorityRepository.findById(authorityId);

        if (benutzer.isPresent() && authority.isPresent()) {

            List<GrantedAuthority> granted = grantedAuthorityRepository.findByUserId(benutzerId);
            Optional<GrantedAuthority> found = granted.stream()
                    .filter(g -> authorityId.equals(g.getAuthorityId()))
                    .findFirst();

            if (found.isPresent()) {
                grantedAuthorityRepository.delete(found.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}