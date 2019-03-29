package de.hsfulda.et.wbs.action;

public interface DeleteGrantedAuthorityAction {

    void perform(Long authorityId, Long benutzerId);
}
