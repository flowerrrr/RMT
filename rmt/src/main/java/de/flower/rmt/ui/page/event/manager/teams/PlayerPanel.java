package de.flower.rmt.ui.page.event.manager.teams;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Invitation;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author flowerrrr
 */
public class PlayerPanel extends BasePanel {

    private Long invitationId;

    public PlayerPanel(Invitation invitation, boolean showRemoveButton) {
        this.invitationId = invitation.getId();
        add(new Label("name", invitation.getName()));

        AjaxLink removeButton = new AjaxLink("removeButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                PlayerPanel.this.onRemove(target, invitationId);
            }
        };
        removeButton.setVisible(showRemoveButton);
        add(removeButton);
    }

    protected void onRemove(final AjaxRequestTarget target, final Long invitationId) {

    }

    public Long getInvitationId() {
        return invitationId;
    }
}
