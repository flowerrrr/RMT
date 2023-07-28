package de.flower.rmt.ui.page.venues.player;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.service.VenueManager;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class VenueListPanel extends BasePanel {

    @SpringBean
    private VenueManager venueManager;

    public VenueListPanel(IModel<List<Venue>> listModel) {

        WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        add(listContainer);

        listContainer.add(new EntityListView<Venue>("list", listModel) {
            @Override
            protected void populateItem(final ListItem<Venue> item) {
                Link link = createViewLink("link", item);
                link.add(new Label("name", item.getModelObject().getName()));
                item.add(link);
            }
        });
        listContainer.add(new AjaxEventListener(Venue.class));
    }

    private Link createViewLink(String id, final ListItem<Venue> item) {
        return new BookmarkablePageLink(id, VenuePage.class, VenuePage.getPageParams(item.getModelObject().getId()));
    }
}
