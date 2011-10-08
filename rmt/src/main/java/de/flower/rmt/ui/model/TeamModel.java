package de.flower.rmt.ui.model;

import de.flower.rmt.model.Team;
import de.flower.rmt.service.ITeamManager;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class TeamModel extends LoadableDetachableModel<Team> {

    @SpringBean
    private ITeamManager manager;

    private Long id;

    public TeamModel(Team entity) {
        if (entity != null) {
            setObject(entity);
            this.id = entity.getId();
        }
        Injector.get().inject(this);
    }

    @Override
    protected Team load() {
        if (id == null) {
            return manager.newTeamInstance();
        } else {
            return manager.findById(id);
        }
    }
}
