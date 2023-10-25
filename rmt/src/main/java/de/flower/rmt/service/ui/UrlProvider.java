package de.flower.rmt.service.ui;

import de.flower.common.util.geo.LatLng;
import de.flower.rmt.service.IUrlProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Url provider used when WicketUrlProvider is not available (i.e. when called from service layer without an wicket request).
 */
@Component
public class UrlProvider implements IUrlProvider {

    @Value("${app.url}")
    private String baseUrl;

    @Value("${app.das-tool-2.url}")
    private String dasTool2Url;

    @Value("${event.url}")
    private String eventUrl;


    @Override
    public String deepLinkEvent(final Long eventId) {
        return baseUrl + eventUrl + "/" + eventId;
    }

    @Override
    public String deepLinkEvent2(final Long eventId) {
        return dasTool2Url + "/event/" + eventId;
    }

    @Override
    public String deepLinkBlog(final Long articleId) {
        throw new UnsupportedOperationException("Use wicket link provider to generate blog perm links");
    }

    @Override
    public String getDirectionsUrl(final LatLng latLng) {
        DecimalFormat format = new DecimalFormat("##.##############", DecimalFormatSymbols.getInstance(Locale.US));
        String lat = format.format(latLng.getLat());
        String lng = format.format(latLng.getLng());
        return "https://maps.google.com/maps?daddr=" + lat + "," + lng;
    }
}
