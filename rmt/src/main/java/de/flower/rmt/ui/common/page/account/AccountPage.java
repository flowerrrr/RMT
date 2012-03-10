package de.flower.rmt.ui.common.page.account;

import de.flower.rmt.ui.common.page.CommonBasePage;

/**
 * @author flowerrrr
 */
public class AccountPage extends CommonBasePage {

    public AccountPage() {
        setHeading("account.heading", "account.heading.sub");
        addMainPanel(new AccountTabPanel());
        // addSecondaryPanel(new Label("foobar", "Put some useful stuff here"));
    }

    @Override
    public String getActiveTopBarItem() {
        return "";
    }
}