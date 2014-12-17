package de.flower.rmt.model.db.entity;

import de.flower.common.validation.unique.Unique;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import javax.mail.internet.InternetAddress;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author flowerrrr
 */
@Entity
@Table(name = "users", uniqueConstraints = {
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


    public interface Validation {

        public interface INameUnique {

        }

        public interface IEmailUnique {

        }

        public final static String nameNotUniqueMessage = "{validation.unique.name}";

        public final static String emailNotUniqueMessage = "{validation.unique.email}";
    }

    @NotBlank(message = "{validation.notblank.email}")
    @Size(max = 80)
    @Email(message = "{validation.email.valid}")
    // need to be  named 'username' to satisfy spring security
    @Column(name = "username")
    @Index(name = "ix_username")
    private String email;

    /**
     * On popular demand users can add second email address for home and work-emails.
     */
    @Size(max = 80)
    @Email(message = "{validation.email.valid}")
    private String secondEmail;

    @Size(max = 40)
    private String phoneNumber;

    /**
     * Encrypted password.
     */
    @NotBlank(message = "{validation.notblank.password}")
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

    /**
     * This is a set to overcome http://blog.eyallupu.com/2010/06/hibernate-exception-simultaneously.html when
     * fetching User_.role and User_.player simultaneously.
     */
    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE})
    private Set<Role> roles = new HashSet<>();

    @NotBlank(message = "{validation.notblank.name}")
    @Size(max = 50)
    @Column
    @Index(name = "ix_fullname")
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

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private DateTime lastLogin;

    /**
     * A user can be part of several teams.
     */
    @OneToMany(mappedBy = "user")
    private List<Player> players = new ArrayList<Player>();

    public User() {
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

    public String getSecondEmail() {
        return secondEmail;
    }

    public void setSecondEmail(final String secondEmail) {
        this.secondEmail = secondEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Set<Role> getRoles() {
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

    public DateTime getLastLogin() {
        return lastLogin;
    }

    public Date getLastLoginAsDate() {
        return lastLogin == null ? null : lastLogin.toDate();
    }

    public void setLastLogin(final DateTime lastLogin) {
        this.lastLogin = lastLogin;
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

    public Role findRole(String roleName) {
        for (Role r : getRoles()) {
            if (r.getAuthority().equals(roleName)) {
                return r;
            }
        }
        return null;
    }

    public String[] getEmails() {
        if (secondEmail == null) {
            return new String[]{email};
        } else {
            return new String[]{email, secondEmail};
        }
    }

    public InternetAddress getInternetAddress() {
        return getInternetAddresses()[0];
    }

    public InternetAddress[] getInternetAddresses() {
        if (secondEmail == null) {
            return new InternetAddress[]{newInternetAddress(email, fullname)};
        } else {
            return new InternetAddress[]{newInternetAddress(email, fullname), newInternetAddress(secondEmail, fullname)};
        }
    }

    public static InternetAddress newInternetAddress(String email, String name) {
        try {
            return new InternetAddress(email, name);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
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
                ", email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}
