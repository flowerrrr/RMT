package de.flower.rmt.model.event;

import de.flower.rmt.model.Team;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author oblume
 */
@Entity
@DiscriminatorValue("Tournament")
public class Tournament extends Event {

//    @ManyToOne
//    private Jersey jersey;

    public Tournament() {
    }

    public Tournament(Team team) {
        super(team);
    }
}
