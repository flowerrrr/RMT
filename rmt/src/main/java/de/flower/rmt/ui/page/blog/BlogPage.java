package de.flower.rmt.ui.page.blog;

import de.flower.rmt.ui.page.base.AbstractCommonBasePage;
import de.flower.rmt.ui.page.base.NavigationPanel;

/**
 * @author flowerrrr
 */
public class BlogPage extends AbstractCommonBasePage {

    public BlogPage() {
        setHeading("blog.heading", "blog.heading.sub");
        addMainPanel(new ArticleListPanel());
        addSecondaryPanel(new BlogSecondaryPanel());
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.BLOG;
    }


}
