package de.flower.rmt.ui.manager;

import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.rmt.ui.common.panel.LogoutLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;

/**
 * @author oblume
 */
public class ManagerHomePage extends ManagerBasePage {

    private AjaxSlideTogglePanel panel;

    private AjaxLink link;


    public ManagerHomePage() {

        add(new LogoutLink("logoutLink", this.getClass()));

     }


}