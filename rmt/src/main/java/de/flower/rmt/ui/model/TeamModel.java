package de.flower.rmt.ui.model;

import de.flower.rmt.model.Team;
import de.flower.rmt.service.ITeamManager;
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

    @Override
    protected Team load(Long id) {
        if (id == null) {
            return manager.newTeamInstance();
        } else {
            return manager.findById(id);
        }
    }
}
