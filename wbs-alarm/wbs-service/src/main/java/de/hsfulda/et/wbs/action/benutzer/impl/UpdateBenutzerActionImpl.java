package de.hsfulda.et.wbs.action.benutzer.impl;

import de.hsfulda.et.wbs.action.benutzer.UpdateBenutzerAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.data.BenutzerDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class UpdateBenutzerActionImpl implements UpdateBenutzerAction {

    private final BenutzerRepository repo;
    private final AccessService accessService;

    public UpdateBenutzerActionImpl(
            BenutzerRepository repo,
            AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public BenutzerData perform(WbsUser user, Long id, BenutzerDto benutzer) {
        return accessService.hasAccessOnBenutzer(user, id, () -> {
            if (!repo.existsById(id)) {
                throw new ResourceNotFoundException();
            }

            repo.updateEinkaeuferAndMail(id, benutzer.getEinkaeufer(), benutzer.getMail());
            return repo.findByIdAsData(id).get();
        });
    }
}
