package de.hsfulda.et.wbs.action.benutzer.impl;

import de.hsfulda.et.wbs.action.benutzer.GetBenutzerAction;
import de.hsfulda.et.wbs.core.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetBenutzerActionImpl implements GetBenutzerAction {

    private final BenutzerRepository repo;
    private final AccessService accessService;

    public GetBenutzerActionImpl(
            BenutzerRepository repo,
            AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public BenutzerData perform(WbsUser user, Long id) {
        return accessService.hasAccessOnBenutzer(user, id, () -> {
            Optional<BenutzerData> benutzer = repo.findByIdAsData(id);

            if (!benutzer.isPresent()) {
                throw new ResourceNotFoundException();
            }

            return benutzer.get();
        });
    }
}
