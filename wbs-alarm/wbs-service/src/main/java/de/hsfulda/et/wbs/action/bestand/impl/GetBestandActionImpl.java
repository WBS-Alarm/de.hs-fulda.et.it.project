package de.hsfulda.et.wbs.action.bestand.impl;

import de.hsfulda.et.wbs.action.bestand.GetBestandAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BestandData;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.BestandRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

@Component
public class GetBestandActionImpl implements GetBestandAction {

    private final BestandRepository repo;
    private final AccessService accessService;

    public GetBestandActionImpl(BestandRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public BestandData perform(WbsUser user, Long id) {
        return accessService.hasAccessOnBestand(user, id, () -> {
            return repo.findByIdAsData(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Bestand mit ID {0} nicht gefunden.", id));
        });
    }
}
