package de.flower.rmt.ui.panel.activityfeed.renderer;

import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.model.db.type.activity.InvitationUpdateMessage2;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InvitationUpdateMessageRenderer2 extends AbstractEventMessageRenderer implements IMessageRenderer<InvitationUpdateMessage2> {

    private final static Logger log = LoggerFactory.getLogger(InvitationUpdateMessageRenderer2.class);

    @Override
    public String toString(final InvitationUpdateMessage2 message) {
        String s;
        String status = (message.getStatus() != null) ? new ResourceModel(RSVPStatus.getResourceKey(message.getStatus())).getObject() : "";
        Object[] params = new Object[]{ getEventArticle(message), getEventLink(message), status};
        InvitationUpdateMessage2.Type updateType = message.getType();

        switch (updateType) {
            case SELF_STATUS:
                s = new StringResourceModel("activity.invitation2.user.status.${status}", Model.of(message), params).getObject();
                break;
            case SELF_COMMENT:
                s = new StringResourceModel("activity.invitation2.user.comment", Model.of(message), params).getObject();
                break;
            case SELF_STATUS_COMMENT:
                s = new StringResourceModel("activity.invitation2.user.status.${status}", Model.of(message), params).getObject();
                s = new StringResourceModel("activity.invitation2.user.statuscomment", Model.of(message), new Object[]{s}).getObject();
                break;
            case OTHER_STATUS:
                s = new StringResourceModel("activity.invitation2.manager.status", Model.of(message), params).getObject();
                break;
            case OTHER_COMMENT:
                s = new StringResourceModel("activity.invitation2.other.comment", Model.of(message), params).getObject();
                break;
            case OTHER_STATUS_COMMENT:
                s = new StringResourceModel("activity.invitation2.manager.status", Model.of(message), params).getObject();
                s = new StringResourceModel("activity.invitation2.manager.statuscomment", Model.of(message), new Object[]{s}).getObject();
                break;
            default:
                throw new IllegalStateException("Unknown usertype");
        }
        log.debug(message.toString() + " -> [" + s + "]");
        return s;
    }

    @Override
    public boolean canHandle(final Object message) {
        return message instanceof InvitationUpdateMessage2;
    }
}
