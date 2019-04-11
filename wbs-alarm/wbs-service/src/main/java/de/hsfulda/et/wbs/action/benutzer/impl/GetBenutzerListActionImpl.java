package de.hsfulda.et.wbs.action.benutzer.impl;

import de.hsfulda.et.wbs.action.benutzer.GetBenutzerListAction;
import de.hsfulda.et.wbs.core.WbsUser;
import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.data.BenutzerGrantedAuthData;
import de.hsfulda.et.wbs.repository.BenutzerRepository;
import de.hsfulda.et.wbs.security.repository.GrantedAuthorityRepository;
import de.hsfulda.et.wbs.service.AccessService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetBenutzerListActionImpl implements GetBenutzerListAction {

    private final BenutzerRepository repo;
    private final GrantedAuthorityRepository grantedAuthorityRepository;
    private final AccessService accessService;

    public GetBenutzerListActionImpl(BenutzerRepository repo, GrantedAuthorityRepository grantedAuthorityRepository, AccessService accessService) {
        this.repo = repo;
        this.grantedAuthorityRepository = grantedAuthorityRepository;
        this.accessService = accessService;
    }

    @Override
    public List<BenutzerGrantedAuthData> perform(WbsUser user, Long traegerId) {
        return accessService.hasAccessOnTraeger(user, traegerId, () -> {
            List<BenutzerData> benutzer = repo.findAllByTraegerId(traegerId);
            return benutzer.stream()
                    .map(b -> new BenutzerGrantedAuthDataImpl(b, grantedAuthorityRepository.findByUserId(b.getId())))
                    .collect(Collectors.toList());
        });
    }
}
