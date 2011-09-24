package de.flower.rmt.ui.player;

import de.flower.rmt.ui.common.panel.LogoutLink;

/**
 * @author flowerrrr
 */
public class PlayerHomePage extends PlayerBasePage {

    public PlayerHomePage() {

        add(new LogoutLink("logoutLink", this.getClass()));

    }

}