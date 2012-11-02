package de.flower.rmt.ui.page.venues.player;

import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.player.PlayerBasePage;
import de.flower.rmt.ui.page.venues.manager.VenueMainPanel;
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
        return Pages.VENUES.name();
    }
}
