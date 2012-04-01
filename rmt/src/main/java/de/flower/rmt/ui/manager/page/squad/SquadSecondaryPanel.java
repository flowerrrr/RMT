package de.flower.rmt.ui.manager.page.squad;

import de.flower.common.ui.ajax.behavior.AjaxSlideToggleBehavior;
import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.rmt.model.Team;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class SquadSecondaryPanel extends BasePanel {

    private AjaxSlideToggleBehavior toggleBehavior;

    public SquadSecondaryPanel(IModel<Team> model) {

        add(new AjaxSlideTogglePanel("addPlayerPanel", "manager.squad.button.add", new AddPlayerPanel(AjaxSlideTogglePanel.WRAPPED_PANEL_ID, model)));
    }
}
