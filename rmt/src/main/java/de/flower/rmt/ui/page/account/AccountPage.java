package de.flower.rmt.ui.page.account;

import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.security.ISecurityService;
import de.flower.rmt.ui.model.UserModel;
import de.flower.rmt.ui.page.base.AbstractCommonBasePage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class AccountPage extends AbstractCommonBasePage {

    @SpringBean
    private ISecurityService securityService;

    public AccountPage() {
        setHeading("account.heading", "account.heading.sub");
        final IModel<User> model = new UserModel(securityService.getUser());
        addMainPanel(new AccountTabPanel(model));
        addSecondaryPanel(new TeamsPanel(model));
    }

    @Override
    public String getActiveTopBarItem() {
        return "";
    }
}