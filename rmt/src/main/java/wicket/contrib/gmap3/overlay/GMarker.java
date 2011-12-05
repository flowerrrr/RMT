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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import wicket.contrib.gmap3.ReviewPending;
import wicket.contrib.gmap3.api.GLatLng;
import wicket.contrib.gmap3.js.Constructor;

/**
 * Represents an Google Maps API's <a href="http://www.google.com/apis/maps/documentation/reference.html#GMarker"
 * >GMarker</a>.
 */
public class GMarker extends GOverlay {
    private static final long serialVersionUID = 1L;

    private final GMarkerOptions _options;

    public GMarker(final GMarkerOptions options) {
        super();
        _options = options;
    }

    public GLatLng getLatLng() {
        return _options.getLatLng();
    }

    public GMarkerOptions getMarkerOptions() {
        return _options;
    }

    @Override
    public String getJSconstructor() {
        final Constructor constructor = new Constructor("google.maps.Marker").add(_options.getJSconstructor());
        return constructor.toJS();
    }

    @Override
    @ReviewPending
    // Remove if method is tested.
    protected void updateOnAjaxCall(final AjaxRequestTarget target, final GOverlayEvent overlayEvent) {
        final Request request = RequestCycle.get().getRequest();
        _options.setLatLng(GLatLng.parse(request.getRequestParameters().getParameterValue("overlay.latLng")
                .toString()));
    }

    @Override
    public String toString() {
        return "GMarker [_options=" + _options + "]";
    }


}
