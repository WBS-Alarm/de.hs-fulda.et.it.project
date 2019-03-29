package de.hsfulda.et.wbs.core.data;


import java.util.List;

public interface TraegerData extends TraegerDto {

    Long getId();
    List<ZielortData> getZielorte();
    List<KategorieData> getKategorien();
    List<BenutzerData> getBenutzer();

}
