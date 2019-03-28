package de.hsfulda.et.wbs.action.traeger.impl;

import de.hsfulda.et.wbs.action.traeger.UpdateTraegerAction;
import de.hsfulda.et.wbs.core.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.core.data.TraegerDto;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Transactional
@Component
public class UpdateTraegerActionImpl implements UpdateTraegerAction {

    private final TraegerRepository repo;

    public UpdateTraegerActionImpl(TraegerRepository repo) {
        this.repo = repo;
    }

    @Override
    public TraegerData perform(Long id, TraegerDto traeger) {
        if (isEmpty(traeger.getName())) {
            throw new IllegalArgumentException("Name des Trägers muss angegeben werden.");
        }

        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException();
        }

        repo.updateName(id, traeger.getName());
        return repo.findByIdAsData(id).get();
    }
}
