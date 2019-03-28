package de.hsfulda.et.wbs.action;

import de.hsfulda.et.wbs.core.data.TraegerData;
import de.hsfulda.et.wbs.core.data.TraegerDto;

public interface CreateTraegerAction {

    TraegerData perform(TraegerDto traeger);
}
