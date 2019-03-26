package de.hsfulda.et.wbs.core.data;

public interface TokenConfigData {

    String getIssuer();

    int getExpiration();

    int getClock();

    String getSecret();
}
