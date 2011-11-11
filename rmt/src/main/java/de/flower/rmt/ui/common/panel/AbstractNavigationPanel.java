package de.flower.rmt.ui.common.panel;

import de.flower.rmt.ui.common.page.INavigationPanelAware;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

/**
 * @author flowerrrr
 */
public class AbstractNavigationPanel extends BasePanel {

    public AbstractNavigationPanel() {
        super("navigationPanel");

        add(new LogoutLink("logoutLink"));
        add(new Label("user", getUser().getFullname()));

        setRenderBodyOnly(true);
    }

    protected Component createMenuItem(String pageName, Class<?> pageClass, final INavigationPanelAware page) {
        WebMarkupContainer li = new WebMarkupContainer(pageName);
        li.add(new BookmarkablePageLink(pageName, pageClass));
        if (page.getActiveTopBarItem().equals(pageName)) {
            li.add(new AttributeAppender("class", "active").setSeparator(" "));
        }
        return li;
    }

}
