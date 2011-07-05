package de.flower.rmt.model;

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
