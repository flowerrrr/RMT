package de.flower.rmt.model.event;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Jersey;
import de.flower.rmt.model.Team;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * @author flowerrrr
 */
@Entity
@DiscriminatorValue("Tournament")
public class Tournament extends Event {

    /**
     * Can be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Jersey jersey;

    protected Tournament() {
    }

    public Tournament(Team team) {
        super(team);
    }

    public Tournament(final Club club) {
        super(club);
    }
}
