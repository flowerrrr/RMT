package de.flower.rmt.model;

import de.flower.common.validation.unique.Unique;
import org.hibernate.annotations.Index;
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
        @Unique(name = "fullname", clazz = User.class,
                message = User.Validation.nameNotUniqueMessage,
                groups = {User.Validation.INameUnique.class, Default.class}), // need group Unique to be able to restrict bean validation to this validator
        @Unique(name = "email", attributeNames = {"email"}, clazz = User.class,
                message = User.Validation.emailNotUniqueMessage,
                groups = {User.Validation.IEmailUnique.class, Default.class})
})
public class User extends AbstractClubRelatedEntity {

    public static class Validation {

        public interface INameUnique {

        }

        public interface IEmailUnique {

        }

        public final static String nameNotUniqueMessage = "{validation.unique.name}";

        public final static String emailNotUniqueMessage = "{validation.unique.email}";
    }

    @NotBlank
    @Size(max = 80)
    @Email
    // need to be  named 'username' to satisfy spring security
    @Column(name = "username")
    @Index(name = "ix_username")
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
    @Index(name = "ix_enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    private List<Role> roles = new ArrayList<Role>();

    @NotBlank
    @Size(max = 50)
    @Column
    private String fullname;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    @Index(name = "ix_status")
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public boolean hasInitialPassword() {
        return initialPassword != null;
    }

    public boolean isInvitationSent() {
        return invitationSent != null && invitationSent;
    }

    public void setInvitationSent(final boolean invitationSent) {
        this.invitationSent = invitationSent;
    }

    public boolean mustChangePassword() {
        return initialPassword != null;
    }

       public boolean isManager() {
        return hasRole(Role.Roles.MANAGER.getRoleName());
    }

    public boolean hasRole(String roleName) {
        return findRole(roleName) != null;
    }


    public  Role findRole(String roleName) {
        for (Role r : getRoles()) {
            if (r.getAuthority().equals(roleName)) {
                return r;
            }
        }
        return null;
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
