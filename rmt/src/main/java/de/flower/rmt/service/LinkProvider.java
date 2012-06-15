package de.flower.rmt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Link provider used when WicketLinkProvider is not available (i.e. when called from service layer without an wicket request).
 *
 * @author flowerrrr
 */
@Component
public class LinkProvider implements ILinkProvider {

    @Value("${app.url}")
    private String baseUrl;

    @Value("${event.url}")
    private String eventUrl;


    @Override
    public String deepLinkEvent(final Long eventId) {
        return baseUrl + eventUrl + "/" + eventId;
    }
}
