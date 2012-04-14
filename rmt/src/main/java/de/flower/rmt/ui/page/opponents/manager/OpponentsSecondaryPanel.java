package de.flower.rmt.ui.page.opponents.manager;

import de.flower.rmt.ui.panel.BasePanel;
import org.apache.wicket.markup.html.link.Link;

/**
 * @author flowerrrr
 */
public class OpponentsSecondaryPanel extends BasePanel {

    public OpponentsSecondaryPanel() {
        add(new Link("newButton") {

            @Override
            public void onClick() {
                setResponsePage(new OpponentEditPage());
            }
        });
    }
}
