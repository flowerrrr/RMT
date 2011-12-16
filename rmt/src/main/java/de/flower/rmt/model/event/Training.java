package de.flower.rmt.model.event;

import de.flower.rmt.model.Club;
import de.flower.rmt.model.Team;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author flowerrrr
 */
@Entity
@DiscriminatorValue("Training")
public class Training extends Event {

//    @Column
//    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime") /* must be same as in Match! */
//    private LocalTime kickOff;

    protected Training() {
    }

    public Training(Team team) {
        super(team);
    }

    public Training(final Club club) {
        super(club);
    }
}
