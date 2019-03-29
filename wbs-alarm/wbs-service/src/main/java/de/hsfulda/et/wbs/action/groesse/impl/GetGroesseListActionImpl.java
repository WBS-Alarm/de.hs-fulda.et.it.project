package de.hsfulda.et.wbs.action.groesse.impl;

import de.hsfulda.et.wbs.action.groesse.GetGroesseListAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.repository.GroesseRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetGroesseListActionImpl implements GetGroesseListAction {

    private final GroesseRepository repo;
    private final AccessService accessService;

    public GetGroesseListActionImpl(GroesseRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public List<GroesseData> perform(WbsUser user, Long kategorieId) {
        return accessService.hasAccessOnKategorie(user, kategorieId, () -> {
            return repo.findAllByKategorieId(kategorieId);
        });
    }
}
