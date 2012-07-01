package de.flower.rmt.ui.page.event.manager.lineup;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.event.Event;
import org.apache.wicket.model.IModel;

/**
 *
 * @author flowerrrr
 */
public class LineupSecondaryPanel extends BasePanel {

    public LineupSecondaryPanel(IModel<Event> model) {

        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);

        add(new InviteeListPanel(model));
    }
}
