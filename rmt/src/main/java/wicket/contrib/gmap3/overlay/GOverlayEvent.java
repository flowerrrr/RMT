/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
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

import com.bosch.cbs.ui.web.common.map.gmap3.ReviewPending;

@ReviewPending
// Remove if class is tested.
public enum GOverlayEvent {

    CLICK, DBLCLICK, DRAGSTART, DRAGEND, MOUSEOUT, MOUSEOVER;

    public static GOverlayEvent from(final String event) {
        return valueOf(event.toUpperCase());
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    /**
     * For adding an event listener that calls a javascript function, not necessarily ending
     * in a wicket call.
     * @param overlay
     * @param function
     * @return
     */
    public String getJSadd(final GOverlay overlay, final String function) {
        return "google.maps.event.addListener(overlay" + overlay.getId() + ", '" + toString() + "'," + function + ");\n";
    }

    public String getJSadd(final GOverlay overlay) {
        return overlay.getParent().getJSinvoke("addOverlayListener('" + overlay.getId() + "', '" + toString() + "')");
    }

    public String getJSclear(final GOverlay overlay) {
        return overlay.getParent().getJSinvoke("clearOverlayListeners('" + overlay.getId() + "', '" + toString() + "')");
    }

}
