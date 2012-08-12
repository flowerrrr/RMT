package de.flower.rmt.ui.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author flowerrrr
 */
@Component
public class PropertyProvider implements IPropertyProvider {

    @Value("${admin.address}")
    private String adminEmail;

    @Value("${events.numpast}")
    private Integer eventsNumPast;

    @Value("${blog.teaser.length}")
    private Integer blogTeaserLength;

    @Override
    public String getAdminEmail() {
        return adminEmail;
    }

    @Override
    public int getEventsNumPast() {
        return eventsNumPast;
    }

    public Integer getBlogTeaserLength() {
        return blogTeaserLength;
    }
}
