package de.flower.rmt.ui.page.blog;

import de.flower.rmt.security.SecurityService;
import de.flower.rmt.service.BlogManager;
import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.AbstractCommonBasePage;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class BlogPage extends AbstractCommonBasePage {

    public BlogPage() {
        setHeading("blog.heading", "blog.heading.sub");
        addMainPanel(new ArticleListPanel());
        addSecondaryPanel(new BlogSecondaryPanel());
        add(new BlogMarkAllReadBehavior());
    }

    @Override
    public String getActiveTopBarItem() {
        return Pages.BLOG.name();
    }

    // current solution is quite simple. whenever user is in blog-modul the all read flag is set.
    public static class BlogMarkAllReadBehavior extends Behavior {

        @SpringBean
        private BlogManager blogManager;

        @SpringBean
        private SecurityService securityService;

        @Override
        public void onConfigure(final Component component) {
            super.onConfigure(component);
            blogManager.markAllRead(securityService.getUser());
        }
    }
}
