package de.hsfulda.et.wbs.action;

import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.core.data.TraegerDto;

public interface UpdateTraegerAction {

    TraegerData perform(Long id, TraegerDto traeger);
}
