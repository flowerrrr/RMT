package de.flower.rmt.ui.page.venues.player;

import de.flower.rmt.model.Venue;
import de.flower.rmt.service.IVenueManager;
import de.flower.rmt.ui.model.VenueModel;
import de.flower.rmt.ui.page.base.player.NavigationPanel;
import de.flower.rmt.ui.page.base.player.PlayerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class VenuePage extends PlayerBasePage {

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
            // TODO (flowerrrr - 15.04.12) log violating url
            log.warn("VenuePage accessed with invalid parameter: " + e.toString());
            log.warn("Violating Request " + RequestCycle.get().getUrlRenderer().renderFullUrl(RequestCycle.get().getRequest().getUrl()));
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
