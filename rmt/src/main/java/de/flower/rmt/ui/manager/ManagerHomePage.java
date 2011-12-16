package de.flower.rmt.ui.manager;

import org.apache.wicket.markup.html.basic.Label;

/**
 * @author flowerrrr
 */
public class ManagerHomePage extends ManagerBasePage {

    public ManagerHomePage() {
        setHeading("manager.home.heading", "manager.home.heading.sub");
        addMainPanel(new Label("foobar", "Diese Seite zeigt in Zukunft die wichtigsten Daten und Ereignisse rund um Ihren Club an."));
    }

    @Override
    public String getActiveTopBarItem() {
        return "home";
    }
}