package de.flower.rmt.ui.page.login;

import de.flower.rmt.ui.page.base.AbstractBasePage;
import de.flower.rmt.ui.page.base.AnonymousNavigationPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author flowerrrr
 */
public class LoginPage extends AbstractBasePage {

    private final static Logger log = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(final PageParameters params) {

        add(new AnonymousNavigationPanel());

        add(new WebMarkupContainer("loginFailure") {
            @Override
            public boolean isVisible() {
                return !params.get("error").isNull();
            }
        });

        // preset username field with previous value if login attempt failed
        TextField username = new TextField("j_username", Model.of(getLastUsername()));
        username.setMarkupId("j_username");
        add(username);

        add(new BookmarkablePageLink("passwordForgottenLink", PasswordForgottenPage.class));
    }

    private String getLastUsername() {
        try {
            HttpSession session = ((HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest()).getSession();
            AuthenticationException attr = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (attr == null) return null;
            Authentication auth = attr.getAuthentication();
            if (auth != null) {
                return (String) auth.getPrincipal();
            }
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
