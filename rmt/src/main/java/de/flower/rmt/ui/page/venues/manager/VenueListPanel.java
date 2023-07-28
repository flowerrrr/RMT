package de.flower.rmt.ui.page.venues.manager;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.service.VenueManager;
import de.flower.rmt.ui.model.VenueModel;
import de.flower.rmt.ui.panel.DropDownMenuPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;


public class VenueListPanel extends BasePanel {

    @SpringBean
    private VenueManager venueManager;

    public VenueListPanel(IModel<List<Venue>> listModel) {

        WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        add(listContainer);

        listContainer.add(new EntityListView<Venue>("list", listModel) {
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
                        venueManager.delete(item.getModelObject().getId());
                        AjaxEventSender.entityEvent(this, Venue.class);
                    }
                }, "button.delete");
                item.add(menuPanel);
            }

        });
        listContainer.add(new AjaxEventListener(Venue.class));
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
