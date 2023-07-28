package de.flower.rmt.ui.page.events.manager;

import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;


public class EventsPage extends ManagerBasePage {

    public EventsPage() {

        setHeading("manager.events.heading");
        addMainPanel(new EventListPanel());
        addSecondaryPanel(new EventsSecondaryPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return Pages.EVENTS.name();
    }
}
