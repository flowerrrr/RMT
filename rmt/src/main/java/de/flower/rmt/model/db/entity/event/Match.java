package de.flower.rmt.model.db.entity.event;

import de.flower.rmt.model.db.entity.Club;
import de.flower.rmt.model.db.entity.Opponent;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("Match")
public class Match extends AbstractSoccerEvent {

    protected Match() {
    }

    public Match(final Club club) {
        super(club);
    }

    public Opponent getOpponent() {
        return super._getOpponent();
    }

    public void setOpponent(final Opponent opponent) {
        super._setOpponent(opponent);
    }

}
