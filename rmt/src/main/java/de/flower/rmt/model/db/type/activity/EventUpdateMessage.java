package de.flower.rmt.model.db.type.activity;

import de.flower.rmt.model.db.entity.event.Event;
import org.hibernate.validator.constraints.NotBlank;


public class EventUpdateMessage extends AbstractEventMessage {

    public enum Type {
        CREATED,
        UPDATED,
        CANCELED,
        LINEUP_PUBLISHED;
    }

    /**
     * Must have a serialVersionUID as instances are stored in database.
     */
    private static final long serialVersionUID = 1L;

    private Type type;

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

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
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
                "type=" + type +
                ", teamName='" + teamName + '\'' +
                ", managerName='" + managerName + '\'' +
                "} " + super.toString();
    }
}
