package de.flower.rmt.ui.page.base.player;

import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.ui.app.IEventListProvider;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.markup.html.form.renderer.EventRenderer;
import de.flower.rmt.ui.page.base.AbstractNavigationPanel;
import de.flower.rmt.ui.page.base.INavigationPanelAware;
import de.flower.rmt.ui.page.event.player.EventPage;
import de.flower.rmt.ui.page.events.player.EventsPage;
import de.flower.rmt.ui.page.venues.player.VenuesPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class NavigationPanel extends AbstractNavigationPanel {

    public static final String EVENTS = "events";

    public static final String VENUES = "venues";

    @SpringBean
    private IEventListProvider eventListProvider;

     public NavigationPanel(INavigationPanelAware page) {
        super(View.PLAYER);

        WebMarkupContainer events = createMenuItem(EVENTS, EventsPage.class, page);
        add(events);
        events.add(new ListView<Event>("eventList", getEventListModel()) {
            @Override
            protected void populateItem(final ListItem<Event> item) {
                Link link = new Link<Long>("link", Model.of(item.getModelObject().getId())) {

                    @Override
                    public void onClick() {
                        setResponsePage(EventPage.class, EventPage.getPageParams(getModel().getObject()));
                    }
                };
                link.add(new Label("label", EventRenderer.getTypeDateSummary(item.getModelObject())));
                item.add(link);
            }
        });
        add(createMenuItem(VENUES, VenuesPage.class, page));
    }

    private IModel<List<Event>> getEventListModel() {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventListProvider.getPlayerNavbarList();
            }
        };
    }

}


