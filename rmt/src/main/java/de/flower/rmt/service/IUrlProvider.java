package de.flower.rmt.service;

import de.flower.common.util.geo.LatLng;


public interface IUrlProvider {

    String deepLinkEvent(Long eventId);

    String deepLinkEvent2(Long eventId);

    String deepLinkBlog(Long articleId);

    String getDirectionsUrl(LatLng latLng);
}
