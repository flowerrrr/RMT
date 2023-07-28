package de.flower.rmt.ui.page.users;

import de.flower.rmt.service.UserManager;
import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.markup.html.panel.SearchFilterPanel;
import de.flower.rmt.ui.page.user.manager.PlayerPage;
import de.flower.rmt.ui.panel.RMTBasePanel;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.mail.internet.InternetAddress;
import java.util.List;

/**
 * @author flowerrrr
 */
public class UsersSecondaryPanel extends RMTBasePanel {

    @SpringBean
    private UserManager userManager;

    public UsersSecondaryPanel() {
        setRenderBodyOnly(true);
        add(new Link("newButton") {

            @Override
            public void onClick() {
                setResponsePage(new PlayerPage());
            }

            @Override
            public boolean isVisible() {
                return isManagerView();
            }
        });

        add(new SearchFilterPanel());

        add(Links.mailLink("allMailLink", new LoadableDetachableModel<List<InternetAddress>>() {
            @Override
            protected List<InternetAddress> load() {
                return userManager.getAddressesForfAllUsers();
            }
        }));

    }
}
