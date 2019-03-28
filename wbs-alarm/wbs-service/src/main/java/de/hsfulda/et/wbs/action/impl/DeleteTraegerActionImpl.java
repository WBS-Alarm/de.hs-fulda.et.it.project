package de.hsfulda.et.wbs.action.impl;

import de.hsfulda.et.wbs.action.DeleteTraegerAction;
import de.hsfulda.et.wbs.core.ResourceNotFoundException;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Component
public class DeleteTraegerActionImpl implements DeleteTraegerAction {

    private final TraegerRepository repo;

    public DeleteTraegerActionImpl(TraegerRepository repo) {
        this.repo = repo;
    }

    @Override
    public void perform(Long id) {
        Optional<TraegerData> managed = repo.findByIdAsData(id);

        if (!managed.isPresent()) {
            throw new ResourceNotFoundException();
        }

        repo.deleteById(id);
    }
}
