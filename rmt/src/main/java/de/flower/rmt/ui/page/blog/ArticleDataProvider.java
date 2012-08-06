package de.flower.rmt.ui.page.blog;

import de.flower.common.ui.inject.InjectorAwareObject;
import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.model.db.entity.QBArticle;
import de.flower.rmt.service.IBlogManager;
import de.flower.rmt.ui.model.BArticleModel;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Iterator;

/**
 * @author flowerrrr
 */
public class ArticleDataProvider extends InjectorAwareObject implements IDataProvider<BArticle> {

    @SpringBean
    private IBlogManager blogManager;

    private int itemsPerPage;

    private Long size;

     public ArticleDataProvider(final int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    @Override
    public Iterator<? extends BArticle> iterator(final int first, final int count) {
        int pageNum = first / itemsPerPage;
        return blogManager.findAllArticles(pageNum, itemsPerPage, QBArticle.bArticle.author).iterator();
    }

    @Override
    public int size() {
        // method is called at least twice during rendering -> cache value
        if (size == null) {
            size = blogManager.getNumArticles();
        }
        return size.intValue();
    }

     @Override
    public IModel<BArticle> model(final BArticle object) {
        return new BArticleModel(object);
    }

    @Override
    public void detach() {
        this.size = null;
     }

}
