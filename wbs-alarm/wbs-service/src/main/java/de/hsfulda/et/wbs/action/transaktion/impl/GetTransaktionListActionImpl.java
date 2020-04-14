package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.transaktion.GetTransaktionListAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.repository.TransaktionRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class GetTransaktionListActionImpl implements GetTransaktionListAction {

    private final TransaktionRepository transaktionen;
    private final AccessService accessService;

    public GetTransaktionListActionImpl(TransaktionRepository transaktionen, AccessService accessService) {
        this.transaktionen = transaktionen;
        this.accessService = accessService;
    }

    @Override
    public Page<TransaktionData> perform(WbsUser user, Long traegerId, int page, int size) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {
            return transaktionen.findAllAsDataByTraegerId(traegerId, PageRequest.of(page, size));
        });
    }
}
