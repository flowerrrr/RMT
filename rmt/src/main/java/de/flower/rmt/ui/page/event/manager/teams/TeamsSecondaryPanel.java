package de.flower.rmt.ui.page.event.manager.teams;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.ui.page.event.manager.lineup.LineupInviteeListPanel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class TeamsSecondaryPanel extends BasePanel {

    public TeamsSecondaryPanel(final IModel<Event> model) {

        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);

        add(new LineupInviteeListPanel(model));
    }
}
