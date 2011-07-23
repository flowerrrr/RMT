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

import org.apache.wicket.ajax.AjaxRequestTarget;
import wicket.contrib.gmap3.js.Array;
import wicket.contrib.gmap3.js.Constructor;

/**
 * Represents an Google Maps API's <a href=
 * "http://www.google.com/apis/maps/documentation/reference.html#GPolygon"
 * >GPolygon</a>.
 */
public class GPolygon extends GOverlay {
    private static final long serialVersionUID = 1L;

    private final LatLng[] _gLatLngs;
    private final String _strokeColor;
    private final int _strokeWeight;
    private final float _strokeOpacity;
    private final String _fillColor;
    private final float _fillOpacity;

    public GPolygon( String strokeColor, int strokeWeight, float strokeOpacity, String fillColor, float fillOpacity,
            LatLng... gLatLngs ) {
        super();

        _gLatLngs = gLatLngs;

        _strokeColor = strokeColor;
        _strokeWeight = strokeWeight;
        _strokeOpacity = strokeOpacity;
        _fillColor = fillColor;
        _fillOpacity = fillOpacity;
    }

    @Override
    public String getJSconstructor() {
        Constructor constructor = new Constructor( "GPolygon" );

        Array array = new Array();
        for ( LatLng gLatLng : _gLatLngs ) {
            array.add( gLatLng.getJSconstructor() );
        }
        constructor.add( array.toJS() );

        constructor.addString( _strokeColor );
        constructor.addString( _strokeWeight );
        constructor.addString( _strokeOpacity );
        constructor.addString( _fillColor );
        constructor.addString( _fillOpacity );

        return constructor.toJS();
    }

    @Override
    protected void updateOnAjaxCall( AjaxRequestTarget target, GEvent overlayEvent ) {
        // TODO
    }
}
