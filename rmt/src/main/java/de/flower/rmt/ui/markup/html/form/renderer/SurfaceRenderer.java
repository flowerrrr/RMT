package de.flower.rmt.ui.markup.html.form.renderer;

import de.flower.common.util.Collections;
import de.flower.rmt.model.db.type.Surface;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.ResourceModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public class SurfaceRenderer implements IChoiceRenderer<Surface> {

    @Override
    public Object getDisplayValue(final Surface object) {
        return getResourceString(Surface.getResourceKey(object));
    }

    @Override
    public String getIdValue(final Surface object, final int index) {
        return "" + object.ordinal();
    }

    public String renderList(List<Surface> surfaceList) {
        if (surfaceList == null || surfaceList.isEmpty()) {
            return getResourceString(Surface.getResourceKey(null));
        } else {
            List<String> list = Collections.convert(surfaceList, new Collections.IElementConverter<Surface, String>() {
                @Override
                public String convert(final Surface element) {
                    return getResourceString(Surface.getResourceKey(element));
                }
            });
            return StringUtils.join(list, ", ");
        }
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


