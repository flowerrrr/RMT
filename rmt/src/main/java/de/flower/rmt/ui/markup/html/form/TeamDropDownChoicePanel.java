package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.service.TeamManager;
import de.flower.rmt.ui.markup.html.form.field.DropDownChoicePanel;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class TeamDropDownChoicePanel extends DropDownChoicePanel<Team> {

    @SpringBean
    private TeamManager teamManager;

    public TeamDropDownChoicePanel(String id) {
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
