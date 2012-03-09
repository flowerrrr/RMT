package de.flower.rmt.ui.model;

import de.flower.rmt.model.Opponent;
import de.flower.rmt.service.IOpponentManager;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class OpponentModel extends AbstractEntityModel<Opponent> {

    @SpringBean
    private IOpponentManager manager;

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
