package de.flower.rmt.ui.player.page.events;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.player.PlayerBasePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class EventsPage extends PlayerBasePage {

    @SpringBean
    private IEventManager eventManager;

    public EventsPage() {
        addHeading("player.events.heading");

        final UserModel userModel = new UserModel(getUser());
        addMainPanel(new EventListPanel(userModel, getUpcomingEventList(userModel)));

        addSecondaryPanel(new Label("foobar", "<h3>Letzte Rückmeldungen</h3>\n" +
                "                    12.02. - 13:34 - Flower - Saisonopening - zugesagt\n" +
                "                    <p>Comment: hallo freu mich</p>"));
    }

    private IModel<List<Event>> getUpcomingEventList(final UserModel userModel) {
        return new LoadableDetachableModel<List<Event>>() {
            @Override
            protected List<Event> load() {
                return eventManager.findUpcomingByUserPlayer(userModel.getObject());
            }
        };
    }

    @Override
     public String getActiveTopBarItem() {
         return "events";
     }

}
