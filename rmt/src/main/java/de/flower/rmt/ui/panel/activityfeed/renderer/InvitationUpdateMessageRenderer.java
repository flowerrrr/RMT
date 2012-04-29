package de.flower.rmt.ui.panel.activityfeed.renderer;

import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.type.activity.InvitationUpdateMessage;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class InvitationUpdateMessageRenderer implements IMessageRenderer<InvitationUpdateMessage> {

    private final static Logger log = LoggerFactory.getLogger(InvitationUpdateMessageRenderer.class);

    private enum UpdateType {
        USER_STATUS,
        USER_COMMENT,
        USER_STATUS_COMMENT,
        MANAGER_STATUS,
        MANAGER_COMMENT,
        MANAGER_STATUS_COMMENT;
    }

    @Override
    public String toString(final InvitationUpdateMessage message) {
        String s;
        String status = (message.getStatus() != null) ? new ResourceModel(RSVPStatus.getResourceKey(message.getStatus())).getObject() : "";
        Object[] params = new Object[]{EventMessageRenderer.getEventLink(message), status};
        UpdateType updateType = getUpdateType(message);

        switch (updateType) {
            case USER_STATUS:
                s = new StringResourceModel("activity.invitation.user.status.${status}", Model.of(message), params).getObject();
                break;
            case USER_COMMENT:
                s = new StringResourceModel("activity.invitation.user.comment", Model.of(message), params).getObject();
                break;
            case USER_STATUS_COMMENT:
                s = new StringResourceModel("activity.invitation.user.status.${status}", Model.of(message), params).getObject();
                s = new StringResourceModel("activity.invitation.user.statuscomment", Model.of(message), new Object[]{s}).getObject();
                break;
            case MANAGER_STATUS:
                s = new StringResourceModel("activity.invitation.manager.status", Model.of(message), params).getObject();
                break;
            case MANAGER_COMMENT:
                s = new StringResourceModel("activity.invitation.manager.comment", Model.of(message), params).getObject();
                break;
            case MANAGER_STATUS_COMMENT:
                s = new StringResourceModel("activity.invitation.manager.status", Model.of(message), params).getObject();
                s = new StringResourceModel("activity.invitation.manager.statuscomment", Model.of(message), new Object[]{s}).getObject();
                break;
            default:
                throw new IllegalStateException("Unknown usertype");
        }
        log.debug(message.toString() + " -> [" + s + "]");
        return s;
    }

    private static UpdateType getUpdateType(final InvitationUpdateMessage message) {
        if (message.getManagerName() != null) {
            if (message.getManagerComment() != null) {
                if (message.getStatus() != null) {
                    return UpdateType.MANAGER_STATUS_COMMENT;
                } else {
                    return UpdateType.MANAGER_COMMENT;
                }
            } else {
                return UpdateType.MANAGER_STATUS;
            }
        } else {
            if (message.getComment() != null) {
                if (message.getStatus() != null) {
                    return UpdateType.USER_STATUS_COMMENT;
                } else {
                    return UpdateType.USER_COMMENT;
                }
            } else {
                return UpdateType.USER_STATUS;
            }
        }
    }
}
