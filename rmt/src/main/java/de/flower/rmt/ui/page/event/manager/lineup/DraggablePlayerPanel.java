package de.flower.rmt.ui.page.event.manager.lineup;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Invitation;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author flowerrrr
 */
public class DraggablePlayerPanel extends BasePanel {

    private Long invitationId;

    public DraggablePlayerPanel(Invitation invitation, boolean showRemoveButton) {
        this.invitationId = invitation.getId();
        add(new Label("name", invitation.getName()));
        add(AttributeModifier.append("class", "draggable"));
        add(AttributeModifier.append("style", "position: absolute;"));

        AjaxLink removeButton = new AjaxLink("removeButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                DraggablePlayerPanel.this.onRemove(target, invitationId);
            }
        };
        removeButton.setVisible(showRemoveButton);
        add(removeButton);
    }

    protected void onRemove(final AjaxRequestTarget target, final Long invitationId) {

    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
        String javascript = String.format("$('#%s').draggable({ revert: 'invalid' });", getMarkupId());
        response.renderOnDomReadyJavaScript(javascript);
    }

    public Long getInvitationId() {
        return invitationId;
    }
}
