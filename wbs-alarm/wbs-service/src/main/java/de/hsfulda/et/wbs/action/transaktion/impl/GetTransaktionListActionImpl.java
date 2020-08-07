package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.transaktion.GetTransaktionListAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class GetTransaktionListActionImpl implements GetTransaktionListAction {

    private final TransaktionDao transaktionDao;

    public GetTransaktionListActionImpl(TransaktionDao transaktionDao) {
        this.transaktionDao = transaktionDao;
    }

    @Override
    public Page<TransaktionData> perform(WbsUser user, Long traegerId, int page, int size) {
        return transaktionDao.hasAccessOnTraeger(user, traegerId, () -> {
            return transaktionDao.getTransaktionPageByTraegerId(traegerId, PageRequest.of(page, size));
        });
    }
}
