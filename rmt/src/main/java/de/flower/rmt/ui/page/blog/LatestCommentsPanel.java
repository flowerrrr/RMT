package de.flower.rmt.ui.page.blog;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.rmt.model.db.entity.BComment;
import de.flower.rmt.model.db.entity.QBComment;
import de.flower.rmt.service.BlogManager;
import de.flower.rmt.ui.panel.RMTBasePanel;
import de.flower.rmt.util.Dates;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;


public class LatestCommentsPanel extends RMTBasePanel {

    private final static Logger log = LoggerFactory.getLogger(LatestCommentsPanel.class);

    @SpringBean
    private BlogManager blogManager;

    private int currentPage = 0;

    public LatestCommentsPanel() {
        
        add(new AjaxEventListener(BComment.class));

        ListView<BComment> list = new ListView<BComment>("list", getListModel()) {
            @Override
            protected void populateItem(final ListItem<BComment> item) {
                BComment comment = item.getModelObject();
                item.add(new Label("date", getDateLabel(comment.getCreateDate())));
                item.add(new Label("author", comment.getAuthor().getFullname()));
                Link link = new BookmarkablePageLink("link", ArticlePage.class, ArticlePage.getPageParams(comment.getArticle().getId()));
                link.add(new Label("heading", comment.getArticle().getHeading()));
                item.add(link);
            }
        };

        add(list);
    }

    private IModel<List<BComment>> getListModel() {
        return new LoadableDetachableModel<List<BComment>>() {
            @Override
            protected List<BComment> load() {
                return blogManager.findLastNComments(10, QBComment.bComment.author, QBComment.bComment.article);
            }
        };
    }

    private String getDateLabel(Date date) {
        return Dates.formatFacebook(date);
    }
}
