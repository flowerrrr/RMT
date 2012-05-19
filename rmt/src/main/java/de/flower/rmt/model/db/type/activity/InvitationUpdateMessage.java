package de.flower.rmt.model.db.type.activity;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;

/**
 * @author flowerrrr
 */
public class InvitationUpdateMessage extends AbstractEventMessage {

    /**
     * Must have a serialVersionUID as instances are stored in database.
     */
    private static final long serialVersionUID = 1L;

    private String userName;

    /**
     * Set if manager has updated the status.
     */
    private String managerName;

    /**
     * Not null if status has changed.
     */
    private RSVPStatus status;

    /**
     * Not null if comment has changed.
     */
    private String comment;

    /**
     * Not null if managerComment has changed.
     */
    private String managerComment;

    public InvitationUpdateMessage(final Event event) {
        super(event);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(final String managerName) {
        this.managerName = managerName;
    }

    public RSVPStatus getStatus() {
        return status;
    }

    public void setStatus(final RSVPStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }



    public String getManagerComment() {
        return managerComment;
    }

    public void setManagerComment(final String managerComment) {
        this.managerComment = managerComment;
    }

    @Override
    public String toString() {
        return "InvitationUpdateMessage{" +
                "userName='" + userName + '\'' +
                ", managerName='" + managerName + '\'' +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                ", managerComment='" + managerComment + '\'' +
                "} " + super.toString();
    }
}
