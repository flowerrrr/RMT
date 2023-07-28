package de.flower.rmt.model.db.type.activity;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.db.type.RSVPStatus;

import java.util.Objects;


public class InvitationUpdateMessage2 extends AbstractEventMessage {

    public enum Type {
        SELF_STATUS,
        SELF_COMMENT,
        SELF_STATUS_COMMENT,
        OTHER_STATUS,
        OTHER_COMMENT,
        OTHER_STATUS_COMMENT;

        public static Type getType(boolean isSelf, boolean isStatus, boolean isComment) {
            if (isSelf && isStatus && !isComment) return SELF_STATUS;
            if (isSelf && !isStatus && isComment) return SELF_COMMENT;
            if (isSelf && isStatus && isComment) return SELF_STATUS_COMMENT;
            if (!isSelf && isStatus && !isComment) return OTHER_STATUS;
            if (!isSelf && !isStatus && isComment) return OTHER_COMMENT;
            if (!isSelf && isStatus && isComment) return OTHER_STATUS_COMMENT;
            throw new IllegalStateException("Unknown type");
        }
    }

    /**
     * Must have a serialVersionUID as instances are stored in database.
     */
    private static final long serialVersionUID = 1L;

    private String invitationUserName;

    private String authorUserName;

    /**
     * Not null if status has changed.
     */
    private RSVPStatus status;

    /**
     * Not null if comment has changed.
     */
    private String comment;

    public InvitationUpdateMessage2(final Event event) {
        super(event);
    }

    public String getInvitationUserName() {
        return invitationUserName;
    }

    public void setInvitationUserName(final String invitationUserName) {
        this.invitationUserName = invitationUserName;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(final String authorUserName) {
        this.authorUserName = authorUserName;
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

    public Type getType() {
        boolean isSelf = Objects.equals(invitationUserName, authorUserName);
        boolean isStatus = status != null;
        boolean isComment = comment != null;
        return Type.getType(isSelf, isStatus, isComment);
    }

    @Override
    public String toString() {
        return "InvitationUpdateMessage{" +
                "invitationUserName='" + invitationUserName + '\'' +
                ", authorName='" + authorUserName + '\'' +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                "} " + super.toString();
    }
}
