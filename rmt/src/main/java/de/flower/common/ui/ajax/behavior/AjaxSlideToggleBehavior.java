package de.flower.common.ui.ajax.behavior;

import de.flower.common.ui.ajax.panel.AjaxSlideTogglePanel;
import de.flower.common.ui.js.JQuery;
import de.flower.common.util.Check;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;

/**
 * Behavior can be used to fade in / fade out panels.
 *
 * @author flowerrrr
 */
public class AjaxSlideToggleBehavior extends Behavior {

    private Component c;

    @Override
    public void bind(final Component component) {
        this.c = component;
        component.setOutputMarkupPlaceholderTag(true);
        component.add(AttributeModifier.append("style", "display:none"));
        component.setVisible(false);
    }

    public void show(AjaxRequestTarget target) {
        c.setVisible(true);
        target.add(c);
        target.appendJavaScript(getSlideDownJS());
    }

    public final void hide(AjaxRequestTarget target) {
        c.setVisible(false);
        // use undocumented wicket feature to delay other ajax processing steps until animation completes.
        // see wicket-ajax.js#processEvaluation()
        String identifier = "processNext";
        String code = JQuery.slideUp(c, "slow", identifier + "()");
        target.prependJavaScript(identifier + "|" + code);
        target.add(c);
        onHide(target);
    }

    private CharSequence getSlideDownJS() {
        return JQuery.slideDown(c, "slow");
    }

    private CharSequence getSlideUpJS() {
        return JQuery.slideUp(c, "slow");
    }

    public static void hideCurrent(Component component, AjaxRequestTarget target) {
        AjaxSlideTogglePanel panel = Check.notNull(component.findParent(AjaxSlideTogglePanel.class));
        panel.hide(target);
    }

    public void onHide(AjaxRequestTarget target) {
        ;
    }
}
