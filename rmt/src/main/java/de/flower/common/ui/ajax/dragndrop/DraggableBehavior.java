package de.flower.common.ui.ajax.dragndrop;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * @author flowerrrr
 */
public class DraggableBehavior extends Behavior {

    private Component c;

    @Override
    public void bind(final Component c) {
        this.c = c;
        c.add(AttributeModifier.append("class", "draggable"));
        c.add(AttributeModifier.append("style", "position: absolute;"));
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        String javascript = String.format("$('#%s').draggable({ revert: 'invalid', stack: '#%s' });", c.getMarkupId(), c.getMarkupId());
        response.renderOnDomReadyJavaScript(javascript);
    }
}
