package de.flower.rmt.model.event;

import de.flower.rmt.model.Team;
import de.flower.rmt.model.event.Event;

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

    private Tournament() {
    }

    public Tournament(Team team) {
        super(team);
    }
}
