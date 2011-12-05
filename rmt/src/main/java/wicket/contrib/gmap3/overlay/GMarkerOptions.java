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

import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GLatLng;
import wicket.contrib.gmap3.api.GValue;
import wicket.contrib.gmap3.js.ObjectLiteral;

/**
 * http://code.google.com/apis/maps/documentation/javascript/reference.html# MarkerOptions
 *
 * @author Christian Hennig (christian.hennig@freiheit.com)
 */
public class GMarkerOptions implements GValue, Cloneable {
    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    private boolean _clickable = true;
    private final String _cursor = null;
    private boolean _draggable = false;
    private final boolean _flat = false;
    private GMarkerImage _icon = null;
    private final GMap _gmap;
    private String _title;
    private GLatLng _latLng;

    private boolean _bouncy = true;

    private boolean _autoPan = false;

    private GMarkerImage _shadow = null;

    public GMarkerOptions(final GMap gmap, final GLatLng latLng) {
        _latLng = latLng;
        _gmap = gmap;
    }

    public GMarkerOptions(final GMap gmap, final GLatLng latLng, final String title) {
        this(gmap, latLng);
        _title = title;

    }

    public GMarkerOptions(final GMap gmap, final GLatLng latLng, final String title, final GMarkerImage icon,
            final GMarkerImage shadow) {
        this(gmap, latLng);
        _title = title;
        _icon = icon;
        _shadow = shadow;
    }

    @Override
    public String getJSconstructor() {
        final ObjectLiteral literal = new ObjectLiteral();

        literal.set("map", _gmap.getJsReference() + ".map");
        literal.set("position", _latLng.getJSconstructor());

        if (!_clickable) {
            literal.set("clickable", "false");
        }
        if (_cursor != null) {
            literal.set("cursor", _cursor);
        }
        if (_draggable) {
            literal.set("draggable", "true");
        }
        if (_flat) {
            literal.setString("flat", "true");
        }
        if (_icon != null) {
            literal.set("icon", _icon.getJSconstructor());
        }
        if (_shadow != null) {
            literal.set("shadow", _shadow.getJSconstructor());
        }
        if (_title != null) {
            literal.setString("title", _title);
        }
        if (!_bouncy) {
            literal.set("bouncy", "false");
        }
        if (_autoPan) {
            literal.set("autoPan", "true");
        }

        return literal.toJS();
    }

    public String getTitle() {
        return _title;
    }

    public boolean isDraggable() {
        return _draggable;
    }

    public boolean isClickable() {
        return _clickable;
    }

    public boolean isBouncy() {
        return _bouncy;
    }

    public boolean isAutoPan() {
        return _autoPan;
    }

    public GMarkerImage getIcon() {
        return _icon;
    }

    @Override
    public GMarkerOptions clone() {
        try {
            return (GMarkerOptions)super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public GMarkerOptions clickable(final boolean clickable) {
        final GMarkerOptions clone = clone();
        clone._clickable = clickable;
        return clone;
    }

    public GMarkerOptions draggable(final boolean draggable) {
        final GMarkerOptions clone = clone();
        clone._draggable = draggable;
        return clone;
    }

    public GMarkerOptions autoPan(final boolean autoPan) {
        final GMarkerOptions clone = clone();
        clone._autoPan = autoPan;
        return clone;
    }

    public GMarkerOptions bouncy(final boolean bouncy) {
        final GMarkerOptions clone = clone();
        clone._bouncy = bouncy;
        return clone;
    }

    public String getCursor() {
        return _cursor;
    }

    public boolean isFlat() {
        return _flat;
    }

    public void setLatLng(final GLatLng latLng) {
        _latLng = latLng;
    }

    public GLatLng getLatLng() {
        return _latLng;
    }

    public GMap getGmap() {
        return _gmap;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + (_autoPan ? 1231 : 1237);
        result = PRIME * result + (_bouncy ? 1231 : 1237);
        result = PRIME * result + (_clickable ? 1231 : 1237);
        result = PRIME * result + (_draggable ? 1231 : 1237);
        result = PRIME * result + ((_icon == null) ? 0 : _icon.hashCode());
        result = PRIME * result + ((_title == null) ? 0 : _title.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GMarkerOptions other = (GMarkerOptions)obj;
        if (_autoPan != other._autoPan) {
            return false;
        }
        if (_bouncy != other._bouncy) {
            return false;
        }
        if (_clickable != other._clickable) {
            return false;
        }
        if (_draggable != other._draggable) {
            return false;
        }
        if (_icon == null) {
            if (other._icon != null) {
                return false;
            }
        } else if (!_icon.equals(other._icon)) {
            return false;
        }
        if (_title == null) {
            if (other._title != null) {
                return false;
            }
        } else if (!_title.equals(other._title)) {
            return false;
        }
        return true;
    }

    public void setShadow(final GMarkerImage shadow) {
        _shadow = shadow;
    }

    public GMarkerImage getShadow() {
        return _shadow;
    }

    @Override
    public String toString() {
        return "GMarkerOptions [_clickable=" + _clickable + ", "
                + (_cursor != null ? "_cursor=" + _cursor + ", " : "") + "_draggable=" + _draggable + ", _flat="
                + _flat + ", " + (_icon != null ? "_icon=" + _icon + ", " : "")
                + (_gmap != null ? "_gmap=" + _gmap + ", " : "") + (_title != null ? "_title=" + _title + ", " : "")
                + (_latLng != null ? "_latLng=" + _latLng + ", " : "") + "_bouncy=" + _bouncy + ", _autoPan="
                + _autoPan + ", " + (_shadow != null ? "_shadow=" + _shadow : "") + "]";
    }


}