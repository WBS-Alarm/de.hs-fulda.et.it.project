package de.hsfulda.et.wbs.action.impl;

import de.hsfulda.et.wbs.action.GetAuthorityListAction;
import de.hsfulda.et.wbs.core.data.AuthorityData;
import de.hsfulda.et.wbs.security.repository.AuthorityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAutorityListActionImpl implements GetAuthorityListAction {

    private final AuthorityRepository repo;

    public GetAutorityListActionImpl(AuthorityRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<AuthorityData> perform() {
        return repo.findAllByAktivTrueOrderById();
    }
}
