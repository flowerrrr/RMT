package de.flower.rmt.ui.page.users;

import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.AbstractCommonBasePage;

/**
 * @author flowerrrr
 */
public class UsersPage extends AbstractCommonBasePage {

    public UsersPage() {

        setHeading("manager.players.heading", null);
        addMainPanel(new UserListPanel());
        addSecondaryPanel(new UsersSecondaryPanel());
    }


    @Override
    public String getActiveTopBarItem() {
        return Pages.USERS.name();
    }

}
