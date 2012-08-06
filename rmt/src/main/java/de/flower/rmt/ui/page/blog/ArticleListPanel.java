package de.flower.rmt.ui.page.blog;

import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.ui.panel.RMTBasePanel;
import de.flower.rmt.util.Dates;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;

/**
 * @author flowerrrr
 */
public class ArticleListPanel extends RMTBasePanel {

    private static final int ITEMS_PER_PAGE = 5;

    public ArticleListPanel() {
        final ArticleDataProvider dataProvider = new ArticleDataProvider(ITEMS_PER_PAGE);
        final WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        add(listContainer);
        final DataView<BArticle> dataView = new DataView<BArticle>("list", dataProvider) {
            @Override
            public boolean isVisible() {
                return getItemCount() > 0;
            }

            @Override
            protected void populateItem(final Item<BArticle> item) {
                final BArticle article = item.getModelObject();

                item.add(new Label("heading", article.getHeading()));
                item.add(new Label("author", article.getAuthor().getFullname()));
                item.add(new Label("date", Dates.formatDateTimeShortWithWeekday(article.getCreateDate())));
                item.add(new Label("numComments", "tdb"));
                item.add(new Label("text", teaser(article.getText())));
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
        return text;
    }
}
