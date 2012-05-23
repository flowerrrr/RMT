package de.flower.rmt.ui.page.login;

import de.flower.rmt.ui.page.base.AbstractBasePage;
import de.flower.rmt.ui.page.base.AnonymousNavigationPanel;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author flowerrrr
 */
public class LoginPage extends AbstractBasePage {

    private static String[] links = new String[]{
            "http://www.buerlecithin.de/",
            "http://www.dr-poehlmann.de/poehlmann/taiginseng/",
            // "http://www.gedaechtnistraining.net/",
            // "http://www.oekotest.de/cgi/index.cgi?artnr=38460;bernr=06;co=",
            // "http://www.lizzynet.de/wws/32371748.php",
            "http://keepass.info/",
    };

    public LoginPage(final PageParameters params) {

        add(new AnonymousNavigationPanel());

        add(new WebMarkupContainer("loginFailure") {
            @Override
            public boolean isVisible() {
                return !params.get("error").isNull();
            }
        });

        add(new ExternalLink("passwordForgottenLink", getLinkModel()));
    }

    private IModel<String> getLinkModel() {
        return new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return links[RandomUtils.nextInt(links.length)];
            }
        };
    }
}
