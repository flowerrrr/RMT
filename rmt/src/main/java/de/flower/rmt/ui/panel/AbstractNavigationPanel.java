package de.flower.rmt.ui.panel;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.service.security.ISecurityService;
import de.flower.rmt.service.security.UserDetailsBean;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.app.View;
import de.flower.rmt.ui.page.account.AccountPage;
import de.flower.rmt.ui.page.base.INavigationPanelAware;
import de.flower.rmt.ui.page.base.manager.ManagerHomePage;
import de.flower.rmt.ui.page.base.player.PlayerHomePage;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class AbstractNavigationPanel extends BasePanel {

    @SpringBean
    protected ISecurityService securityService;

    public AbstractNavigationPanel(View view) {
        super("navigationPanel");

        add(Links.aboutLink("about"));

        add(new BookmarkablePageLink("account", AccountPage.class));
        add(createSwitchViewLink("switchView", view));
        add(Links.logoutLink("logoutLink"));
        add(new Label("user", securityService.getUser().getFullname()));

        setRenderBodyOnly(true);
    }

    /**
     * Depending on user role (manager or player) a link is generated that
     * allows the user to switch beetween both views.
     *
     * @return
     */
    private Link createSwitchViewLink(String id, View view) {
        UserDetailsBean userDetails = securityService.getCurrentUser();
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
            li.add(AttributeModifier.append("class", "active"));
        }
        return li;
    }

    public static Component createMenuItem(String pageName, AbstractLink link, final INavigationPanelAware page) {
        WebMarkupContainer li = new WebMarkupContainer(pageName);
        li.add(link);
        if (page != null && page.getActiveTopBarItem().equals(pageName)) {
            li.add(AttributeModifier.append("class", "active"));
        }
        return li;
    }

}
