package de.flower.rmt.model.db.type.activity;

import de.flower.rmt.model.db.entity.event.Event;
import org.hibernate.validator.constraints.NotBlank;


public class EmailSentMessage extends AbstractEventMessage {

    /**
     * Must have a serialVersionUID as instances are stored in database.
     */
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String managerName;

    public EmailSentMessage(final Event event) {
        super(event);
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(final String managerName) {
        this.managerName = managerName;
    }

    @Override
    public String toString() {
        return "EmailSentMessage{" +
                "managerName='" + managerName + '\'' +
                "} " + super.toString();
    }
}
