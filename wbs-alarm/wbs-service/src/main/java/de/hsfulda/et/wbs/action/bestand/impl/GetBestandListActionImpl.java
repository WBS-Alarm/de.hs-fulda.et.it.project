package de.hsfulda.et.wbs.action.bestand.impl;

import de.hsfulda.et.wbs.action.bestand.GetBestandListAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.repository.BestandRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetBestandListActionImpl implements GetBestandListAction {

    private final BestandRepository repo;
    private final AccessService accessService;

    public GetBestandListActionImpl(BestandRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public List<BestandData> perform(WbsUser user, Long zielortId) {
        return accessService.hasAccessOnZielort(user, zielortId, () -> {
            return repo.findAllByZielortId(zielortId);
        });
    }
}
