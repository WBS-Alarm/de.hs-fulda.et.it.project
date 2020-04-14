package de.hsfulda.et.wbs.action.report.impl;

import de.hsfulda.et.wbs.action.report.GetBestandViewAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandViewData;
import de.hsfulda.et.wbs.repository.BestandViewRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetBestandViewActionImpl implements GetBestandViewAction {

    private final BestandViewRepository repository;
    private final AccessService accessService;

    public GetBestandViewActionImpl(BestandViewRepository repository, AccessService accessService) {
        this.repository = repository;
        this.accessService = accessService;
    }

    @Override
    public List<BestandViewData> perform(WbsUser user, Long traegerId) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {
            return repository.findByTraegerAsData(traegerId);
        });
    }
}
