package de.flower.rmt.ui.manager.page.event;

import de.flower.common.ui.ajax.behavior.AjaxSlideToggleBehavior;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.js.JQuery;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class InviteeSecondaryPanel extends BasePanel {

    private AjaxSlideToggleBehavior toggleBehavior;

    public InviteeSecondaryPanel(IModel<Event> model) {

        final AjaxLink addButton = new AjaxLink("addButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                toggleBehavior.show(target);
            }
        };
        add(addButton);

        Panel addInviteePanel = new AddInviteePanel(model) {
            @Override
            protected void onClose(AjaxRequestTarget target) {
                toggleBehavior.hide(target);
            }
        };
        toggleBehavior = new AjaxSlideToggleBehavior() {
            @Override
            public void onHide(AjaxRequestTarget target) {
                target.prependJavaScript(JQuery.fadeIn(addButton, "slow"));
            }
        };
        addInviteePanel.add(toggleBehavior);
        add(addInviteePanel);
    }
}
