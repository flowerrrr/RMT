package de.flower.rmt.ui.model;

import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.service.IBlogManager;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class BArticleModel extends AbstractEntityModel<BArticle> {

    @SpringBean
    private IBlogManager manager;

    public BArticleModel(BArticle entity) {
        super(entity);
    }

    public BArticleModel() {
       super();
    }

    public BArticleModel(final IModel<BArticle> model) {
        super(model);
    }

    @Override
    protected BArticle load(Long id) {
        return manager.loadArticleById(id);
    }

}
