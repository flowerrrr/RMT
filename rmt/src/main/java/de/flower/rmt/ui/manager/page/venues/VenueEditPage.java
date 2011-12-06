package de.flower.rmt.ui.manager.page.venues;

import de.flower.rmt.model.Venue;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.model.VenueModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class VenueEditPage extends ManagerBasePage {

    public VenueEditPage() {
        this(new VenueModel());
    }

    public VenueEditPage(IModel<Venue> model) {
        super(model);
        addHeading("manager.venue.edit.heading", null);
        addMainPanel(new VenueEditPanel(model) {
            @Override
            protected void onClose() {
                setResponsePage(VenuesPage.class);
            }
        });
        addSecondaryPanel(new Label("foobar", "Put something useful here"));
    }

    @Override
    public String getActiveTopBarItem() {
        return "venues";
    }

}
