package de.flower.rmt.model.event;

import de.flower.rmt.model.Team;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author oblume
 */
@Entity
@DiscriminatorValue("Event")
public class GeneralEvent extends Event {

    private GeneralEvent() {
    }

    public GeneralEvent(Team team) {
        super(team);
    }
}
