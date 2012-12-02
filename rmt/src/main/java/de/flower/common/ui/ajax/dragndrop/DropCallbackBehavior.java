package de.flower.common.ui.ajax.dragndrop;

import de.flower.common.ui.ajax.AbstractParameterizedDefaultAjaxBehavior;
import de.flower.common.ui.util.MarkupIdVisitor;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @author flowerrrr
*/
abstract class DropCallbackBehavior extends AbstractParameterizedDefaultAjaxBehavior {

    private final static Logger log = LoggerFactory.getLogger(DropCallbackBehavior.class);

    public Parameter<String> id = Parameter.of("id", String.class, "ui.helper[0].id");

    public Parameter<Long> top = Parameter.of("top", Long.class, "top");

    public Parameter<Long> left = Parameter.of("left", Long.class, "left");

    public Parameter<Long> width = Parameter.of("width", Long.class, "width");

    public Parameter<Long> height = Parameter.of("height", Long.class, "height");

    @Override
    protected void respond(final AjaxRequestTarget target, final ParameterMap parameterMap) {
        MarkupIdVisitor visitor = new MarkupIdVisitor(parameterMap.getValue(id));
        this.getComponent().getPage().visitChildren(visitor);
        Component draggedComponent = visitor.getFoundComponent();
        if (draggedComponent == null) {
            // happens if element is dragged during ajax-refresh of panel
            log.warn("Dropped component not found.");
            return;
        }
        DraggableDto dto = new DraggableDto();
        dto.entityId = ((IDraggable) draggedComponent).getEntityId();
        dto.top = parameterMap.getValue(top);
        dto.left = parameterMap.getValue(left);

        onDrop(target, dto);
    }

    @Override
    protected Parameter<?>[] getParameter() {
        return new Parameter[]{id, top, left, width, height};
    }

    protected abstract void onDrop(AjaxRequestTarget target, DraggableDto dto);
}
