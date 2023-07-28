package de.flower.rmt.model.db.entity;

import de.flower.common.model.db.entity.AbstractBaseEntity;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "authority"}))
public class Role extends AbstractBaseEntity {

    public enum Roles {

        PLAYER("ROLE_PLAYER"),
        MANAGER("ROLE_MANAGER"),
        ADMIN("ROLE_ADMIN");

        private String roleName;

        Roles(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleName() {
            return roleName;
        }

        public static Roles fromRoleName(String roleName) {
            for (Roles r : values()) {
                if (r.getRoleName().equals(roleName)) {
                    return r;
                }
            }
            throw new IllegalArgumentException("Unknown roleName [" + roleName + "].");
        }
    }

    @NotNull
    @Column
    @Index(name = "ix_authority")
    private String authority;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Index(name = "ix_user")
    private User user;

    protected Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
