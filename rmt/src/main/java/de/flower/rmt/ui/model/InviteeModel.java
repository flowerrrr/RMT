package de.flower.rmt.ui.model;

import de.flower.common.util.Check;
import de.flower.rmt.model.Invitee;
import de.flower.rmt.service.IInviteeManager;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class InviteeModel extends AbstractEntityModel<Invitee> {

    @SpringBean
    private IInviteeManager manager;

    public InviteeModel(Invitee entity) {
        super(entity);
        Check.notNull(entity);
    }

     @Override
    protected Invitee load(Long id) {
        return manager.loadById(id);
    }

    @Override
    protected Invitee newInstance() {
        throw new UnsupportedOperationException("Model only supports persisted objects!");
    }

}
