package de.flower.common.ui.ajax.behavior;

import de.flower.common.ui.js.JQuery;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

/**
 * Behavior can be used to fade in / fade out panels.
 */
public class AjaxSlideToggleBehavior extends Behavior {

    private Component c;

    /**
     * If component should be faded in its visibility is set to true but the component must be
     * rendered with display: none.
     */
    private boolean beforeFadeIn;

    @Override
    public void bind(final Component component) {
        this.c = component;
        component.setOutputMarkupPlaceholderTag(true);
        component.setVisible(false);
        // component.add(AttributeModifier.append("style", component.isVisible() ? "" : "display:none"));
    }

    public void show(AjaxRequestTarget target) {
        c.setVisible(true);
        beforeFadeIn = true;
        target.add(c);
        target.appendJavaScript(getSlideDownJS());
    }

    @Override
    public void onComponentTag(final Component component, final ComponentTag tag) {
        super.onComponentTag(component, tag);
        if (beforeFadeIn) {
            tag.put("style", "display: none;");
            beforeFadeIn = false;
        }
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

    public void onHide(AjaxRequestTarget target) {
        ;
    }
}
