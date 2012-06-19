package de.flower.rmt.ui.markup.html.form.renderer;

import de.flower.rmt.model.db.entity.CalItem;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.ResourceModel;

/**
 * @author flowerrrr
 */
public class CalItemTypeRenderer implements IChoiceRenderer<CalItem.Type> {

    @Override
    public Object getDisplayValue(final CalItem.Type object) {
        return getResourceString(CalItem.Type.getResourceKey(object));
    }

    @Override
    public String getIdValue(final CalItem.Type object, final int index) {
        return "" + object.ordinal();
    }

    /**
     * Subclass can override if other resource lookup than wickets resource model is used
     * @param key
     * @return
     */
    protected String getResourceString(String key) {
        return new ResourceModel(key).getObject();
    }
}


