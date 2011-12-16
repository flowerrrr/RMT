package de.flower.rmt.ui.app;

import de.flower.common.util.geo.LatLng;
import de.flower.rmt.service.security.ISecurityService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author flowerrrr
 */
public class RMTSession extends WebSession {

    private LatLng latLng;

    // TODO (flowerrrr - 16.12.11) remove if alertMessagePanel does not use it.
    private Map<String, Serializable> sessionMap = new HashMap<String, Serializable>();

    @SpringBean
    private ISecurityService securityService;

    public RMTSession(Request request) {
        super(request);
        Injector.get().inject(this);
        initLocation();
    }

    public static RMTSession get() {
        return (RMTSession) WebSession.get();
    }

    /**
     * Determine location based on IP address of browser.
     */
    private void initLocation() {
        // TODO (flowerrrr - 24.07.11) replace with real implementation
        // latLng = iplocationtools.com/api?....
        // latLng = configurationService.getDefaultLocation();
        // latLng = user.getClub().getLatLng();
        latLng = new LatLng(48.13724243994332, 	11.575392225925508); // München Marienplatz
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public Map<String, Serializable> getSessionMap() {
        return sessionMap;
    }
}
