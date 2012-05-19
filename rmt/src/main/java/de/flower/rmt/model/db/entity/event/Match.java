package de.flower.rmt.model.db.entity.event;

import de.flower.rmt.model.db.entity.Club;
import de.flower.rmt.model.db.entity.Opponent;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * @author flowerrrr
 */
@Entity
@DiscriminatorValue("Match")
public class Match extends AbstractSoccerEvent {

    /**
     * Can be null. Sometimes events are created without knowing who the opponent will be.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Opponent opponent;

    protected Match() {
    }

    public Match(final Club club) {
        super(club);
    }

    public Opponent getOpponent() {
        return opponent;
    }

    public void setOpponent(final Opponent opponent) {
        this.opponent = opponent;
    }

}
