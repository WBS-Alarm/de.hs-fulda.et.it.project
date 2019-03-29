package de.hsfulda.et.wbs.action.traeger;

import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.core.data.TraegerDto;

public interface CreateTraegerAction {

    TraegerData perform(TraegerDto traeger);
}
