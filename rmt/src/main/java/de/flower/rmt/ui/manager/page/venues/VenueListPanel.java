package de.flower.rmt.ui.manager.page.venues;

import de.flower.common.ui.ajax.AjaxLinkWithConfirmation;
import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.Venue;
import de.flower.rmt.service.IVenueManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.common.panel.DropDownMenuPanel;
import de.flower.rmt.ui.model.VenueModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class VenueListPanel extends BasePanel {

    @SpringBean
    private IVenueManager venueManager;

    public VenueListPanel() {
        super();

        final IModel<List<Venue>> listModel = getVenueListModel();

        WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        add(listContainer);
        listContainer.add(new WebMarkupContainer("noEntry") {
            @Override
            public boolean isVisible() {
                return listModel.getObject().isEmpty();
            }
        });

        listContainer.add(new ListView<Venue>("venueList", listModel) {

            @Override
            protected void populateItem(final ListItem<Venue> item) {
                item.add(new Label("name", item.getModelObject().getName()));
                DropDownMenuPanel menuPanel = new DropDownMenuPanel();
                menuPanel.add(new MyAjaxLink("editButton") {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        setResponsePage(new VenueEditPage(new VenueModel(item.getModelObject())));
                    }
                });
                menuPanel.add(new AjaxLinkWithConfirmation("deleteButton", new ResourceModel("manager.venues.delete.confirm")) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        venueManager.delete(item.getModelObject());
                        target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityDeleted(Venue.class)));
                    }
                });
                item.add(menuPanel);
            }
        });
        listContainer.add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Venue.class)));
    }

    private IModel<List<Venue>> getVenueListModel() {
        return new LoadableDetachableModel<List<Venue>>() {
            @Override
            protected List<Venue> load() {
                return venueManager.findAll();
            }
        };
    }
}
