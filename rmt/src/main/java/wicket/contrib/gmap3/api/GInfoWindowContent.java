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
package wicket.contrib.gmap3.api;

import org.apache.wicket.Component;
import wicket.contrib.gmap3.ReviewPending;
import wicket.contrib.gmap3.js.Constructor;

/**
 * Represents an Google Maps API's <a href=
 * "http://www.google.com/apis/maps/documentation/reference.html#GInfoWindowTab" >GInfoWindowTab</a>.
 */
@ReviewPending
// Remove if class is tested.
public class GInfoWindowContent implements GValue {

    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    private final Component _content;

    /**
     * Construct.
     *
     * @param title
     * @param content
     */
    public GInfoWindowContent(final Component content) {
        _content = content;
        content.setOutputMarkupId(true);

    }

    public Component getContent() {
        return _content;
    }

    /**
     * @return A JavaScript constructor that represents this element.
     */
    @Override
    public String getJSconstructor() {
        return new Constructor("google.maps.InfoWindow").add(
                "{content: document.getElementById('" + _content.getMarkupId() + "')}").toJS();
    }

    public String getJSconstructor(final GLatLng latLng) {
        return new Constructor("google.maps.InfoWindow").add(
                "{content: document.getElementById('" + _content.getMarkupId() + "'), position: "
                        + latLng.getJSconstructor() + "}").toJS();
    }
}
