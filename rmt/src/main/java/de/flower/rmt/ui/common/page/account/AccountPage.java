package de.flower.rmt.ui.common.page.account;

import de.flower.rmt.ui.common.page.CommonBasePage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author flowerrrr
 */
public class AccountPage extends CommonBasePage {

    public AccountPage() {
        addHeading("tbd", "tbd");
        addMainPanel(new AccountMainPanel());
        addSecondaryPanel(new Label("foobar", "Put some useful stuff here"));
    }

    @Override
    public String getActiveTopBarItem() {
        return "";
    }
}