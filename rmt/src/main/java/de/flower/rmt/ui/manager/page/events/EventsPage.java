package de.flower.rmt.ui.manager.page.events;

import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;

/**
 * @author flowerrrr
 */
public class EventsPage extends ManagerBasePage {

    public EventsPage() {

        setHeading("manager.events.heading");
        addMainPanel(new EventListPanel());
        addSecondaryPanel(new EventsSecondaryPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.EVENTS;
    }
}
