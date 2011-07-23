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
package wicket.contrib.gmap3;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wicket.contrib.gmap3.api.*;
import wicket.contrib.gmap3.event.GEventListenerBehavior;

import java.util.*;

/**
 * Wicket component to embed <a href="http://maps.google.com">Google Maps</a>
 * into your pages.
 * <p>
 * The Google Maps API requires an API key to use it. You will need to generate
 * one for each deployment context you have. See the <a
 * href="http://www.google.com/apis/maps/signup.html">Google Maps API sign up
 * page</a> for more information.
 */
public class GMap extends Panel implements GOverlayContainer {
    /** log. */
    private static final Logger log = LoggerFactory.getLogger( GMap.class );

    private static final long serialVersionUID = 1L;

    private LatLng _center = new LatLng( 37.4419, -122.1419 );

    private boolean _draggingEnabled = true;

    private boolean _doubleClickZoomEnabled = false;

    private boolean _scrollWheelZoomEnabled = false;

    private GMapType _mapType = GMapType.ROADMAP;

    private int _zoom = 13;

    private final Set<GControl> _controls = new HashSet<GControl>();

    private final List<GOverlay> _overlays = new ArrayList<GOverlay>();

    private final WebMarkupContainer _map;

    private final GInfoWindow _infoWindow;

    private GLatLngBounds _bounds;

    private OverlayListener _overlayListener = null;

    /**
     * Construct.
     * 
     * @param id
     */
    public GMap( final String id ) {
        this( id, new GMapHeaderContributor(), new ArrayList<GOverlay>() );
    }

    /**
     * Construct.
     * 
     * @param id
     * @param overlays
     * @deprecated The usage is discouraged. Use this(String, String) instead
     *             and add the overlays later on.
     */
    @Deprecated
    public GMap( final String id, final List<GOverlay> overlays ) {
        this( id, new GMapHeaderContributor(), overlays );
    }

    /**
     * Construct.
     * 
     * @param id
     * @param headerContrib
     */
    public GMap( final String id, final Behavior headerContrib ) {
        super( id );

        add( headerContrib );
        add( new AbstractBehavior() {

            @Override
            public void renderHead(Component component, final IHeaderResponse response ) {
                response.renderOnDomReadyJavaScript( getJSinit() );
            }
        } );

        _infoWindow = new GInfoWindow();
        add( _infoWindow );

        _map = new WebMarkupContainer( "map" );
        _map.setOutputMarkupId( true );
        add( _map );

        if ( activateOverlayListener() ) {
            _overlayListener = new OverlayListener();
            add( _overlayListener );
        }
    }

    public String getMapId() {
        return _map.getMarkupId();
    }

    protected boolean activateOverlayListener() {
        return true;
    }

    /**
     * Construct.
     * 
     * @param id
     * @param headerContrib
     * @param overlays
     * @deprecated The usage is discouraged. Use this(String, String) instead
     *             and add the overlays later on.
     */
    @Deprecated
    public GMap( final String id, final GMapHeaderContributor headerContrib, final List<GOverlay> overlays ) {
        this( id, headerContrib );

        for ( final GOverlay overlay : overlays ) {
            addOverlay( overlay );
        }
    }

    /**
     * @see org.apache.wicket.MarkupContainer#onRender(org.apache.wicket.markup.MarkupStream)
     */
    @Override
    protected void onRender() {
        super.onRender();
        if (RuntimeConfigurationType.DEVELOPMENT.equals( Application.get().getConfigurationType() )
                && !Application.get().getMarkupSettings().getStripWicketTags() ) {
            log.warn( "Application is in DEVELOPMENT mode && Wicket tags are not stripped,"
                    + "Some Chrome Versions will not render the GMap."
                    + " Change to DEPLOYMENT mode  || turn on Wicket tags stripping." + " See:"
                    + " http://www.nabble.com/Gmap2-problem-with-Firefox-3.0-to18137475.html." );
        }
    }

    /**
     * Add a control.
     * 
     * @param control
     *            control to add
     * @return This
     */
    public GMap addControl( final GControl control ) {
        _controls.add( control );

        if ( AjaxRequestTarget.get() != null && findPage() != null ) {
            AjaxRequestTarget.get().appendJavaScript(control.getJSadd(GMap.this));
        }

        return this;
    }

    /**
     * Remove a control.
     * 
     * @param control
     *            control to remove
     * @return This
     */
    public GMap removeControl( final GControl control ) {
        _controls.remove( control );

        if ( AjaxRequestTarget.get() != null && findPage() != null ) {
            AjaxRequestTarget.get().appendJavaScript(control.getJSremove(GMap.this));
        }

        return this;
    }

    /**
     * Add an overlay.
     * 
     * @param overlay
     *            overlay to add
     * @return This
     */
    @Override
    public GMap addOverlay( final GOverlay overlay ) {
        _overlays.add( overlay );
        overlay.setParent( this );

        if ( AjaxRequestTarget.get() != null && findPage() != null ) {
            AjaxRequestTarget.get().appendJavaScript(overlay.getJS());
        }

        return this;
    }

    /**
     * Remove an overlay.
     * 
     * @param overlay
     *            overlay to remove
     * @return This
     */
    @Override
    public GMap removeOverlay( final GOverlay overlay ) {
        while ( _overlays.contains( overlay ) ) {
            _overlays.remove( overlay );
        }

        if ( AjaxRequestTarget.get() != null && findPage() != null ) {
            AjaxRequestTarget.get().appendJavaScript(overlay.getJSremove());
        }

        overlay.setParent( null );

        return this;
    }

    /**
     * Clear all overlays.
     * 
     * @return This
     */
    @Override
    public GMap removeAllOverlays() {
        for ( final GOverlay overlay : _overlays ) {
            overlay.setParent( null );
        }
        _overlays.clear();
        if ( AjaxRequestTarget.get() != null && findPage() != null ) {
            AjaxRequestTarget.get().appendJavaScript(getJSinvoke("clearOverlays()"));
        }
        return this;
    }

    @Override
    public List<GOverlay> getOverlays() {
        return Collections.unmodifiableList( _overlays );
    }

    public Set<GControl> getControls() {
        return Collections.unmodifiableSet( _controls );
    }

    public LatLng getCenter() {
        return _center;
    }

    public GLatLngBounds getBounds() {
        return _bounds;
    }

    public void setDraggingEnabled( final boolean enabled ) {
        if ( this._draggingEnabled != enabled ) {
            _draggingEnabled = enabled;

            if ( AjaxRequestTarget.get() != null && findPage() != null ) {
                AjaxRequestTarget.get().appendJavaScript(getJSsetDraggingEnabled(enabled));
            }
        }
    }

    public boolean isDraggingEnabled() {
        return _draggingEnabled;
    }

    public void setDoubleClickZoomEnabled( final boolean enabled ) {
        if ( this._doubleClickZoomEnabled != enabled ) {
            _doubleClickZoomEnabled = enabled;

            if ( AjaxRequestTarget.get() != null && findPage() != null ) {
                AjaxRequestTarget.get().appendJavaScript(getJSsetDoubleClickZoomEnabled(enabled));
            }
        }
    }

    public boolean isDoubleClickZoomEnabled() {
        return _doubleClickZoomEnabled;
    }

    public void setScrollWheelZoomEnabled( final boolean enabled ) {
        if ( this._scrollWheelZoomEnabled != enabled ) {
            _scrollWheelZoomEnabled = enabled;

            if ( AjaxRequestTarget.get() != null && findPage() != null ) {
                AjaxRequestTarget.get().appendJavaScript(getJSsetScrollWheelZoomEnabled(enabled));
            }
        }
    }

    public boolean isScrollWheelZoomEnabled() {
        return _scrollWheelZoomEnabled;
    }

    public GMapType getMapType() {
        return _mapType;
    }

    public void setMapType( final GMapType mapType ) {
        if ( this._mapType != mapType ) {
            this._mapType = mapType;

            if ( AjaxRequestTarget.get() != null && findPage() != null ) {
                AjaxRequestTarget.get().appendJavaScript(mapType.getJSsetMapType(GMap.this));
            }
        }
    }

    public int getZoom() {
        return _zoom;
    }

    public void setZoom( final int level ) {
        if ( this._zoom != level ) {
            this._zoom = level;

            if ( AjaxRequestTarget.get() != null && findPage() != null ) {
                AjaxRequestTarget.get().appendJavaScript(getJSsetZoom(_zoom));
            }
        }
    }

    /**
     * Set the center.
     * 
     * @param center
     *            center to set
     */
    public void setCenter( final LatLng center ) {
        if ( !this._center.equals( center ) ) {
            this._center = center;

            if ( AjaxRequestTarget.get() != null && findPage() != null ) {
                AjaxRequestTarget.get().appendJavaScript(getJSsetCenter(center));
            }
        }
    }

    /**
     * Changes the center point of the map to the given point. If the point is
     * already visible in the current map view, change the center in a smooth
     * animation.
     * 
     * @param center
     *            the new center of the map
     */
    public void panTo( final LatLng center ) {
        if ( !this._center.equals( center ) ) {
            this._center = center;

            if ( AjaxRequestTarget.get() != null && findPage() != null ) {
                AjaxRequestTarget.get().appendJavaScript(getJSpanTo(center));
            }
        }
    }

    public GInfoWindow getInfoWindow() {
        return _infoWindow;
    }

    /**
     * Generates the JavaScript used to instantiate this GMap3 as an JavaScript
     * class on the client side.
     * 
     * @return The generated JavaScript
     */
    private String getJSinit() {
        final StringBuffer js = new StringBuffer( "new WicketMap('" + _map.getMarkupId() + "');\n" );

        if ( activateOverlayListener() ) {
            js.append( _overlayListener.getJSinit() );
        }
        js.append( getJSsetCenter( getCenter() ) );
        js.append( getJSsetZoom( getZoom() ) );
        js.append( getJSsetDraggingEnabled( _draggingEnabled ) );
        js.append( getJSsetDoubleClickZoomEnabled( _doubleClickZoomEnabled ) );
        js.append( getJSsetScrollWheelZoomEnabled( _scrollWheelZoomEnabled ) );

        js.append( _mapType.getJSsetMapType( this ) );

        // Add the controls.
        //        for ( final GControl control : controls ) {
        //            js.append( control.getJSadd( this ) );
        //        }

        // Add the overlays.
        for ( final GOverlay overlay : _overlays ) {
            js.append( overlay.getJS() );
        }

        for ( final Object behavior : getBehaviors( GEventListenerBehavior.class ) ) {
            js.append( ( (GEventListenerBehavior) behavior ).getJSaddListener() );
        }

        return js.toString();
    }

    /**
     * Convenience method for generating a JavaScript call on this GMap2 with
     * the given invocation.
     * 
     * @param invocation
     *            The JavaScript call to invoke on this GMap2.
     * @return The generated JavaScript.
     */
    // TODO Could this become default or protected?
    public String getJSinvoke( final String invocation ) {
        return getJsReference() + "." + invocation + ";\n";
    }

    /**
     * Build a reference in JS-Scope.
     */
    public String getJsReference() {
        return "Wicket.maps['" + _map.getMarkupId() + "']";
    }

    /**
     * @see #fitMarkers(List, boolean, double)
     */
    public void fitMarkers( final List<LatLng> markersToShow ) {
        fitMarkers( markersToShow, false, 0.0 );
    }

    /**
     * @see #fitMarkers(List, boolean, double)
     */
    public void fitMarkers( final List<LatLng> markersToShow, final boolean showMarkersForPoints ) {
        fitMarkers( markersToShow, showMarkersForPoints, 0.0 );
    }

    /**
     * <p>
     * Makes the map zoom out and centre around all the GLatLng points in
     * markersToShow.
     * <p>
     * Big ups to Doug Leeper for the code.
     * 
     * @see <a href=
     *      "http://www.nabble.com/Re%3A-initial-GMap2-bounds-question-p19886673.html"
     *      >Doug's Nabble post</a>
     * @param markersToShow
     *            the points to centre around.
     * @param showMarkersForPoints
     *            if true, will also add basic markers to the map for each point
     *            focused on. Just a simple convenience method - you will
     *            probably want to turn this off so that you can show more
     *            information with each marker when clicked etc.
     */
    public void fitMarkers( final List<LatLng> markersToShow, final boolean showMarkersForPoints, final double zoomAdjustment ) {
        if ( markersToShow.isEmpty() ) {
            log.warn( "Empty list provided to GMap2.fitMarkers method." );
            return;
        }

        this.add(new AbstractBehavior() {

            @Override
            public void renderHead(Component component, final IHeaderResponse response ) {
                final StringBuffer buf = new StringBuffer();
                buf.append( "var bounds = new google.maps.LatLngBounds();\n" );
                buf.append( "var map = " + GMap.this.getJSinvoke( "map" ) );

                // Ask google maps to keep extending the bounds to include each
                // point
                for ( final LatLng point : markersToShow ) {
                    buf.append( "bounds.extend( " + point.getJSconstructor() + " );\n" );
                }

                buf.append( "map.fitBounds( bounds  );\n" );

                response.renderOnDomReadyJavaScript(buf.toString());
            }
        });

        // show the markers
        if ( showMarkersForPoints ) {
            for ( final LatLng location : markersToShow ) {
                this.addOverlay( new GMarker( new GMarkerOptions( this, location ) ) );
            }
        }
    }

    private String getJSsetDraggingEnabled( final boolean enabled ) {
        return getJSinvoke( "setDraggingEnabled(" + enabled + ")" );
    }

    private String getJSsetDoubleClickZoomEnabled( final boolean enabled ) {
        return getJSinvoke( "setDoubleClickZoomEnabled(" + enabled + ")" );
    }

    private String getJSsetScrollWheelZoomEnabled( final boolean enabled ) {
        return getJSinvoke( "setScrollWheelZoomEnabled(" + enabled + ")" );
    }

    private String getJSsetZoom( final int zoom ) {
        return getJSinvoke( "setZoom(" + zoom + ")" );
    }

    private String getJSsetCenter( final LatLng center ) {
        if ( center != null ) {
            return getJSinvoke( "setCenter(" + center.getJSconstructor() + ")" );
        }
        return "";
    }

    private String getJSpanDirection( final int dx, final int dy ) {
        return getJSinvoke( "panDirection(" + dx + "," + dy + ")" );
    }

    private String getJSpanTo( final LatLng center ) {
        return getJSinvoke( "panTo(" + center.getJSconstructor() + ")" );
    }

    private String getJSzoomOut() {
        return getJSinvoke( "zoomOut()" );
    }

    private String getJSzoomIn() {
        return getJSinvoke( "zoomIn()" );
    }

    /**
     * Update state from a request to an AJAX target.
     */
    public void update() {
        final Request request = RequestCycle.get().getRequest();

        // Attention: don't use setters as this will result in an endless
        // AJAX request loop
        _bounds = GLatLngBounds.parse( request.getRequestParameters().getParameterValue( "bounds" ).toString() );
        _center = LatLng.parse( request.getRequestParameters().getParameterValue( "center" ).toString() );
        _zoom = Integer.parseInt( request.getRequestParameters().getParameterValue( "zoom" ).toString() );
        _mapType = GMapType.valueOf( request.getRequestParameters().getParameterValue( "currentMapType" ).toString() );

        _infoWindow.update();
    }

    public void setOverlays( final List<GOverlay> overlays ) {
        removeAllOverlays();
        for ( final GOverlay overlay : overlays ) {
            addOverlay( overlay );
        }
    }

    private abstract class JSMethodBehavior extends AbstractBehavior {

        private static final long serialVersionUID = 1L;

        private final String _attribute;

        public JSMethodBehavior( final String attribute ) {
            _attribute = attribute;
        }

        /**
         * @see org.apache.wicket.behavior.AbstractBehavior#onComponentTag(org.apache.wicket.Component,
         *      org.apache.wicket.markup.ComponentTag)
         */
        @Override
        public void onComponentTag( final Component component, final ComponentTag tag ) {
            String invoke = getJSinvoke();

            if ( _attribute.equalsIgnoreCase( "href" ) ) {
                invoke = "javascript:" + invoke;
            }

            tag.put( _attribute, invoke );
        }

        protected abstract String getJSinvoke();
    }

    public class ZoomOutBehavior extends JSMethodBehavior {
        private static final long serialVersionUID = 1L;

        public ZoomOutBehavior( final String event ) {
            super( event );
        }

        @Override
        protected String getJSinvoke() {
            return getJSzoomOut();
        }
    }

    public class ZoomInBehavior extends JSMethodBehavior {
        private static final long serialVersionUID = 1L;

        public ZoomInBehavior( final String event ) {
            super( event );
        }

        @Override
        protected String getJSinvoke() {
            return getJSzoomIn();
        }
    }

    public class PanDirectionBehavior extends JSMethodBehavior {
        private static final long serialVersionUID = 1L;

        private final int _dx;

        private final int _dy;

        public PanDirectionBehavior( final String event, final int dx, final int dy ) {
            super( event );
            _dx = dx;
            _dy = dy;
        }

        @Override
        protected String getJSinvoke() {
            return getJSpanDirection( _dx, _dy );
        }
    }

    public class SetZoomBehavior extends JSMethodBehavior {
        private static final long serialVersionUID = 1L;

        private final int _zoomBehavior;

        public SetZoomBehavior( final String event, final int zoom ) {
            super( event );
            _zoomBehavior = zoom;
        }

        @Override
        protected String getJSinvoke() {
            return getJSsetZoom( _zoomBehavior );
        }
    }

    public class SetCenterBehavior extends JSMethodBehavior {
        private static final long serialVersionUID = 1L;

        private final LatLng _gLatLng;

        public SetCenterBehavior( final String event, final LatLng gLatLng ) {
            super( event );
            _gLatLng = gLatLng;
        }

        @Override
        protected String getJSinvoke() {
            return getJSsetCenter( _gLatLng );
        }
    }

    public class SetMapTypeBehavior extends JSMethodBehavior {
        private static final long serialVersionUID = 1L;

        private final GMapType _mapTypeBehavior;

        public SetMapTypeBehavior( final String event, final GMapType mapType ) {
            super( event );
            _mapTypeBehavior = mapType;
        }

        @Override
        protected String getJSinvoke() {
            return _mapTypeBehavior.getJSsetMapType( GMap.this );
        }
    }

    public class OverlayListener extends AbstractDefaultAjaxBehavior {
        private static final long serialVersionUID = 1L;

        @Override
        protected void respond( final AjaxRequestTarget target ) {
            final Request request = RequestCycle.get().getRequest();

            final String overlayId = request.getRequestParameters().getParameterValue( "overlay.overlayId" ).toString();
            final String event = request.getRequestParameters().getParameterValue( "overlay.event" ).toString();
            // TODO this is ugly
            // the id's of the Overlays are unique within the ArrayList
            // maybe we should change that collection
            for ( final GOverlay overlay : _overlays ) {
                if ( overlay.getId().equals( overlayId ) ) {
                    overlay.onEvent( target, GEvent.valueOf( event ) );
                    break;
                }
            }
        }

        public Object getJSinit() {
            return GMap.this.getJSinvoke( "overlayListenerCallbackUrl = '" + this.getCallbackUrl() + "'" );

        }
    }
}
