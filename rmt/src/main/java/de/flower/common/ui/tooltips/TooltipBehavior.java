package de.flower.common.ui.tooltips;

import de.flower.common.util.Check;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class TooltipBehavior extends Behavior {

    public static final String TOOLTIP_JS = "$(function () { $(\"[rel=tooltip]\").tooltip({ live: false }) })";

    private IModel<String> model;

    public TooltipBehavior(final IModel<String> model) {
        this.model = model;
    }

    @Override
    public void bind(final Component component) {
        component.add(AttributeModifier.prepend("onclick", "$('#" + component.getMarkupId() + "').tooltip('hide');"));
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        renderJavaScriptReference(component, response);
        // wicket takes care of rendering the script only once if multiple components with this behavior are added.
        response.renderOnDomReadyJavaScript(TOOLTIP_JS);
    }

    /**
     * Subclass can override to provide necessary javascript libs.
     * @param component
     * @param response
     */
    protected void renderJavaScriptReference(final Component component, final IHeaderResponse response) {
    }

    @Override
    public void onComponentTag(final Component component, final ComponentTag tag) {
        // must be an A-tag
        Check.isEqual(tag.getName().toLowerCase(), "a", "Behavior can only be added to links");
        tag.put("rel", "tooltip");
        tag.put("data-original-title", model.getObject());
    }
}
