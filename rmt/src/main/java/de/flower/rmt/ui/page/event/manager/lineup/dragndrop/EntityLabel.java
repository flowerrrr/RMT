package de.flower.rmt.ui.page.event.manager.lineup.dragndrop;

import de.flower.common.ui.ajax.dragndrop.IDraggable;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;


public class EntityLabel extends BasePanel implements IDraggable {

    private Long entityId;

    public EntityLabel(Long entityId, String name, boolean showRemoveButton) {
        this.entityId = entityId;
        add(new Label("name", name));

        AjaxLink removeButton = new AjaxLink("removeButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                EntityLabel.this.onRemove(target, EntityLabel.this.entityId);
            }
        };
        removeButton.setVisible(showRemoveButton);
        add(removeButton);
    }

    protected void onRemove(final AjaxRequestTarget target, final Long entityId) {

    }

    public Long getEntityId() {
        return entityId;
    }
}
