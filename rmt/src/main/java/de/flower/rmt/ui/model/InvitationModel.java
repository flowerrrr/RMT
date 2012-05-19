package de.flower.rmt.ui.model;

import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.service.IInvitationManager;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class InvitationModel extends AbstractEntityModel<Invitation> {

    @SpringBean
    private IInvitationManager manager;

    public InvitationModel(Invitation entity) {
        super(entity);
        Check.notNull(entity);
    }

     @Override
    protected Invitation load(Long id) {
        return manager.loadById(id);
    }

    @Override
    protected Invitation newInstance() {
        throw new UnsupportedOperationException("Model only supports persisted objects!");
    }

}
