package de.flower.rmt.ui.app;

import de.flower.common.util.Collections;
import de.flower.common.util.geo.LatLng;
import de.flower.rmt.ui.page.about.AboutPage;
import de.flower.rmt.ui.page.event.player.EventPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.mail.internet.InternetAddress;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

/**
 * @author flowerrrr
 */
public class Links {

    public static BookmarkablePageLink eventLink(String id, Long eventId) {
        return new BookmarkablePageLink(id, EventPage.class, new PageParameters().set(EventPage.PARAM_EVENTID, eventId));
    }

    public static Link homePage(final String id) {
        return new Link(id) {

            @Override
            public void onClick() {
                setResponsePage(getApplication().getHomePage());
            }
        };
    }

    public static ExternalLink mailLink(String id, String emailAddress, boolean label) {
        ExternalLink link = mailLink(id, emailAddress, label ? emailAddress : null);
        return link;
    }

    public static ExternalLink mailLink(final String id, String emailAddress, final String label) {
        if (label == null) {
            return new ExternalLink(id, "mailto:" + emailAddress);
        } else {
            return new ExternalLink(id, "mailto:" + emailAddress, label);
        }
    }

    public static ExternalLink mailLink(final String id, List<InternetAddress> emailAddresses) {

        List<String> stringList = Collections.convert(emailAddresses,
                new Collections.IElementConverter<InternetAddress, String>() {
                    @Override
                    public String convert(final InternetAddress ia) {
                        // fix for RMT-614 (umlaute pose problems in mailto-links)
                        return ia.getAddress();
                        // return ia.toString();
                    }
                });
        // outlook likes ';', iphone mail client prefers ','. but according to most sources ';' is correct when used in mailto.
        // could try to detect user agent
        String href = StringUtils.join(stringList, ";");
        href = "mailTo:" + URLEncoder.encode(href);
        return new ExternalLink(id, href);
    }

    public static String getDirectionsUrl(final LatLng latLng) {
        DecimalFormat format = new DecimalFormat("##.##############", DecimalFormatSymbols.getInstance(Locale.US));
        String lat = format.format(latLng.getLat());
        String lng = format.format(latLng.getLng());
        return "http://maps.google.com/maps?daddr=" + lat + "," + lng;
    }

    public static Link aboutLink(final String id) {
        return new BookmarkablePageLink(id, AboutPage.class);
    }

    public static Component logoutLink(final String id) {
        return new ExternalLink(id, "/j_spring_security_logout").setContextRelative(true);
    }
}
