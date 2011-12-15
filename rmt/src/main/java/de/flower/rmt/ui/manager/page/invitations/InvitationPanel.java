package de.flower.rmt.ui.manager.page.invitations;

import de.flower.common.ui.ajax.AjaxLink;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class InvitationPanel extends BasePanel {

    public InvitationPanel(final IModel<Event> model) {
        add(new AjaxLink("sendInvitationButton") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                // setResponsePage(new InvitationPage());
                throw new UnsupportedOperationException("Feature not implemented!");
            }
        });

    }
}
