package de.flower.rmt.ui.player;

import de.flower.rmt.ui.common.panel.LogoutLink;

/**
 * @author oblume
 */
public class PlayerHomePage extends PlayerBasePage {

    public PlayerHomePage() {

        add(new LogoutLink("logoutLink", this.getClass()));

    }

}