package de.flower.rmt.ui.page.account;

import de.flower.rmt.ui.page.base.AbstractCommonBasePage;

/**
 * @author flowerrrr
 */
public class AccountPage extends AbstractCommonBasePage {

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