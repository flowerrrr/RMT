package de.flower.rmt.ui.page.blog;

import de.flower.common.util.Strings;
import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.service.BlogManager;
import de.flower.rmt.ui.app.PropertyProvider;
import de.flower.rmt.ui.panel.RMTBasePanel;
import de.flower.rmt.util.Dates;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author flowerrrr
 */
public class ArticleListPanel extends RMTBasePanel {

    private static final int ITEMS_PER_PAGE = 5;

    @SpringBean
    private PropertyProvider propertyProvider;

    @SpringBean
    private BlogManager blogManager;

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
                item.add(new NumCommentsLabel("numComments", item.getModel(), true));
                item.add(new Label("text", teaser(article.getText(), propertyProvider.getBlogTeaserLength())).setEscapeModelStrings(false));
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

    public static String teaser(String text, final Integer teaserLength) {
        // get first n chars (limt result to complete words), ?s = DOTALL
        String teaser = Strings.substring(text, "(?s).{0," + teaserLength + "}\\b") + " ...";
        // teaser might have open html tags. must sanitize string.
        Document doc = Jsoup.parse("<html><body>" + teaser + "</body></html>");
        Element body = doc.select("body").first();
        String sanatized = body.html();
        if (!sanatized.contains(" ...")) {
            sanatized += " ...";
        }
        return sanatized;
    }

    public static class NumCommentsLabel extends Label {

        @SpringBean
        private BlogManager blogManager;

        private boolean showWhenNoComments;

        public NumCommentsLabel(final String id, final IModel<BArticle> model, boolean showWhenNoComments) {
            super(id);
            setDefaultModel(getNumComments(model));
            this.showWhenNoComments = showWhenNoComments;
        }

        private IModel<String> getNumComments(final IModel<BArticle> model) {
            return new LoadableDetachableModel<String>() {
                @Override
                protected String load() {
                    long num = blogManager.getNumComments(model.getObject());
                    if (num == 0) {
                        return showWhenNoComments ? new ResourceModel("blog.comments.num.none").getObject() : "";
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
