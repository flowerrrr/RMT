package de.flower.rmt.model;

import de.flower.common.model.AbstractBaseEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author flowerrrr
 */
@Entity
public class Club extends AbstractBaseEntity {

    @Column
    @NotBlank
    @Size(max = 50)
    private String name;

    @OneToMany(mappedBy = "club")
    private List<Team> teams;

    private Club() {
    }

    public Club(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
