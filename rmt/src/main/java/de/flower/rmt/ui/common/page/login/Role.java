package de.flower.rmt.ui.common.page.login;

/**
 * @author oblume
 */
public enum Role {

    PLAYER("ROLE_PLAYER"),
    MANAGER("ROLE_MANAGER"),
    ADMIN("ROLE_ADMIN");

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
