package de.flower.rmt.ui.manager.page.event;

import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.model.Model;

/**
 * @author flowerrrr
 */
public class EventEditPage extends ManagerBasePage {

    public EventEditPage(Event event) {
        setHeading("manager.event.edit.heading", null);
        addMainPanel(new EventEditPanel(Model.of(event)));
        // addSecondaryPanel(new Label("foobar", "Put some useful stuff here."));
    }

    @Override
    public String getActiveTopBarItem() {
        return "events";
    }

}
