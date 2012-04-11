package de.flower.rmt.ui.player.page.venues;

import de.flower.rmt.model.Venue;
import de.flower.rmt.ui.player.NavigationPanel;
import de.flower.rmt.ui.player.PlayerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class VenuePage extends PlayerBasePage {

    public VenuePage(IModel<Venue> model) {
        super(model);
        setHeading("player.venue.heading", null);
        addMainPanel(new VenuePanel(model) {
            @Override
            protected void onClose(AjaxRequestTarget target) {
                setResponsePage(VenuesPage.class);
            }
        });
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.VENUES;
    }

}
