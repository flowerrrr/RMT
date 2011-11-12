package de.flower.rmt.ui.manager.component;

import de.flower.rmt.model.Team;
import de.flower.rmt.service.ITeamManager;
import de.flower.rmt.ui.common.form.DropDownChoicePanel;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class TeamSelect extends DropDownChoicePanel<Team> {

    @SpringBean
    private ITeamManager teamManager;

    public TeamSelect(String id) {
        super(id);
        setChoices(getEntityChoices());
        setChoiceRenderer(new IChoiceRenderer<Team>() {
            @Override
            public Object getDisplayValue(Team entity) {
                return entity.getName();
            }

            @Override
            public String getIdValue(Team entity, int index) {
                return entity.getId().toString();
            }
        });
    }

    private IModel<List<Team>> getEntityChoices() {
        return new LoadableDetachableModel<List<Team>>() {
            @Override
            protected List<Team> load() {
                return teamManager.findAll();
            }
        };
    }
}
