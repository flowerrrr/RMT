package de.flower.rmt.ui.page.event.manager.invitees;

import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.panel.BasePanel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class InviteeSecondaryPanel extends BasePanel {

    public InviteeSecondaryPanel(IModel<Event> model) {

        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);
        add(new AjaxSlideTogglePanel("addInviteePanel", "manager.event.invitee.button.add", new AddInviteePanel(AjaxSlideTogglePanel.WRAPPED_PANEL_ID, model)));
        add(new AjaxSlideTogglePanel("addGuestPlayerPanel", "manager.event.guestplayer.button.add", new AddGuestPlayerPanel(AjaxSlideTogglePanel.WRAPPED_PANEL_ID, model)));
    }
}
