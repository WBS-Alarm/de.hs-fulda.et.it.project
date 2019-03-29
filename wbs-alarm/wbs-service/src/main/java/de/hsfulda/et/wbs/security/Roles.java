package de.hsfulda.et.wbs.security;

import de.hsfulda.et.wbs.core.data.GrantedAuthorityData;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public enum Roles {

    ADMIN(new String[]{Auth.ADMIN}, RoleName.ADMIN),
    TR_MAN(new String[]{Auth.ADMIN, Auth.CONTROL}, RoleName.TRAEGER_MANAGER),
    RE_ALL(new String[]{Auth.ADMIN, Auth.CONTROL, Auth.MANAGE, Auth.WRITE, Auth.READ}, RoleName.READ_ALL);

    public static final String TRMAN = "TRAEGER_MANAGER";
    public static final String REALL = "READ_ALL";

    private final List<String> roles = new ArrayList<>();
    private final String roleName;

    Roles(String[] roles, String roleName) {
        this.roleName = roleName;
        this.roles.addAll(Arrays.asList(roles));
    }

    public boolean contains(String auth) {
        return roles.contains(auth);
    }

    public static Set<SimpleGrantedAuthority> getRoles(Collection<GrantedAuthorityData> authorities) {
        final Set<SimpleGrantedAuthority> rollen = new HashSet<>();
        for (Roles role : values()) {
            for (GrantedAuthorityData authority : authorities) {
                if (role.contains(authority.getGroup().getCode())) {
                    rollen.add(new SimpleGrantedAuthority(role.roleName));
                }
            }
        }
        return rollen;
    }

    public interface RoleName {
        String ADMIN = "ADMIN";
        String TRAEGER_MANAGER = "TRAEGER_MANAGER";
        String READ_ALL = "READ_ALL";
    }

    private interface Auth {
        String ADMIN = "ADMIN";
        String CONTROL = "CONTROL";
        String MANAGE = "MANAGE";
        String WRITE = "WRITE";
        String READ = "READ";
    }
}
