package de.flower.common.ui.ajax.panel;

import de.flower.common.ui.ajax.behavior.AjaxSlideToggleBehavior;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.util.Check;
import de.flower.rmt.ui.panel.BasePanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.ResourceModel;

/**
 * Panel with a button and a wrapped panel. Wrapped panel is displayed when button is clicked.
 *
 * @author flowerrrr
 */
public class AjaxSlideTogglePanel extends Panel {

    private AjaxSlideToggleBehavior toggleBehavior;

    public static final String WRAPPED_PANEL_ID = "wrappedPanel";

    public AjaxSlideTogglePanel(final String id, final String toggleButtonLabelResourceKey, final BasePanel wrappedPanel) {
        super(id);
        Check.isEqual(wrappedPanel.getId(), WRAPPED_PANEL_ID);
        final AjaxLink toggleButton = new AjaxLink("toggleButton") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                if (wrappedPanel.isVisible()) {
                    toggleBehavior.hide(target);
                } else {
                    toggleBehavior.show(target);
                }
                target.add(this);
            }
        };
        Label icon = new Label("icon", "");
        icon.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return (wrappedPanel.isVisible()) ? "icon-chevron-up" : "icon-chevron-down";
            }
        }));
        toggleButton.add(icon);
        toggleButton.add(new Label("label", new ResourceModel(toggleButtonLabelResourceKey)));
        add(toggleButton);

        toggleBehavior = new AjaxSlideToggleBehavior();
        wrappedPanel.setOnCloseCallback(new BasePanel.IOnCloseCallback() {

            @Override
            public void onClose(final AjaxRequestTarget target) {
                toggleBehavior.hide(target);
                target.add(toggleButton);
            }
        });
        wrappedPanel.add(toggleBehavior);
        add(wrappedPanel);
    }
}
