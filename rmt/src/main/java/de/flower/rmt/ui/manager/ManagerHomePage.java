package de.flower.rmt.ui.manager;

/**
 * @author flowerrrr
 */
public class ManagerHomePage extends ManagerBasePage {

    public ManagerHomePage() {
        setHeading("manager.home.heading", "manager.home.heading.sub");
        addMainPanel(new ManagerHomeMainPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.HOME;
    }
}