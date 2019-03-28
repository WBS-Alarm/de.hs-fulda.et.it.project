package de.hsfulda.et.wbs.action;

import de.hsfulda.et.wbs.core.data.TraegerData;

public interface GetTraegerAction {

    TraegerData perform(Long id);
}
