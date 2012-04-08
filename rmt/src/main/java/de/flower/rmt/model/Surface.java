package de.flower.rmt.model;

import de.flower.common.util.Collections;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.ResourceModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public enum Surface {

    /**
     * DO NOT ALTER THESE NAMES. They are stored in database as strings.
     */
    NATURAL_GRASS,
    ARTIFICIAL_GRASS,
    ASH;

    public static String getResourceKey(Surface object) {
        if (object == null) {
            return "surfaces.null";
        } else {
            return "surfaces." + object.name().toLowerCase();
        }
    }

    /**
     * Ok, violates several design rules, but it is sooo handy to have the element render itself.
     *
     * @param surfaceList
     * @return
     */
    public static String render(List<Surface> surfaceList) {
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
     * Super crazy hack, as it introduces a reference to wicket in this layer.
     * // TODO (flowerrrr - 08.04.12) replace with plain java bundle lookup.
     *
     * @param key
     * @return
     */
    private static String getResourceString(String key) {
        return new ResourceModel(key).getObject();
    }

}
