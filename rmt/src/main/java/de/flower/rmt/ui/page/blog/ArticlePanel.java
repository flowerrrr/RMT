package de.flower.rmt.ui.page.blog;

import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.service.BlogManager;
import de.flower.rmt.ui.panel.RMTBasePanel;
import de.flower.rmt.util.Dates;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Date;


public class ArticlePanel extends RMTBasePanel<BArticle> {

    @SpringBean
    private BlogManager blogManager;

    public ArticlePanel(final IModel<BArticle> model) {
        super(model);

        // posting
        // add(new Label("heading", new PropertyModel(model, "heading")));
        add(new Label("author", new PropertyModel(model, "author.fullname")));
        add(DateLabel.forDatePattern("date", new PropertyModel<Date>(getModel(), "createDate"), Dates.DATE_TIME_MEDIUM_SHORT));
        add(new ArticleListPanel.NumCommentsLabel("numComments", model, true));
        add(new Label("text", new PropertyModel(model, "text")).setEscapeModelStrings(false));

        add(new Link("editLink") {
            @Override
            public void onClick() {
                setResponsePage(new ArticleEditPage(model));
            }

            @Override
            public boolean isVisible() {
                return securityService.isCurrentUserOrManager(model.getObject().getAuthor());
            }
        });

        // comments
        add(new BCommentsPanel(model));
    }
}
