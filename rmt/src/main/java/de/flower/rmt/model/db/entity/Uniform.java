package de.flower.rmt.model.db.entity;

import de.flower.common.model.db.entity.AbstractBaseEntity;
import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author flowerrrr
 */
@Entity
public class Uniform extends AbstractBaseEntity {

    @Column
    @NotBlank
    @Size(max = 50)
    private String name;

    @Column
    @NotBlank
    @Size(max = 50)
    private String shirt;

    @Column
    @NotBlank
    @Size(max = 50)
    private String shorts;

    @Column
    @NotBlank
    @Size(max = 50)
    private String socks;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Index(name = "ix_team")
    private Team team;

    protected Uniform() {
    }

    public Uniform(final Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getShirt() {
        return shirt;
    }

    public void setShirt(final String shirt) {
        this.shirt = shirt;
    }

    public String getShorts() {
        return shorts;
    }

    public void setShorts(final String shorts) {
        this.shorts = shorts;
    }

    public String getSocks() {
        return socks;
    }

    public void setSocks(final String socks) {
        this.socks = socks;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(final Team team) {
        this.team = team;
    }
}
