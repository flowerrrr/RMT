package de.flower.rmt.ui.model;

import de.flower.rmt.model.Team;
import de.flower.rmt.service.ITeamManager;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class TeamModel extends AbstractEntityModel<Team> {

    @SpringBean
    private ITeamManager manager;

    public TeamModel(Team entity) {
        super(entity);
    }

    public TeamModel() {
        super();
    }

    public TeamModel(final IModel<Team> model) {
        super(model);
    }

    @Override
    protected Team load(Long id) {
        return manager.loadById(id);
    }

    @Override
    protected Team newInstance() {
        return manager.newInstance();
    }

    /**
     * Team model that allows null instances.
     */
    public static class NullableTeamModel extends TeamModel {

        public NullableTeamModel(final Team entity) {
            super(entity);
        }

        @Override
        protected Team newInstance() {
            return null;
        }

        @Override
        protected boolean nullAllowed() {
            return true;
        }
    }
}
