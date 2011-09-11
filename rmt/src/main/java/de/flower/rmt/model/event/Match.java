package de.flower.rmt.model.event;

import de.flower.rmt.model.Team;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author oblume
 */
@Entity
@DiscriminatorValue("Match")
public class Match extends Event {

    @ManyToOne
    // TODO: check if @OneToMany might be better
    private Team opponent;

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime")
    private LocalTime kickOff;


//    @ManyToOne
//    private Jersey jersey;

    public Match() {
    }

    public Match(Team team) {
        super(team);
    }
}