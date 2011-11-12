package de.flower.rmt.ui.manager;

import org.apache.wicket.markup.html.basic.Label;

/**
 * @author flowerrrr
 */
public class ManagerHomePage extends ManagerBasePage {

    public ManagerHomePage() {
        addHeading("tbd", "tbd");
        addMainPanel(new Label("foobar", "Hello world"));
        addSecondaryPanel(new Label("foobar", "Hello world"));
    }

    @Override
    public String getActiveTopBarItem() {
        return "home";
    }
}