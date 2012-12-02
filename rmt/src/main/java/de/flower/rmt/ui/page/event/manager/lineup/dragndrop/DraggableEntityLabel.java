package de.flower.rmt.ui.page.event.manager.lineup.dragndrop;

import de.flower.common.ui.ajax.dragndrop.DraggableBehavior;

/**
 * @author flowerrrr
 */
public class DraggableEntityLabel extends EntityLabel {

    public DraggableEntityLabel(Long entityId, String label, boolean showRemoveButton) {
        super(entityId, label, showRemoveButton);
        add(new DraggableBehavior());
    }
}
