package de.flower.rmt.ui.manager.page.uniforms;

import de.flower.rmt.model.Team;
import de.flower.rmt.ui.manager.ManagerBasePage;
import de.flower.rmt.ui.manager.NavigationPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

/**
 * @author flowerrrr
 */
public class UniformsPage extends ManagerBasePage {

    public UniformsPage(final IModel<Team> model) {
        super();
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
