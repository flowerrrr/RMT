package de.flower.rmt.ui.page.about;

import de.flower.rmt.ui.app.Links;
import de.flower.rmt.ui.panel.BasePanel;

/**
 * @author flowerrrr
 */
public class ContactPanel extends BasePanel {


    public ContactPanel() {
        add(Links.adminMailLink("adminMailLink", true));
    }

}
