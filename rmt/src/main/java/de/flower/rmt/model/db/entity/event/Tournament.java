package de.flower.rmt.model.db.entity.event;

import de.flower.rmt.model.db.entity.Club;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author flowerrrr
 */
@Entity
@DiscriminatorValue("Tournament")
public class Tournament extends AbstractSoccerEvent {

    protected Tournament() {
    }

    public Tournament(final Club club) {
        super(club);
    }

}
