package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.transaktion.AddTransaktionAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class AddTransaktionActionImpl implements AddTransaktionAction {

    private final TransaktionValidaton validation;
    private final TransaktionExecution execution;
    private final TransaktionDao transaktionDao;
    private final TransaktionMailService transaktionMailService;

    public AddTransaktionActionImpl(TransaktionValidaton validation, TransaktionExecution execution,
            TransaktionMailService transaktionMailService, TransaktionDao transaktionDao) {
        this.validation = validation;
        this.execution = execution;
        this.transaktionMailService = transaktionMailService;
        this.transaktionDao = transaktionDao;
    }

    @Override
    public TransaktionData perform(WbsUser user, TransaktionDto dto) {
        return transaktionDao.hasAccessOnTransaktion(user, dto, () -> {

            validation.validateTransaktionDto(dto);
            TransaktionData transaktion = execution.createTransaktion(user, dto);
            transaktionMailService.sendMail(user, dto);
            return transaktion;
        });
    }
}
