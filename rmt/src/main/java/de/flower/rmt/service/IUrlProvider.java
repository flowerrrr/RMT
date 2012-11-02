package de.flower.rmt.service;

import de.flower.common.util.geo.LatLng;

/**
 * @author flowerrrr
 */
public interface IUrlProvider {

    String deepLinkEvent(Long eventId);

    String deepLinkBlog(Long articleId);

    String getDirectionsUrl(LatLng latLng);
}
