package de.hsfulda.et.wbs.action.groesse.impl;

import de.hsfulda.et.wbs.action.groesse.UpdateGroesseAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.GroesseData;
import de.hsfulda.et.wbs.core.dto.GroesseDto;
import de.hsfulda.et.wbs.core.exception.ResourceNotFoundException;
import de.hsfulda.et.wbs.repository.GroesseRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static org.springframework.util.StringUtils.isEmpty;

@Transactional
@Component
public class UpdateGroesseActionImpl implements UpdateGroesseAction {

    private final GroesseRepository repo;
    private final AccessService accessService;

    public UpdateGroesseActionImpl(GroesseRepository repo, AccessService accessService) {
        this.repo = repo;
        this.accessService = accessService;
    }

    @Override
    public GroesseData perform(WbsUser user, Long id, GroesseDto groesse) {
        return accessService.hasAccessOnGroesse(user, id, () -> {
            if (isEmpty(groesse.getName())) {
                throw new IllegalArgumentException("Name des Größe muss angegeben werden");
            }

            if (!repo.existsById(id)) {
                throw new ResourceNotFoundException("Größe mit der ID {0} nicht gefunden.", id);
            }

            repo.updateName(id, groesse.getName());
            return repo.findById(id)
                    .get();
        });
    }
}
