package de.flower.rmt.ui.model;

import de.flower.rmt.model.db.entity.Opponent;
import de.flower.rmt.service.OpponentManager;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


public class OpponentModel extends AbstractEntityModel<Opponent> {

    @SpringBean
    private OpponentManager manager;

    public OpponentModel(Opponent entity) {
        super(entity);
    }

    public OpponentModel() {
        super();
    }

    public OpponentModel(final IModel<Opponent> model) {
        super(model);
    }

    @Override
    protected Opponent load(Long id) {
        return manager.loadById(id);
    }

    @Override
    protected Opponent newInstance() {
        return manager.newInstance();
    }
}
