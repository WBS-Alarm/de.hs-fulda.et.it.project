package de.hsfulda.et.wbs.action.groesse.impl;

import de.hsfulda.et.wbs.action.groesse.GetGroesseAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.GroesseRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetGroesseActionImpl implements GetGroesseAction {

    private final GroesseRepository repo;
    private final AccessService accessService;

    public GetGroesseActionImpl(GroesseRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public GroesseData perform(WbsUser user, Long id) {
        return accessService.hasAccessOnGroesse(user, id, () -> {
            Optional<GroesseData> managed = repo.findByIdAndAktivIsTrue(id);
            return managed.orElseThrow(() -> new ResourceNotFoundException("Größe mit ID {0} nicht gefunden.", id));
        });
    }
}
