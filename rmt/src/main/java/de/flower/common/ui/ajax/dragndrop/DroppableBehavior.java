package de.flower.common.ui.ajax.dragndrop;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * @author flowerrrr
 */
public abstract class DroppableBehavior extends Behavior {

    private final static String SCRIPT = "$(function () {\n" +
            "            var id = '%s';\n" +
            "            var rerenderAfterDrop = %s;\n" +
            "            $('#' + id).droppable({\n" +
            "                hoverClass: 'droppable-hover',\n" +
            "                greedy: true,\n" +
            "                drop: function (event, ui) {\n" +
            // values can be decimals. better round it to avoid parsing exception in wicket long converter.
            "                    var top = Math.round(ui.offset.top - $(this).position().top);\n" +
            "                    var left = Math.round(ui.offset.left - $(this).position().left);\n" +
            "                    var width = $(this).width();\n" +
            "                    var height = $(this).height();\n" +
            "                    var url = $('#' + id).attr('url');\n" +
            "                    var callback = eval(url);\n" +
            "                    wicketAjaxGet(callback);\n" +
            "                    if (rerenderAfterDrop) ui.draggable.remove();\n" + // remove dragged object
            "                }\n" +
            "            });\n" +
            "        });\n";

    private boolean rerenderAfterDrop;

    protected DroppableBehavior(final boolean rerenderAfterDrop) {
        this.rerenderAfterDrop = rerenderAfterDrop;
    }

    @Override
    public void bind(final Component c) {
        final AbstractDefaultAjaxBehavior behavior = new DropCallbackBehavior() {
            @Override
            protected void onDrop(final AjaxRequestTarget target, final DraggableDto dto) {
                DroppableBehavior.this.onDrop(target, dto);
            }
        };
        c.add(behavior);
        c.add(AttributeModifier.replace("url", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return "'" + behavior.getCallbackUrl().toString() + "'";
            }
        }));
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        response.renderOnDomReadyJavaScript(String.format(SCRIPT, component.getMarkupId(), rerenderAfterDrop));
    }

    protected abstract void onDrop(final AjaxRequestTarget target, final DraggableDto dto);
}
