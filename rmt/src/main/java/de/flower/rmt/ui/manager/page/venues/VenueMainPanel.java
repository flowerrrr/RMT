package de.flower.rmt.ui.manager.page.venues;

import de.flower.rmt.model.Venue;
import de.flower.rmt.service.IVenueManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.page.venues.map.VenuesMapPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class VenueMainPanel extends BasePanel {

    @SpringBean
    private IVenueManager venueManager;

    public VenueMainPanel() {
        final IModel<List<Venue>> listModel = getListModel();

        add(new WebMarkupContainer("noEntry") {
            @Override
            public boolean isVisible() {
                return listModel.getObject().isEmpty();
            }
        });
        WebMarkupContainer container = new WebMarkupContainer("container") {
            @Override
            public boolean isVisible() {
                return !listModel.getObject().isEmpty();
            }
        };
        container.add(new VenueListPanel(listModel));
        container.add(new VenuesMapPanel(listModel));
        add(container);
    }

    private IModel<List<Venue>> getListModel() {
        return new LoadableDetachableModel<List<Venue>>() {
            @Override
            protected List<Venue> load() {
                return venueManager.findAll();
            }
        };
    }
}
