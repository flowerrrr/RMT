package de.flower.rmt.ui.common.page.login;

import de.flower.rmt.ui.common.page.AbstractBasePage;
import de.flower.rmt.ui.common.page.AnonymousNavigationPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author flowerrrr
 */
public class LoginPage extends AbstractBasePage {

    public LoginPage(final PageParameters params) {

        add(new AnonymousNavigationPanel());

        add(new WebMarkupContainer("loginFailure") {
            @Override
            public boolean isVisible() {
                return !params.get("error").isNull();
            }
        });
    }
}
