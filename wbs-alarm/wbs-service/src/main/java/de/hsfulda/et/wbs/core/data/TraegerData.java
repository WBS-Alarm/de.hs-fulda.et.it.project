package de.hsfulda.et.wbs.core.data;


import java.util.List;

public interface TraegerData {


    Long getId();
    String getName();
    List<ZielortData> getZielorte();
    List<KategorieData> getKategorien();
    List<BenutzerData> getBenutzer();

}
