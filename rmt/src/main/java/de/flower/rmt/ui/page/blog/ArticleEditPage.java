package de.flower.rmt.ui.page.blog;

import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.ui.page.Pages;
import de.flower.rmt.ui.page.base.AbstractCommonBasePage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;

/**
 * edits a blog article.
 */
public class ArticleEditPage extends AbstractCommonBasePage {

    public ArticleEditPage(IModel<BArticle> model) {
        setHeadingModel(new ResourceModel("blog.heading"), new StringResourceModel("blog.article.edit.new.${new}.heading.sub", model));
        addMainPanel(new ArticleEditPanel(model));
    }

    @Override
    public String getActiveTopBarItem() {
        return Pages.BLOG.name();
    }
}
