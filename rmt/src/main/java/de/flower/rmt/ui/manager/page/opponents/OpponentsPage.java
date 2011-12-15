package de.flower.rmt.ui.manager.page.opponents;

import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author flowerrrr
 */
public class OpponentsPage extends ManagerBasePage {

    public OpponentsPage() {
        addHeading("manager.opponents.heading");
        addMainPanel(new Label("foobar", "Hier werden Sie in Kürze Ihre Gegner verwalten können."));
     }

    @Override
    public String getActiveTopBarItem() {
        return "opponents";
    }
}