package de.flower.rmt.model;

import de.flower.common.validation.groups.IEmailUnique;
import de.flower.common.validation.groups.INameUnique;
import de.flower.common.validation.unique.Unique;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

/**
 * @author flowerrrr
 */
@Entity
@Table(name = "Users", uniqueConstraints = {
        @UniqueConstraint(name = "fullname", columnNames = {"fullname", "club_id"}),
        @UniqueConstraint(name = "email", columnNames = {"username"})
})
@Unique.List({
        @Unique(name = "fullname", clazz = User.class, groups = {INameUnique.class, Default.class}), // need group Unique to be able to restrict bean validation to this validator
        @Unique(name = "email", attributeNames = {"email"}, clazz = User.class, groups = {IEmailUnique.class, Default.class})
})
public class User extends AbstractClubRelatedEntity {

    @NotBlank
    @Size(max = 80)
    @Email
    // need to be  named 'username' to satisfy spring security
    @Column(name = "username")
    private String email;

    /**
     * Encrypted password.
     */
    @NotBlank
    @Size(max = 50)
    // need to be named 'password' to satisfy spring security
    @Column(name = "password")
    private String encryptedPassword;

    /**
     * Initial password set by system when user is created (or password is reset).
     * When the user changes his password this value is cleared.
     */
    @Size(max = 50)
    @Column
    private String initialPassword;

    @NotNull
    @Column
    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    private List<Role> roles = new ArrayList<Role>();

    @NotBlank
    @Size(max = 50)
    @Column
    private String fullname;

    @Column
    private Status status;

    /**
     * Flag indicating whether invitation mail has been sent.
     */
    @Column
    private Boolean invitationSent;

    /**
     * A user can be part of several teams.
     */
    @OneToMany(mappedBy = "user")
    private List<Player> players = new ArrayList<Player>();

    private User() {
    }

    public User(String email, String encryptedPassword, boolean enabled, String fullname, Club club) {
        super(club);
        this.email = email;
        this.encryptedPassword = encryptedPassword;
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

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(final String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
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

    public String getInitialPassword() {
        return initialPassword;
    }

    public void setInitialPassword(final String initialPassword) {
        this.initialPassword = initialPassword;
    }

    public boolean isInvitationSent() {
        return invitationSent;
    }

    public void setInvitationSent(final boolean invitationSent) {
        this.invitationSent = invitationSent;
    }

    public boolean mustChangePassword() {
        return initialPassword != null;
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
