package de.flower.rmt.ui.manager.component;

import de.flower.rmt.model.Team;
import de.flower.rmt.model.Uniform;
import de.flower.rmt.service.IUniformManager;
import de.flower.rmt.ui.common.form.field.AbstractFormFieldPanel;
import de.flower.rmt.ui.common.form.field.DropDownChoicePanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Collections;
import java.util.List;

/**
 * @author flowerrrr
 */
public class UniformDropDownChoicePanel extends DropDownChoicePanel<Uniform> {

    @SpringBean
    private IUniformManager uniformManager;

    private IModel<Team> teamModel;

    public UniformDropDownChoicePanel(String id, final IModel<Team> teamModel) {
        super(id, new UniformDropDownChoice(AbstractFormFieldPanel.ID));
        this.teamModel = teamModel;
        setChoices(getUniformChoices());
    }

    private IModel<List<Uniform>> getUniformChoices() {
        return new LoadableDetachableModel<List<Uniform>>() {
            @Override
            protected List<Uniform> load() {
                Team team = teamModel.getObject();
                if (team == null) {
                    return Collections.emptyList();
                } else {
                    return uniformManager.findAllByTeam(team);
                }
            }
        };
    }

    public void setTeamModel(final IModel<Team> teamModel) {
        this.teamModel = teamModel;
    }

    @Override
    public void onDetach() {
        if (teamModel != null) {
            teamModel.detach();
        }
        super.onDetach();
    }
}
