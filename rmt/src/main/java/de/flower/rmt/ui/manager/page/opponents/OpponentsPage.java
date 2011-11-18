package de.flower.rmt.ui.manager.page.opponents;

import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author flowerrrr
 */
public class OpponentsPage extends ManagerBasePage {

    public OpponentsPage() {
        addHeading("tbd", "tbd");
        addMainPanel(new Label("foobar", "Opponents"));
        addSecondaryPanel(new Label("foobar", "Put some useful stuff here"));
     }

    @Override
    public String getActiveTopBarItem() {
        return "opponents";
    }
}