package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**

 * @author flowerrrr
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "authority"}))
public class Role extends AbstractBaseEntity {

    /**
     * @author flowerrrr
     */
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
    }

    @NotNull
    @Column
    private String authority;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Role() {
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
