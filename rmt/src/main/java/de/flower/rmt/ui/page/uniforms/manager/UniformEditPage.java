package de.flower.rmt.ui.page.uniforms.manager;

import de.flower.rmt.model.Team;
import de.flower.rmt.model.Uniform;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import de.flower.rmt.ui.page.base.manager.NavigationPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class UniformEditPage extends ManagerBasePage {

    public UniformEditPage(final IModel<Uniform> model, final IModel<Team> teamModel) {
        setHeading("manager.uniform.edit.heading", null);
        addMainPanel(new UniformEditPanel(model) {
            @Override
            protected void onClose(AjaxRequestTarget target) {
                setResponsePage(new UniformsPage(teamModel));
            }
        });
        addSecondaryPanel();
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.TEAMS;
    }

}
