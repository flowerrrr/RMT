package de.flower.rmt.ui.player.page.venues;

import de.flower.rmt.model.Venue;
import de.flower.rmt.ui.manager.page.venues.VenueMainPanel;
import de.flower.rmt.ui.player.NavigationPanel;
import de.flower.rmt.ui.player.PlayerBasePage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public class VenuesPage extends PlayerBasePage {

    public VenuesPage() {

        setHeading("manager.venues.heading", null);
        addMainPanel(new VenueMainPanel() {
            @Override
            protected Panel getListPanel(final IModel<List<Venue>> listModel) {
                return new VenueListPanel(listModel);
            }
        });
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.VENUES;
    }
}
