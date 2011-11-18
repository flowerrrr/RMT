package de.flower.rmt.ui.manager.page.events;

import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class EventsPage extends ManagerBasePage {

    @SpringBean
    private IEventManager eventManager;

    public EventsPage() {

        addHeading("manager.events.heading");
        addMainPanel(new EventListPanel());
        addSecondaryPanel(new EventsSecondaryPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return "events";
    }
}
