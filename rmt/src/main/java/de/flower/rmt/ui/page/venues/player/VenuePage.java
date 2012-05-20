package de.flower.rmt.ui.page.venues.player;

import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.service.IVenueManager;
import de.flower.rmt.ui.model.VenueModel;
import de.flower.rmt.ui.page.base.player.NavigationPanel;
import de.flower.rmt.ui.page.base.player.PlayerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flowerrrr
 */
public class VenuePage extends PlayerBasePage {

    private final static Logger log = LoggerFactory.getLogger(VenuePage.class);

    public final static String PARAM_VENUEID = "venue";

    @SpringBean
    private IVenueManager venueManager;

    /**
     * Deep link support
     *
     * @param params
     */
    public VenuePage(PageParameters params) {
        Venue venue = null;
        try {
            Long id = params.get(PARAM_VENUEID).toLong();
            venue = venueManager.loadById(id);
        } catch (Exception e) {
            log.warn("VenuePage accessed with invalid parameter: " + e.toString());
            throw new AbortWithHttpErrorCodeException(404, "Invalid page parameter: " + e.getMessage());
        }
        init(new VenueModel(venue));
    }

    public static PageParameters getPageParams(Long venueId) {
        return new PageParameters().set(PARAM_VENUEID, venueId);
    }

    public VenuePage(IModel<Venue> model) {
        init(model);
    }

    private void init(IModel<Venue> model) {
        setDefaultModel(model);
        setHeading("player.venue.heading", null);
        addMainPanel(new VenuePanel(model) {
            @Override
            protected void onClose(AjaxRequestTarget target) {
                setResponsePage(VenuesPage.class);
            }
        });
        addSecondaryPanel(new GetDirectionsPanel(model), new WeatherPanel(model));
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.VENUES;
    }
}
