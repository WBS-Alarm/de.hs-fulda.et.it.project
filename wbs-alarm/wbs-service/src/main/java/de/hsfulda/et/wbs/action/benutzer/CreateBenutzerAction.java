package de.hsfulda.et.wbs.action.benutzer;

import de.hsfulda.et.wbs.core.data.BenutzerData;
import de.hsfulda.et.wbs.core.dto.BenutzerCreateDto;

public interface CreateBenutzerAction {

    BenutzerData perform(Long traegerId, BenutzerCreateDto benutzer);
}
