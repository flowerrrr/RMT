/*
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.gmap3.overlay;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.ReviewPending;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Represents an Google Maps API's <a href= "http://www.google.com/apis/maps/documentation/reference.html#GOverlay"
 * >GOverlay</a>.
 */
@ReviewPending
// Remove if class is tested.
public abstract class GOverlay implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id of this object. it is session unique.
     */
    private final String _id;

    private GMap _parent = null;

    private final Map<GOverlayEvent, GOverlayEventHandler> events = new EnumMap<GOverlayEvent, GOverlayEventHandler>(
            GOverlayEvent.class);

    private final Map<GOverlayEvent, String> _functions = new EnumMap<GOverlayEvent, String>(GOverlayEvent.class);

    /**
     * Construct.
     */
    public GOverlay() {
        // id is session unique
        _id = String.valueOf(Session.get().nextSequenceValue());
    }

    public String getJsReference() {
        return _parent.getJsReference() + ".overlays['" + getId() + "']";
    }

    /**
     * @return String representing the JavaScript add command for the corresponding JavaScript object.
     */
    public String getJS() {

        return addFunctions(addEvents(addOverlays(new StringBuffer()))).toString();

    }

    private StringBuffer addOverlays(final StringBuffer js) {
        js.append("var overlay" + getId() + " = " + getJSconstructor() + ";\n");
        // Overlays will set map in their options, so this is not necessary
        js.append("overlay" + getId() + ".setMap(" + _parent.getJsReference() + ".map);\n");
        js.append(_parent.getJSinvoke("addOverlay('" + getId() + "', overlay" + getId() + ")"));
        return js;
    }

    private StringBuffer addFunctions(final StringBuffer js) {
        for (final GOverlayEvent event : _functions.keySet()) {
            js.append(event.getJSadd(this, _functions.get(event)));
        }
        return js;

    }

    private StringBuffer addEvents(final StringBuffer js) {
        for (final GOverlayEvent event : events.keySet()) {
            js.append(event.getJSadd(this));
        }
        return js;

    }

    /**
     * @return String representing the JavaScript remove command for the corresponding JavaScript object.
     */
    public String getJSremove() {
        final StringBuffer js = new StringBuffer();
        // clear the Events
        for (final GOverlayEvent event : events.keySet()) {
            js.append(event.getJSclear(this));
        }
        js.append(_parent.getJSinvoke("removeOverlay('" + getId() + "')"));
        return js.toString();
    }

    /**
     * @return The session unique id of this object as a String.
     */
    public String getId() {
        return _id;
    }

    /**
     * Implement the needed JavaScript constructor for the corresponding JavaScript object.
     *
     * @return String representing the JavaScript constructor.
     */
    public abstract String getJSconstructor();

    public GMap getParent() {
        return _parent;
    }

    public void setParent(final GMap parent) {
        _parent = parent;
    }

    /**
     * Add a control.
     *
     * @param control control to add
     * @return This
     */
    public GOverlay addListener(final GOverlayEvent event, final GOverlayEventHandler handler) {
        events.put(event, handler);
        return this;
    }

    /**
     * Add a control.
     *
     * @param control control to add
     * @return This
     */
    public GOverlay addListener(final GOverlayEvent event, final GOverlayEventHandler handler,
            final AjaxRequestTarget target) {
        addListener(event, handler);
        target.appendJavaScript(event.getJSadd(this));
        return this;
    }

    /**
     * Add a none Ajax Event.
     */
    public GOverlay addFunctionListener(final GOverlayEvent event, final String jsFunction) {
        _functions.put(event, jsFunction);
        return this;
    }

    /**
     * Return all registered Listeners.
     *
     * @return registered listeners
     */
    public Map<GOverlayEvent, GOverlayEventHandler> getListeners() {
        return Collections.unmodifiableMap(events);
    }

    /**
     * Return all registered Listeners.
     *
     * @return registered listeners
     */
    public Map<GOverlayEvent, String> getFunctionListeners() {
        return Collections.unmodifiableMap(_functions);
    }

    /**
     * Clear listeners.
     *
     * @param event event to be cleared.
     * @return This
     */
    public GOverlay clearListeners(final GOverlayEvent event) {
        events.remove(event);
        return this;
    }

    /**
     * Clear listeners.
     *
     * @param event event to be cleared.
     * @return This
     */
    public GOverlay clearListeners(final GOverlayEvent event, final AjaxRequestTarget target) {
        clearListeners(event);
        target.appendJavaScript(event.getJSclear(this));
        return this;
    }

    /**
     * Called when an Ajax call occurs.
     *
     * @param target
     * @param overlayEvent
     */
    public void onEvent(final AjaxRequestTarget target, final GOverlayEvent overlayEvent) {
        updateOnAjaxCall(target, overlayEvent);
        events.get(overlayEvent).onEvent(target);
    }

    /**
     * Implement to handle Ajax calls to your needs.
     *
     * @param target
     * @param overlayEvent
     */
    protected abstract void updateOnAjaxCall(AjaxRequestTarget target, GOverlayEvent overlayEvent);
}