package de.hsfulda.et.wbs.action.traeger.impl;

import de.hsfulda.et.wbs.action.traeger.GetTraegerListAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.repository.TraegerRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class GetTraegerListActionImpl implements GetTraegerListAction {

    private final TraegerRepository repo;

    public GetTraegerListActionImpl(TraegerRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<TraegerData> perform(WbsUser user) {
        if (user.isAdmin()) {
            return repo.findAllAsData();
        }
        return Collections.singletonList(user.getBenutzer()
                .getTraeger());
    }
}
