package de.flower.rmt.ui.model;

import de.flower.rmt.model.db.entity.BArticle;
import de.flower.rmt.service.BlogManager;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class BArticleModel extends AbstractEntityModel<BArticle> {

    @SpringBean
    private BlogManager manager;

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
