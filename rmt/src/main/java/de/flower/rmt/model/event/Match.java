package de.flower.rmt.model.event;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Jersey;
import de.flower.rmt.model.Opponent;
import de.flower.rmt.model.Team;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import javax.persistence.*;

/**
 * @author flowerrrr
 */
@Entity
@DiscriminatorValue("Match")
public class Match extends Event {

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime") /* must be same as in Training! */
    private LocalTime kickOff;

    /**
     * Can be null. Sometimes events are created without knowing who the opponent will be.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Opponent opponent;

    /**
     * Can be null.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Jersey jersey;

    protected Match() {
    }

    public Match(Team team) {
        super(team);
    }

    public Match(final Club club) {
        super(club);
    }

    public LocalTime getKickOff() {
        return kickOff;
    }

    public void setKickOff(final LocalTime kickOff) {
        this.kickOff = kickOff;
    }

    public Opponent getOpponent() {
        return opponent;
    }

    public void setOpponent(final Opponent opponent) {
        this.opponent = opponent;
    }

    public Jersey getJersey() {
        return jersey;
    }

    public void setJersey(final Jersey jersey) {
        this.jersey = jersey;
    }
}
