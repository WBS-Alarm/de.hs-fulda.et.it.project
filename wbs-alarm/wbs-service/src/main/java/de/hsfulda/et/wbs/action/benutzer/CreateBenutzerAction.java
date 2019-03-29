package de.hsfulda.et.wbs.action.benutzer;

import de.hsfulda.et.wbs.core.data.BenutzerCreateDto;
import de.hsfulda.et.wbs.core.data.BenutzerData;

public interface CreateBenutzerAction {

    BenutzerData perform(Long traegerId, BenutzerCreateDto benutzer);
}
