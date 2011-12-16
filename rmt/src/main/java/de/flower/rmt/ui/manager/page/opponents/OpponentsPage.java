package de.flower.rmt.ui.manager.page.opponents;

import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author flowerrrr
 */
public class OpponentsPage extends ManagerBasePage {

    public OpponentsPage() {
        setHeading("manager.opponents.heading", null);
        addMainPanel(new Label("foobar", "Hier werden Sie in Kürze Ihre Gegner verwalten können."));
     }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.OPPONENTS;
    }
}