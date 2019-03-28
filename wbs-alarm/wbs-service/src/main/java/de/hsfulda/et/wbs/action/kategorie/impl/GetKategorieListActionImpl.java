package de.hsfulda.et.wbs.action.kategorie.impl;

import de.hsfulda.et.wbs.action.kategorie.GetKategorieListAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieData;
import de.hsfulda.et.wbs.repository.KategorieRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetKategorieListActionImpl implements GetKategorieListAction {

    private final KategorieRepository repo;
    private final AccessService accessService;

    public GetKategorieListActionImpl(KategorieRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public List<KategorieData> perform(WbsUser user, Long traegerId) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {
            return repo.findAllByTraegerId(traegerId);
        });
    }
}
