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
            "            var repositionAfterDrop = %s;\n" +
            "            $('#' + id).droppable({\n" +
            "                drop: function (event, ui) {\n" +
            "                    var top = ui.offset.top - $(this).position().top;\n" +
            "                    var left = ui.offset.left - $(this).position().left;\n" +
            "                    var width = $(this).width();\n" +
            "                    var height = $(this).height();\n" +
            "                    var url = $('#' + id).attr('url');\n" +
            "                    var callback = eval(url);\n" +
            "                    wicketAjaxGet(callback);\n" +
            "                    if (repositionAfterDrop) ui.draggable.remove();\n" + // remove dragged object
            "                }\n" +
            "            });\n" +
            "        });\n";

    private boolean repositionAfterDrop;

    protected DroppableBehavior(final boolean repositionAfterDrop) {
        this.repositionAfterDrop = repositionAfterDrop;
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
        response.renderOnDomReadyJavaScript(String.format(SCRIPT, component.getMarkupId(), repositionAfterDrop));
    }

    protected abstract void onDrop(final AjaxRequestTarget target, final DraggableDto dto);
}
