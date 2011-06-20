package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author oblume
 */
@Entity
public class Users extends AbstractBaseEntity {

    /**
     * Spring security related fields. Do not rename or remove.
     */

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Column
    private String password;

    @NotNull
    @Column
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private Set<Authorities> authorities;

    /**
     * All other fields.
     */

    @Email
    @NotNull
    @Column
    private String email;

    @ManyToOne
    private Club club;

    public Users(String username, String password, boolean enabled, String email) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.email = email;
    }
}
