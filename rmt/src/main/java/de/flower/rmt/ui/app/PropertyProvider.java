package de.flower.rmt.ui.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PropertyProvider {

    @Value("${admin.address}")
    private String adminEmail;

    @Value("${events.numpast}")
    private Integer eventsNumPast;

    @Value("${blog.teaser.length}")
    private Integer blogTeaserLength;

    public String getAdminEmail() {
        return adminEmail;
    }

    public int getEventsNumPast() {
        return eventsNumPast;
    }

    public Integer getBlogTeaserLength() {
        return blogTeaserLength;
    }
}
