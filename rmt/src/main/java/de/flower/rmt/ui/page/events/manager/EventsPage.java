package de.flower.rmt.ui.page.events.manager;

import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import de.flower.rmt.ui.page.base.manager.NavigationPanel;

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
