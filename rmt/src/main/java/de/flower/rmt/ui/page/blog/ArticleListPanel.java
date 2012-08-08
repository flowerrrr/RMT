package de.flower.rmt.ui.page.blog;

import de.flower.common.util.Strings;
import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.service.IBlogManager;
import de.flower.rmt.ui.panel.RMTBasePanel;
import de.flower.rmt.util.Dates;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class ArticleListPanel extends RMTBasePanel {

    private static final int ITEMS_PER_PAGE = 5;

    private static final int TEASER_LENGTH = 240;

    @SpringBean
    private IBlogManager blogManager;

    public ArticleListPanel() {

        final ArticleDataProvider dataProvider = new ArticleDataProvider(ITEMS_PER_PAGE);
        final WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        listContainer.setOutputMarkupId(true);
        add(listContainer);
        final DataView<BArticle> dataView = new DataView<BArticle>("list", dataProvider) {
            @Override
            public boolean isVisible() {
                return getItemCount() > 0;
            }

            @Override
            protected void populateItem(final Item<BArticle> item) {
                final BArticle article = item.getModelObject();

                Link link = new BookmarkablePageLink("articleLink", ArticlePage.class, ArticlePage.getPageParams(article.getId()));
                link.add(new Label("heading", article.getHeading()));
                item.add(link);
                item.add(new Label("author", article.getAuthor().getFullname()));
                item.add(new Label("date", Dates.formatDateLongTimeShortWithWeekday(article.getCreateDate())));
                item.add(new NumCommentsLabel("numComments", item.getModel()));
                item.add(new MultiLineLabel("text", teaser(article.getText())));
                item.add(new BookmarkablePageLink("moreLink", ArticlePage.class, ArticlePage.getPageParams(article.getId())));
            }
        };
        dataView.setItemsPerPage(ITEMS_PER_PAGE);
        listContainer.add(dataView);

        listContainer.add(new WebMarkupContainer("noEntry") {
            @Override
            public boolean isVisible() {
                return dataView.getItemCount() == 0;
            }
        });

        listContainer.add(new AjaxPagingNavigator("pager", dataView) {
            @Override
            protected void onAjaxEvent(AjaxRequestTarget target) {
                target.add(listContainer);
            }
        });

        // listContainer.add(new AjaxEventListener(BArticle.class));
    }

    public static String teaser(String text) {
        // get first n chars (limt result to complete words), ?s = DOTALL
        return Strings.substring(text, "(?s).{0," + TEASER_LENGTH + "}\\b") + " ...";
    }

    public static class NumCommentsLabel extends Label {

        @SpringBean
        private IBlogManager blogManager;

        public NumCommentsLabel(final String id, final IModel<BArticle> model) {
            super(id);
            setDefaultModel(getNumComments(model));
        }

        private IModel<String> getNumComments(final IModel<BArticle> model) {
            return new LoadableDetachableModel<String>() {
                @Override
                protected String load() {
                    long num = blogManager.getNumComments(model.getObject());
                    if (num == 0) {
                        return "";
                    } else if (num == 1) {
                        return num + " " + new ResourceModel("blog.comments.num.one").getObject();
                    } else {
                        return num + " " + new ResourceModel("blog.comments.num.more").getObject();
                    }
                }
            };
        }
    }
}
