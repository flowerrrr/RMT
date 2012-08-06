package de.flower.rmt.ui.page.blog;

import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.model.db.entity.BComment;
import de.flower.rmt.model.db.entity.QBComment;
import de.flower.rmt.service.IBlogManager;
import de.flower.rmt.ui.panel.RMTBasePanel;
import de.flower.rmt.util.Dates;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Date;
import java.util.List;

/**
 * @author flowerrrr
 */
public class ArticlePanel extends RMTBasePanel {

    @SpringBean
    private IBlogManager blogManager;

    public ArticlePanel(final IModel<BArticle> model) {
        super(model);

        // posting
        add(new Label("heading", new PropertyModel(model, "heading")));
        add(new Label("author", new PropertyModel(model, "author.fullname")));
        add(DateLabel.forDatePattern("date", new PropertyModel<Date>(getModel(), "createDate"), Dates.DATE_TIME_MEDIUM_SHORT));
        add(new Label("numComments", "tdb"));
        add(new Label("text", new PropertyModel(model, "text")));

        // comments
        ListView<BComment> comments = new ListView<BComment>("comments", getListModel(model)) {
            @Override
            protected void populateItem(final ListItem<BComment> item) {
                BComment comment = item.getModelObject();
                item.add(new Label("author", comment.getAuthor().getFullname()));
                item.add(new Label("date", Dates.formatDateTimeShortWithWeekday(comment.getCreateDate())));
                item.add(new Label("text", comment.getText()));
            }
        };
        add(comments);

        // new comment form
        add(new CommentFormPanel(model));

    }

    private IModel<List<BComment>> getListModel(final IModel<BArticle> model) {
        return new LoadableDetachableModel<List<BComment>>() {
            @Override
            protected List<BComment> load() {
                return blogManager.findAllComments(model.getObject(), QBComment.bComment.author);
            }
        } ;
    }
}
