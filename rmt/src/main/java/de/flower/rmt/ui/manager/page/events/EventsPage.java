package de.flower.rmt.ui.manager.page.events;

import de.flower.rmt.ui.manager.ManagerBasePage;

/**
 * @author flowerrrr
 */
public class EventsPage extends ManagerBasePage {

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
