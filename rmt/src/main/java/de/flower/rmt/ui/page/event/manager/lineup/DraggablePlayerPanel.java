package de.flower.rmt.ui.page.event.manager.lineup;

import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.LineupItem;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author flowerrrr
 */
public class DraggablePlayerPanel extends BasePanel {

    private Long invitationId;

    public DraggablePlayerPanel(Invitation invitation, final LineupItem lineupItem, boolean showRemoveButton) {
        this.invitationId = invitation.getId();
        add(new Label("name", invitation.getName()));
        add(AttributeModifier.append("class", "draggable"));
        add(AttributeModifier.append("style", "position: absolute;"));

        if (lineupItem != null) {
            add(AttributeModifier.append("style", "top: " + lineupItem.getTop() + "px; left: " + lineupItem.getLeft() + "px;"));
        }

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

    protected boolean isDraggable() {
        return true;
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
        if (isDraggable()) {
            String javascript = String.format("$('#%s').draggable({ revert: 'invalid' });", getMarkupId());
            response.renderOnDomReadyJavaScript(javascript);
        }
    }

    public Long getInvitationId() {
        return invitationId;
    }
}
