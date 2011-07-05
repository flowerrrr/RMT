package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;
import de.flower.common.validation.unique.Unique;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * @author oblume
 */
@Entity
@Unique
public class Users extends AbstractBaseEntity {

    /**
     * Spring security related fields. Do not rename or remove.
     * The user's email address is used as the username.
     */

    @NotBlank
    @Email
    @Column(unique = true)
    private String username;

    @NotBlank
    @Column
    private String password;

    @NotNull
    @Column
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<Authorities> authorities;

    /**
     * All other fields.
     */

    @NotBlank
    @Column
    private String fullname;


    @NotNull
    @ManyToOne
    private Club club;

    public Users() {
    }

    public Users(String username, String password, boolean enabled, String fullname) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.fullname = fullname;
    }

    public Users(Club club) {
        this.club = club;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    private String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return getUsername();
    }

    public void setEmail(String email) {
        setUsername(email);
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

    public List<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authorities> authorities) {
        this.authorities = authorities;
    }

    public boolean isManager() {
        return hasRole(Role.MANAGER.getRoleName());
    }

    public boolean hasRole(String role) {
        for (Authorities authority : authorities) {
            if (authority.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public enum Collections {
        Authorities;
    }

}
