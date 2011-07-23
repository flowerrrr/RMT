package wicket.contrib.gmap3;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.WicketAjaxReference;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public class GMapHeaderContributor extends Behavior {
    private static final long serialVersionUID = 1L;

    // URL for Google Maps' API endpoint.
    private static final String GMAP_API_URL = "://maps.google.com/maps/api/js?v=3&sensor=false";

    private static final String HTTP = "http";

    // We have some custom Javascript.
    private static final ResourceReference WICKET_GMAP_JS = new JavaScriptResourceReference( GMap.class, "wicket-gmap.js" );

    protected static final String EMPTY = "";

    String _schema;

    String _clientId;

    public GMapHeaderContributor() {
        this( HTTP, null );
    }

    public GMapHeaderContributor( final String schema, final String clientId ) {
        this._schema = schema;
        this._clientId = clientId;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response ) {
        final String clientParm;
        if ( _clientId != null && !EMPTY.equals( _clientId ) ) {
            clientParm = "&client=" + _clientId;
        } else {
            clientParm = EMPTY;
        }
        response.renderJavaScriptReference(_schema + GMAP_API_URL + clientParm);
        response.renderJavaScriptReference(WicketEventReference.INSTANCE);
        response.renderJavaScriptReference(WicketAjaxReference.INSTANCE);
        response.renderJavaScriptReference(WICKET_GMAP_JS);

    }



    String getClientId() {
        return _clientId;
    }
}
