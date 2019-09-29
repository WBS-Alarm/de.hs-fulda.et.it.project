package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.transaktion.GetTransaktionListAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetTransaktionListActionImpl implements GetTransaktionListAction {

    private final AccessService accessService;

    public GetTransaktionListActionImpl(AccessService accessService) {
        this.accessService = accessService;
    }

    @Override
    public List<TransaktionData> perform(WbsUser user) {
        return new ArrayList<>();
    }
}
