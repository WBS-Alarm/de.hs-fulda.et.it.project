package de.hsfulda.et.wbs.action.report.impl;

import de.hsfulda.et.wbs.action.report.GetZielortViewAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.ZielortViewData;
import de.hsfulda.et.wbs.repository.ZielortViewRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetZielortViewActionImpl implements GetZielortViewAction {

    private final ZielortViewRepository repository;
    private final AccessService accessService;

    public GetZielortViewActionImpl(ZielortViewRepository repository, AccessService accessService) {
        this.repository = repository;
        this.accessService = accessService;
    }

    @Override
    public List<ZielortViewData> perform(WbsUser user, Long traegerId) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {
            return repository.findByTraegerAsData(traegerId);
        });
    }
}
