package de.flower.rmt.ui.manager.page.venues;

import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
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
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class VenueListPanel extends BasePanel {

    @SpringBean
    private IVenueManager venueManager;

    public VenueListPanel(IModel<List<Venue>> listModel) {

        WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        add(listContainer);

        listContainer.add(new ListView<Venue>("list", listModel) {
            @Override
            protected void populateItem(final ListItem<Venue> item) {
                Link editLink = createEditLink("editLink", item);
                editLink.add(new Label("name", item.getModelObject().getName()));
                item.add(editLink);
                DropDownMenuPanel menuPanel = new DropDownMenuPanel();
                menuPanel.addLink(createEditLink("link", item), "button.edit");
                menuPanel.addLink(new AjaxLinkWithConfirmation("link", new ResourceModel("manager.venues.delete.confirm")) {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        venueManager.delete(item.getModelObject());
                        target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityDeleted(Venue.class)));
                    }
                }, "button.delete");
                item.add(menuPanel);
            }

        });
        listContainer.add(new AjaxUpdateBehavior(AjaxEvent.EntityAll(Venue.class)));
    }

    private Link createEditLink(String id, final ListItem<Venue> item) {
        return new Link(id) {
            @Override
            public void onClick() {
                setResponsePage(new VenueEditPage(new VenueModel(item.getModel())));
            }
        };
    }
}
