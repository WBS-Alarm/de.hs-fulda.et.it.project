package de.hsfulda.et.wbs.action.report.impl;

import de.hsfulda.et.wbs.action.report.GetKategorieViewAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.KategorieViewData;
import de.hsfulda.et.wbs.repository.KategorieViewRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetKategorieViewActionImpl implements GetKategorieViewAction {

    private final KategorieViewRepository repository;
    private final AccessService accessService;

    public GetKategorieViewActionImpl(KategorieViewRepository repository, AccessService accessService) {
        this.repository = repository;
        this.accessService = accessService;
    }

    @Override
    public List<KategorieViewData> perform(WbsUser user, Long traegerId) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {
            return repository.findByTraegerAsData(traegerId);
        });
    }
}
