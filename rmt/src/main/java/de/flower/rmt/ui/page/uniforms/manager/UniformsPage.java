package de.flower.rmt.ui.page.uniforms.manager;

import de.flower.rmt.model.db.entity.Team;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import de.flower.rmt.ui.page.base.manager.NavigationPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * @author flowerrrr
 */
public class UniformsPage extends ManagerBasePage {

    public UniformsPage(final IModel<Team> model) {
        setHeadingText(model.getObject().getName());
        setSubheadingText(new ResourceModel("manager.uniforms.heading.sub").getObject());
        addMainPanel(new UniformListPanel(model));
        addSecondaryPanel(new UniformsSecondaryPanel(model));
   }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.TEAMS;
    }


}
