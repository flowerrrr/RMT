package de.flower.rmt.ui.manager.page.events;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.model.Model;

/**
 * @author flowerrrr
 */
public class EventEditPage extends ManagerBasePage {

    public EventEditPage(Event event) {
        add(new EventEditPanel(Model.of(event)));
    }

    @Override
    public String getActiveTopBarItem() {
        return "events";
    }

}
