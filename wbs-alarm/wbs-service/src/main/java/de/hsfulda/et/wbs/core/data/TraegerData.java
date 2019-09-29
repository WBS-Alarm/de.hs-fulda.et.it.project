package de.hsfulda.et.wbs.core.data;


import de.hsfulda.et.wbs.core.dto.TraegerDto;

import java.util.List;

public interface TraegerData extends TraegerDto {

    Long getId();
    List<ZielortData> getZielorte();
    List<KategorieData> getKategorien();
    List<BenutzerData> getBenutzer();

}
