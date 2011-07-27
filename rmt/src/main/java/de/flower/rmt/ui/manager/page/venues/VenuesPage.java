package de.flower.rmt.ui.manager.page.venues;

import de.flower.common.ui.ajax.AjaxLinkWithConfirmation;
import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.AjaxUpdateBehavior;
import de.flower.common.ui.ajax.updatebehavior.events.Event;
import de.flower.rmt.model.Venue;
import de.flower.rmt.service.IVenueManager;
import de.flower.rmt.ui.common.page.ModalDialogWindow;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.page.venues.panel.GMapPanel;
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
 * @author oblume
 */
public class VenuesPage extends ManagerBasePage {

    @SpringBean
    private IVenueManager venueManager;

    public VenuesPage() {

        final ModalDialogWindow modal = new ModalDialogWindow("venueDialog");
        final VenueEditPanel venueEditPanel = new VenueEditPanel(modal.getContentId());
        modal.setContent(venueEditPanel);
        add(modal);

        add(new MyAjaxLink("newButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                // show modal dialog with venue edit form.
                venueEditPanel.init(null);
                modal.show(target);
            }
        });

        WebMarkupContainer venueListContainer = new WebMarkupContainer("venueListContainer");
        add(venueListContainer);
        venueListContainer.add(new ListView<Venue>("venueList", getVenueListModel()) {

            @Override
            protected void populateItem(final ListItem<Venue> item) {
                item.add(new Label("name", item.getModelObject().getName()));
                item.add(new MyAjaxLink("editButton") {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        venueEditPanel.init(item.getModel());
                        modal.show(target);
                    }
                });
                item.add(new AjaxLinkWithConfirmation("deleteButton", new ResourceModel("manager.venues.delete.confirm")) {

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        venueManager.delete(item.getModelObject());
                        target.registerRespondListener(new AjaxRespondListener(Event.EntityDeleted(Venue.class)));
                    }
                });
            }
        });
        venueListContainer.add(new AjaxUpdateBehavior(Event.EntityAll(Venue.class)));

        add(new GMapPanel("gmap") {
            @Override
            public boolean isVisible() {
                return false;
            }
        });
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
