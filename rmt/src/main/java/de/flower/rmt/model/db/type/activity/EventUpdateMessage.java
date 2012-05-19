package de.flower.rmt.model.db.type.activity;

import de.flower.rmt.model.db.entity.event.Event;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author flowerrrr
 */
public class EventUpdateMessage extends AbstractEventMessage {

    /**
     * Must have a serialVersionUID as instances are stored in database.
     */
    private static final long serialVersionUID = 1L;

    private boolean created;

    private String teamName;

    @NotBlank
    private String managerName;

    public EventUpdateMessage(final Event event) {
        super(event);
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(final String managerName) {
        this.managerName = managerName;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(final boolean created) {
        this.created = created;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(final String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "EventUpdateMessage{" +
                "created=" + created +
                ", teamName='" + teamName + '\'' +
                ", managerName='" + managerName + '\'' +
                "} " + super.toString();
    }
}
