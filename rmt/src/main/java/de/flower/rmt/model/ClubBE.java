package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author oblume
 */
@Entity
public class ClubBE extends AbstractBaseEntity {

    @Column
    private String name;

    @OneToMany
    private Set<TeamBE> teams;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TeamBE> getTeams() {
        return teams;
    }

    public void setTeams(Set<TeamBE> teams) {
        this.teams = teams;
    }
}
