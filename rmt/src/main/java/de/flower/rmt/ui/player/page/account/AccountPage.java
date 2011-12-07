package de.flower.rmt.ui.player.page.account;

import de.flower.rmt.ui.player.PlayerBasePage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author flowerrrr
 */
public class AccountPage extends PlayerBasePage {

    public AccountPage() {
        addHeading("tbd", "tbd");
        addMainPanel(new AccountMainPanel());
        addSecondaryPanel(new Label("foobar", "Put some useful stuff here"));
    }

    @Override
    public String getActiveTopBarItem() {
        return "account";
    }
}