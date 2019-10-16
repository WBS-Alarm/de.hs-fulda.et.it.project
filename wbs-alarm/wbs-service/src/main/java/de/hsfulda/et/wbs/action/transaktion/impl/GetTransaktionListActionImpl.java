package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.transaktion.GetTransaktionListAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.repository.TransaktionRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetTransaktionListActionImpl implements GetTransaktionListAction {

    private final TransaktionRepository transaktionen;
    private final AccessService accessService;

    public GetTransaktionListActionImpl(TransaktionRepository transaktionen, AccessService accessService) {
        this.transaktionen = transaktionen;
        this.accessService = accessService;
    }

    @Override
    public List<TransaktionData> perform(WbsUser user, Long traegerId) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {
            return transaktionen.findAllAsDataByTraegerId(traegerId);
        });
    }
}
