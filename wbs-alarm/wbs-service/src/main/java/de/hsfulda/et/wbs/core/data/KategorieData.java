package de.hsfulda.et.wbs.core.data;


import java.util.List;

public interface KategorieData {

    Long getId();

    String getName();

    boolean isAktiv();

    TraegerData getTraeger();

    List<GroesseData> getGroessen();

}
