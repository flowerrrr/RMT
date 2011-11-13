package de.flower.rmt.ui.manager.page.squad;

import de.flower.rmt.model.Team;
import de.flower.rmt.ui.manager.ManagerBasePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class SquadPage extends ManagerBasePage {

    public SquadPage(final IModel<Team> model) {
        super();
        addHeadingText(model.getObject().getName());
        addMainPanel(new PlayerListPanel(model));
        addSecondaryPanel(new Label("foobar", "Put some useful stuff here"));

   }

    @Override
    public String getActiveTopBarItem() {
        return "teams";
    }


}
