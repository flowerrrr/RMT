package de.flower.rmt.ui.page.event.player;

import de.flower.common.ui.alert.AlertMessage;
import de.flower.common.ui.alert.AlertMessagePanel;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.type.RSVPStatus;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * Displayed on event page if player has not yet changed his status to declined after event has been canceled.
 *
 * @author flowerrrr
 */
public abstract class ConfirmEventCanceledMessage extends AlertMessage {

    private IModel<Invitation> model;

    public ConfirmEventCanceledMessage(final IModel<Invitation> model) {
        super(new ResourceModel("alert.message.event.canceled.confirm"), new ResourceModel("alert.message.event.canceled.button.confirm"));
        this.model = model;
    }

    @Override
    public boolean onClick(final AlertMessagePanel alertMessagePanel) {
        confirm();
        return false;
    }

    @Override
    public boolean isVisible(final AlertMessagePanel alertMessagePanel) {
        Invitation invitation = model.getObject();
        if (invitation == null) {
            return false;
        }
        boolean isCanceled = invitation.getEvent().isCanceled();
        return isCanceled && invitation.getStatus() != RSVPStatus.DECLINED;
    }

    @Override
    public String getSessionKey() {
        return super.getSessionKey() + model.getObject().getId();
    }

    protected abstract void confirm();
}
