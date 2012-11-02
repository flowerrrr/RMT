package de.flower.rmt.ui.page.venues.manager;

import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.ui.markup.html.weather.WeatherPanel;
import de.flower.rmt.ui.model.VenueModel;
import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class VenueEditPage extends ManagerBasePage {

    public VenueEditPage() {
        this(new VenueModel());
    }

    public VenueEditPage(final IModel<Venue> model) {
        super(model);
        setHeading("manager.venue.edit.heading", null);
        addMainPanel(new VenueEditPanel(model) {
            @Override
            protected void onClose(AjaxRequestTarget target) {
                setResponsePage(VenuesPage.class);
            }
        });
        addSecondaryPanel(new WeatherPanel(new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return model.getObject().getAddress();
            }
        }));
    }

    @Override
    public String getActiveTopBarItem() {
        return Pages.VENUES.name();
    }

}
