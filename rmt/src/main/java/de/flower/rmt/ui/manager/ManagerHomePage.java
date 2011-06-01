package de.flower.rmt.ui.manager;

import de.flower.rmt.ui.common.panel.LogoutLink;

/**
 * @author oblume
 */
public class ManagerHomePage extends ManagerBasePage {

    public ManagerHomePage() {

        add(new LogoutLink("logoutLink", this.getClass()));

    }

}