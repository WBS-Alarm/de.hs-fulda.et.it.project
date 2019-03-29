package de.hsfulda.et.wbs.action.impl;

import de.hsfulda.et.wbs.action.GetGrantedAuthorityListAction;
import de.hsfulda.et.wbs.core.data.GrantedAuthorityData;
import de.hsfulda.et.wbs.security.repository.GrantedAuthorityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetGrantedAuthorityListActionImpl implements GetGrantedAuthorityListAction {

    private final GrantedAuthorityRepository repo;

    public GetGrantedAuthorityListActionImpl(GrantedAuthorityRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<GrantedAuthorityData> perform(Long benutzerId) {
        return repo.findByUserId(benutzerId);
    }
}
