package de.flower.rmt.ui.common.page;

import de.flower.rmt.ui.app.AbstractBasePage;
import de.flower.rmt.ui.common.page.login.LoginPage;
import org.apache.wicket.RestartResponseException;

/**
 * @author flowerrrr
 */
public class HomePage extends AbstractBasePage {

    public HomePage() {
        throw new RestartResponseException(LoginPage.class);
    }
}
