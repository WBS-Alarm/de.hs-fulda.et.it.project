package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.transaktion.AddTransaktionAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.core.dto.TransaktionDto;
import de.hsfulda.et.wbs.entity.Transaktion;
import de.hsfulda.et.wbs.repository.TransaktionRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class AddTransaktionActionImpl implements AddTransaktionAction {

    private final TransaktionValidaton validation;
    private final TransaktionExecution execution;
    private final TransaktionRepository transaktionen;
    private final TransaktionMailService transaktionMailService;
    private final AccessService accessService;

    public AddTransaktionActionImpl(TransaktionValidaton validation, TransaktionExecution execution,
            TransaktionRepository transaktionen, TransaktionMailService transaktionMailService,
            AccessService accessService) {
        this.validation = validation;
        this.execution = execution;
        this.transaktionen = transaktionen;
        this.transaktionMailService = transaktionMailService;
        this.accessService = accessService;
    }

    @Override
    public TransaktionData perform(WbsUser user, TransaktionDto dto) {
        return accessService.hasAccessOnTransaktion(user, dto, () -> {

            validation.validateTransaktionDto(dto);
            Transaktion transaktion = execution.createTransaktion(user, dto);
            transaktionMailService.sendMail(user, dto);
            return transaktionen.save(transaktion);
        });
    }
}
