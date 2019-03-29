package de.hsfulda.et.wbs.action.impl;

import de.hsfulda.et.wbs.action.DeleteGrantedAuthorityAction;
import de.hsfulda.et.wbs.core.data.GrantedAuthorityData;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.security.repository.AuthorityRepository;
import de.hsfulda.et.wbs.security.repository.GrantedAuthorityRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Component
public class DeleteGrantedAuthorityActionImpl implements DeleteGrantedAuthorityAction {

    private final GrantedAuthorityRepository repo;
    private final AuthorityRepository authorityRepository;
    private final BenutzerRepository benutzerRepository;

    public DeleteGrantedAuthorityActionImpl(
            GrantedAuthorityRepository repo,
            AuthorityRepository authorityRepository,
            BenutzerRepository benutzerRepository) {
        this.repo = repo;
        this.authorityRepository = authorityRepository;
        this.benutzerRepository = benutzerRepository;
    }

    @Override
    public void perform(Long authorityId, Long benutzerId) {
        if (!(benutzerRepository.existsById(benutzerId) && authorityRepository.existsById(authorityId))) {
            throw new ResourceNotFoundException();
        }

        List<GrantedAuthorityData> granted = repo.findByUserId(benutzerId);
        Optional<GrantedAuthorityData> found = granted.stream()
                .filter(g -> authorityId.equals(g.getAuthorityId()))
                .findFirst();

        if (!found.isPresent()) {
            throw new ResourceNotFoundException();
        }

        repo.deleteById(authorityId, benutzerId);
    }
}
