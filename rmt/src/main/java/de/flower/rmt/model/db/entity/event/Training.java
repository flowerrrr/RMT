package de.flower.rmt.model.db.entity.event;

import de.flower.rmt.model.db.entity.Club;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("Training")
public class Training extends AbstractSoccerEvent {

    protected Training() {
    }

    public Training(final Club club) {
        super(club);
    }
}
