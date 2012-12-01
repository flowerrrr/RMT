package de.flower.rmt.ui.page.event.manager.lineup;

import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.LineupItem;
import de.flower.rmt.ui.page.event.manager.teams.PlayerPanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * @author flowerrrr
 */
public class DraggablePlayerPanel extends PlayerPanel {

    public DraggablePlayerPanel(Invitation invitation, final LineupItem lineupItem, boolean showRemoveButton) {
        super(invitation, showRemoveButton);
        add(AttributeModifier.append("class", "draggable"));
        add(AttributeModifier.append("style", "position: absolute;"));

        if (lineupItem != null) {
            add(AttributeModifier.append("style", "top: " + lineupItem.getTop() + "px; left: " + lineupItem.getLeft() + "px;"));
        }
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
}
