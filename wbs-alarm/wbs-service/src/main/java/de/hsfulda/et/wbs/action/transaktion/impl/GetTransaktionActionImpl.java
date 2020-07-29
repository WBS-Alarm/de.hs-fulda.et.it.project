package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.transaktion.GetTransaktionAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetTransaktionActionImpl implements GetTransaktionAction {

    private final TransaktionDao transaktionDao;

    public GetTransaktionActionImpl(TransaktionDao transaktionDao) {
        this.transaktionDao = transaktionDao;
    }

    @Override
    public TransaktionData perform(WbsUser user, Long id) {
        return transaktionDao.hasAccessOnTransaktion(user, id, () -> {
            Optional<TransaktionData> managed = transaktionDao.getTransaktionData(id);
            return managed.orElseThrow(
                    () -> new ResourceNotFoundException("Transaktion mit ID {0} nicht gefunden.", id));
        });
    }
}
