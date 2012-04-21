package wicket.contrib.gmap3.overlay;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.js.Constructor;

/**
 * @author flowerrrr
 */
public class GInfoWindow extends GOverlay {

    private String content;

    public GInfoWindow(String content) {
        this.content = content;
    }

    /**
     * @return A JavaScript constructor that represents this element.
     */
    @Override
    public String getJSconstructor() {
        return new Constructor("google.maps.InfoWindow").add(
                "{content: '" + content + "'}").toJS();
    }

    public String getJSopenFunction(GMap map, GMarker marker) {
        return "function() { " + getJsReference() + ".open(" + map.getJsReference() + ", " + marker.getJsReference() + ");}";
    }

    @Override
    protected void updateOnAjaxCall(final AjaxRequestTarget target, final GOverlayEvent overlayEvent) {
        throw new UnsupportedOperationException("Method not implemented!");
    }

    public static String getJSopenFunction(final GMap map, final String content, final GMarker overlay) {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("function() {");
        buffer.append(map.getJsReference() + ".openSingleInfoWindowOn(overlay" + overlay.getId() + ", \""
                + StringEscapeUtils.escapeEcmaScript(content.toString()) + "\");}");
        return buffer.toString();
    }

}
