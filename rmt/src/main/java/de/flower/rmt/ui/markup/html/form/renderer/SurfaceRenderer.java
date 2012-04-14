package de.flower.rmt.ui.markup.html.form.renderer;

import de.flower.rmt.model.Surface;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.ResourceModel;

/**
 * @author flowerrrr
 */
public class SurfaceRenderer implements IChoiceRenderer<Surface> {

    @Override
    public Object getDisplayValue(final Surface object) {
        return new ResourceModel(Surface.getResourceKey(object)).getObject();
    }

    @Override
    public String getIdValue(final Surface object, final int index) {
        return "" + object.ordinal();
    }
}


