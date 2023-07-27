package de.flower.rmt.ui.page.venues.manager;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.service.IVenueManager;
import de.flower.rmt.ui.page.venues.manager.map.VenuesMapPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class VenuesMainPanel extends BasePanel {

    @SpringBean
    private IVenueManager venueManager;

    public VenuesMainPanel() {
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
        container.add(getListPanel(listModel));
        container.add(getMapPanel(listModel));
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

    protected Panel getListPanel(final IModel<List<Venue>> listModel) {
        return new VenueListPanel(listModel);
    }

    protected Panel getMapPanel(final IModel<List<Venue>> listModel) {
        return new VenuesMapPanel(listModel);
    }
}
