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
public class TwipsyBehavior extends Behavior {

    public static final String TWIPSY_JS_URL = "js/bootstrap/bootstrap-twipsy.js";

    public static final String TWIPSY_JS = "$(function () { $(\"a[rel=twipsy]\").twipsy({ live: false }) })";

    private IModel<String> model;

    public TwipsyBehavior(final IModel<String> model) {
        this.model = model;
    }

    @Override
    public void bind(final Component component) {
        component.add(AttributeModifier.prepend("onclick", "$('#" + component.getMarkupId() + "').twipsy('hide');"));
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        response.renderJavaScriptReference(TWIPSY_JS_URL, "twipsy");
        // wicket takes care of rendering the script only once if multiple compontents with this behavior are added.
        response.renderOnDomReadyJavaScript(TWIPSY_JS);
    }

    @Override
    public void onComponentTag(final Component component, final ComponentTag tag) {
        // must be an A-tag
        Check.isEqual(tag.getName().toLowerCase(), "a", "Behavior can only be added to links");
        tag.put("rel", "twipsy");
        tag.put("data-original-title", model.getObject());
    }
}
