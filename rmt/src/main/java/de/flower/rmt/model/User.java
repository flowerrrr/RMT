package de.flower.rmt.model;

import de.flower.common.validation.unique.Unique;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(name = "fullname", columnNames = {"fullname", "club_id"}))
@Unique(groups = { Unique.class, Default.class }) // need group Unique to be able to restrict bean validation to this validator
public class User extends AbstractClubRelatedEntity {

    @NotBlank
    @Email
    // need to be  named username to satisfy spring security
    @Column(unique = true, name = "username")
    private String email;

    // @NotBlank
    @Column
    private String password;

    @NotNull
    @Column
    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE })
    private List<Role> roles = new ArrayList<Role>();

    @NotBlank
    @Column
    private String fullname;

    @Column
    private Status status;

    /**
     * A user can be part of several teams.
     */
    @OneToMany(mappedBy = "user")
    private List<Player> players = new ArrayList<Player>();

    private User() {}

    public User(String email, String password, boolean enabled, String fullname, Club club) {
        super(club);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.fullname = fullname;
    }

    public User(Club club) {
        super(club);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return getEmail();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> authorities) {
        this.roles = authorities;
    }

    public boolean isManager() {
        return hasRole(Role.Roles.MANAGER.getRoleName());
    }

    public boolean hasRole(String role) {
        for (Role r : roles) {
            if (r.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public Status getStatus() {
        return (status == null) ? Status.UNKNOWN : status;
    }

    public void setStatus(Status status) {
        this.status = (status == null) ? Status.UNKNOWN : status;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public enum Status {
        UNKNOWN,
        FIT,
        INJURED;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}
