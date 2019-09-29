package de.hsfulda.et.wbs.action.transaktion.impl;

import de.hsfulda.et.wbs.action.transaktion.GetTransaktionAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TransaktionData;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.TransaktionRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetTransaktionActionImpl implements GetTransaktionAction {

    private final TransaktionRepository repo;
    private final AccessService accessService;

    public GetTransaktionActionImpl(TransaktionRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public TransaktionData perform(WbsUser user, Long id) {
        return accessService.hasAccessOnTransaktion(user, id, () -> {
            Optional<TransaktionData> managed = repo.findByIdAsData(id);
            return managed.orElseThrow(() -> new ResourceNotFoundException("Transaktion mit ID {0} nicht gefunden.", id));
        });
    }
}
