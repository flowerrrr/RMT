package de.flower.rmt.model.event;

import de.flower.rmt.model.Team;
import de.flower.rmt.model.event.Event;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author oblume
 */
@Entity
@DiscriminatorValue("Training")
public class Training extends Event {

    @Column
    @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
    private LocalTime kickOff;

    private Training() {
    }

    public Training(Team team) {
        super(team);
    }
}
