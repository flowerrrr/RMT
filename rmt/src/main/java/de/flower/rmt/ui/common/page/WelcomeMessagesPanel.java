package de.flower.rmt.ui.common.page;

import de.flower.rmt.ui.app.RMTSession;
import de.flower.rmt.ui.common.page.account.AccountPage;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author flowerrrr
 */
public class WelcomeMessagesPanel extends BasePanel {

    public WelcomeMessagesPanel() {
        add(createPasswordChangeContainer());
    }

    private Component createPasswordChangeContainer() {
        WebMarkupContainer container = new WebMarkupContainer("passwordChangeMessage") {

            public static final String PASSWORD_CHANGE_MESSAGE = "PASSWORD_CHANGE_MESSAGE";

            {
                add(new Link("gotoAccount") {
                    @Override
                    public void onClick() {
                        // dismiss message for this session
                        setHideMessage(PASSWORD_CHANGE_MESSAGE, true);
                        setResponsePage(AccountPage.class);
                    }
                });
                add(new AjaxLink("closeButton") {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {
                        setHideMessage(PASSWORD_CHANGE_MESSAGE, true);
                        target.add(WelcomeMessagesPanel.this);
                    }
                });
            }

            @Override
            public boolean isVisible() {
                return !isHideMessage(PASSWORD_CHANGE_MESSAGE) && getUser().hasInitialPassword();
            }
        };
        return container;
    }

    private void setHideMessage(String key, boolean hide) {
        RMTSession.get().getSessionMap().put(key, hide);
    }

    private boolean isHideMessage(String key) {
        Boolean hide = (Boolean) RMTSession.get().getSessionMap().get(key);
        return (hide != null && hide);
    }
}
