package de.hsfulda.et.wbs.controller;

import de.hsfulda.et.wbs.controller.security.LoginController;
import de.hsfulda.et.wbs.controller.security.UsersController;

public enum Relations {

    REL_USER("user", UsersController.PATH_USER),
    REL_USER_LOGOUT("logout", UsersController.PATH_LOGOUT),
    REL_USER_CURRENT("current", UsersController.PATH_CURRENT),
    REL_USER_REGISTER("registerUser", UsersController.PATH_REGISTER),
    REL_USER_LOGIN("userLogin", LoginController.LOGIN_PATH);

    private final String rel;
    private final String href;

    Relations(String rel, String href) {
        this.rel = rel;
        this.href = href.substring(1);
    }

    public String getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }
}
