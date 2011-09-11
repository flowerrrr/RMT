package de.flower.rmt.ui.common.page.login;

import de.flower.rmt.ui.common.page.CommonBasePage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author oblume
 */
public class LoginPage extends CommonBasePage {

    public LoginPage(final PageParameters params) {
        add(new WebMarkupContainer("loginFailure") {
            @Override
            public boolean isVisible() {
                return !params.get("error").isNull();
            }
        });
    }
}
