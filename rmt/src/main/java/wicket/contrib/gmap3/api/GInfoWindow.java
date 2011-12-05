package wicket.contrib.gmap3.api;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.ReviewPending;
import wicket.contrib.gmap3.overlay.GMarker;

/**
 * Represents an Google Maps API's <a href= "http://www.google.com/apis/maps/documentation/reference.html#GInfoWindow"
 * >GInfoWindow</a>.
 */
@ReviewPending
// Remove if class is tested.
public class GInfoWindow extends WebMarkupContainer {
    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    private GInfoWindowContent _infoWindowContent;

    private GLatLng _latLng;

    private GMarker _marker;

    private final RepeatingView _content = new RepeatingView("content");

    public GInfoWindow() {
        super("infoWindow");
        setOutputMarkupId(true);
        add(_content);

    }

    /**
     * Update state from a request to an AJAX target.
     */
    public void update() {
        final Request request = RequestCycle.get().getRequest();

        if (Boolean.parseBoolean(request.getRequestParameters().getParameterValue("infoWindow.hidden").toString())) {
            // Attention: don't use close() as this might result in an
            // endless AJAX request loop
            _marker = null;
            _latLng = null;
        }
    }

    public final String getJSinit() {
        return _infoWindowContent.getJSconstructor();
    }

    /**
     * Open an info window.
     *
     * @param content content to open in info window
     * @return This
     */
    public GInfoWindow open(final GLatLng latLng, final Component content, final AjaxRequestTarget target) {
        return open(latLng, new GInfoWindowContent(content), target);
    }

    /**
     * Open an info window.
     *
     * @param map
     *
     * @param content content to open in info window
     * @return This
     */
    public GInfoWindow open(final GMarker marker, final Component content, final AjaxRequestTarget target) {
        return open(marker, new GInfoWindowContent(content), target);
    }

    public GInfoWindow open(final GLatLng latLng, final GInfoWindowContent tab, final AjaxRequestTarget target) {

        _latLng = latLng;
        _marker = null;
        _content.add(tab.getContent());
        target.appendJavaScript(getJSopen(latLng, tab));
        target.add(this);

        return this;
    }

    public GInfoWindow open(final GMarker marker, final GInfoWindowContent tab, final AjaxRequestTarget target) {

        _latLng = null;
        _marker = marker;

        target.appendJavaScript(getJSopen(marker, tab));
        target.add(this);

        return this;
    }

    public boolean isOpen() {
        return (_latLng != null || _marker != null);
    }

    public void close(final AjaxRequestTarget target) {

        _marker = null;
        _latLng = null;

        target.appendJavaScript(getJSclose());
        target.add(this);
    }

    private String getJSopen(final GLatLng latLng, final GInfoWindowContent tab) {
        final StringBuffer buffer = new StringBuffer();
        buffer.append(" var info =  " + tab.getJSconstructor(latLng) + " ;\n");
        buffer.append("info.open(");
        buffer.append(getGMap2().getJsReference() + ".map");
        buffer.append(");");
        return buffer.toString();
    }

    public static String getJSopenFunction(final GMap map, final String content, final GMarker overlay) {
        final StringBuffer openFunction = new StringBuffer();
        openFunction.append("function() {");
        openFunction.append(map.getJsReference() + ".openSingleInfoWindowOn(overlay" + overlay.getId() + ", \""
                + content.toString() + "\");}");
        return openFunction.toString();
    }

    private String getJSopen(final GMarker marker, final GInfoWindowContent tab) {
        final StringBuffer buffer = new StringBuffer();
        buffer.append(" var infoWin =  " + tab.getJSconstructor() + " ;\n");

        buffer.append("infoWin.open(");
        buffer.append(getGMap2().getJsReference() + ".map,");
        buffer.append(marker.getId());
        buffer.append(");");

        return buffer.toString();
    }

    private String getJSclose() {
        return getGMap2().getJSinvoke("closeInfoWindow()");
    }

    private GMap getGMap2() {
        return findParent(GMap.class);
    }

}