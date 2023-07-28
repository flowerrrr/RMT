package de.flower.rmt.ui.page.blog;

import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.service.BlogManager;
import de.flower.rmt.ui.model.BArticleModel;
import de.flower.rmt.ui.panel.RMTBasePanel;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class BlogSecondaryPanel extends RMTBasePanel {

    @SpringBean
    private BlogManager blogManager;

    public BlogSecondaryPanel() {
        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);

        add(new BookmarkablePageLink("blogLink", BlogPage.class) {
            @Override
            public boolean isVisible() {
                // only visible on article pages
                return !findPage().getClass().equals(BlogPage.class);
            }
        });

        add(new Link("newButton") {
            @Override
            public void onClick() {
                setResponsePage(new ArticleEditPage(new BArticleModel() {
                    @Override
                    protected BArticle newInstance() {
                        return blogManager.newArticle(getUser());
                    }
                }));
            }

            @Override
            public boolean isVisible() {
                // only visible on overview page
                return findPage().getClass().equals(BlogPage.class);
            }
        });

        add(new LatestCommentsPanel());
    }
}
