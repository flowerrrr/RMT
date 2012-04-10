package de.flower.rmt.ui.common.panel;

import de.flower.rmt.service.security.UserDetailsBean;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.common.page.INavigationPanelAware;
import de.flower.rmt.ui.common.page.account.AccountPage;
import de.flower.rmt.ui.manager.ManagerHomePage;
import de.flower.rmt.ui.player.PlayerHomePage;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;


/**
 * @author flowerrrr
 */
public class AbstractNavigationPanel extends BasePanel {

    public AbstractNavigationPanel(View view) {
        super("navigationPanel");

        add(new BookmarkablePageLink("account", AccountPage.class));
        add(createSwitchViewLink("switchView", view));
        add(new LogoutLink("logoutLink"));
        add(new Label("user", getUserDetails().getUser().getFullname()));

        setRenderBodyOnly(true);
    }

    /**
     * Depending on user role (manager or player) a link is generated that
     * allows the user to switch beetween both views.
     *
     * @return
     */
    private Link createSwitchViewLink(String id, View view) {
        UserDetailsBean userDetails = getUserDetails();
        Link link;
        if (view == View.MANAGER) {
            // manager can always switch to player view.
            link = new BookmarkablePageLink(id, PlayerHomePage.class);
        } else {
            // player can only switch to manager mode if he has MANAGER role
            link = new BookmarkablePageLink(id, ManagerHomePage.class);
            link.setVisible(userDetails.isManager());
        }
        return link;
    }

    public static Component createMenuItem(String pageName, Class<?> pageClass, final INavigationPanelAware page) {
        WebMarkupContainer li = new WebMarkupContainer(pageName);
        li.add(new BookmarkablePageLink(pageName, pageClass));
        if (page != null && page.getActiveTopBarItem().equals(pageName)) {
            li.add(new AttributeAppender("class", "active").setSeparator(" "));
        }
        return li;
    }

}
