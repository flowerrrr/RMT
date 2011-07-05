package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *   create table authorities (
      username varchar_ignorecase(50) not null,
      authority varchar_ignorecase(50) not null,
      constraint fk_authorities_users foreign key(username) references users(username));
      create unique index ix_auth_username on authorities (username,authority);
 *
 * @author oblume
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username", "authority"}))
public class Authorities extends AbstractBaseEntity {

    @NotNull
    @Column
    private String authority;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Users user;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
