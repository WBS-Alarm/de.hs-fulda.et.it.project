package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.transaktion.AddEinkaufAction;
import de.hsfulda.et.wbs.action.transaktion.AddTransaktionAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.core.dto.PositionDto;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Component
public class AddEinkaufActionImpl implements AddEinkaufAction {

    private final TransaktionDao transaktionDao;
    private final AddTransaktionAction addAction;

    public AddEinkaufActionImpl(TransaktionDao transaktionDao, AddTransaktionAction addAction) {
        this.transaktionDao = transaktionDao;
        this.addAction = addAction;
    }

    @Override
    public TransaktionData perform(WbsUser user, Long tragerId, List<PositionDto> dto) {
        return transaktionDao.hasAccessOnTraeger(user, tragerId, () -> {
            Long wareneingangId = transaktionDao.findWareneingangByTraegerId(tragerId)
                    .orElseThrow(() -> new IllegalStateException(
                            "Fehler beim Bezug des Wareneingangs. Bitte wenden Sie sich an Ihren Systemadministrator."));

            Long lagerId = transaktionDao.findLagerByTraegerId(tragerId)
                    .orElseThrow(() -> new IllegalStateException(
                            "Fehler beim Bezug des Lagers. Bitte wenden Sie sich an Ihren Systemadministrator."));

            return addAction.perform(user, TransaktionDtoImpl.of(wareneingangId, lagerId, dto));
        });
    }
}
