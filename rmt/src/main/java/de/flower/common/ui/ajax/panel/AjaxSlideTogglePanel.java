package de.flower.common.ui.ajax.panel;

import de.flower.common.ui.ajax.behavior.AjaxSlideToggleBehavior;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.js.JQuery;
import de.flower.common.util.Check;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;

/**
 * Panel with a button and a wrapped panel. Wrapped panel is displayed when button is clicked.
 *
 * @author flowerrrr
 */
public class AjaxSlideTogglePanel extends Panel {

    private AjaxSlideToggleBehavior toggleBehavior;

    public static final String WRAPPED_PANEL_ID = "wrappedPanel";

    public AjaxSlideTogglePanel(final String id, final String fadeInButtonLabelResourceKey, final BasePanel wrappedPanel) {
        super(id);
        Check.isEqual(wrappedPanel.getId(), WRAPPED_PANEL_ID);
        final AjaxLink fadeInButton = new AjaxLink("fadeInButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                toggleBehavior.show(target);
            }
        };
        fadeInButton.add(new Label("fadeInButtonLabel", new ResourceModel(fadeInButtonLabelResourceKey)));
        add(fadeInButton);

        toggleBehavior = new AjaxSlideToggleBehavior() {
            @Override
            public void onHide(AjaxRequestTarget target) {
                target.prependJavaScript(JQuery.fadeIn(fadeInButton, "slow"));
            }
        };
        wrappedPanel.setOnCloseCallback(new BasePanel.IOnCloseCallback() {

            @Override
            public void onClose(final AjaxRequestTarget target) {
                toggleBehavior.hide(target);
            }
        });
        wrappedPanel.add(toggleBehavior);
        add(wrappedPanel);
    }
}
