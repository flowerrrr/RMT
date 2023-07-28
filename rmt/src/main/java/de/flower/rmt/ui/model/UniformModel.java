package de.flower.rmt.ui.model;

import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.model.db.entity.Uniform;
import de.flower.rmt.service.UniformManager;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class UniformModel extends AbstractEntityModel<Uniform> {

    @SpringBean
    private UniformManager manager;

    private IModel<Team> teamModel;

    public UniformModel(Uniform entity) {
        super(entity);
        if (entity.isNew()) {
            this.teamModel = new TeamModel(entity.getTeam());
        }
    }

    public UniformModel(final IModel<Uniform> model) {
        super(model);
    }

    @Override
    protected Uniform load(Long id) {
        return manager.loadById(id);
    }

    @Override
    protected Uniform newInstance() {
        return manager.newInstance(teamModel.getObject());
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        if (teamModel != null) {
            teamModel.detach();
        }
    }
}
