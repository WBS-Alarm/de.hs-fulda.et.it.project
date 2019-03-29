package de.hsfulda.et.wbs.action;

public interface GrantAuthorityAction {

    void perform(Long authorityId, Long benutzerId);
}
