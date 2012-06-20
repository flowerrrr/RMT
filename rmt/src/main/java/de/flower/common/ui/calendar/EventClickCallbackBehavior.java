package de.flower.common.ui.calendar;

import de.flower.common.ui.ajax.AbstractParameterizedDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author flowerrrr
 */
public abstract class EventClickCallbackBehavior extends AbstractParameterizedDefaultAjaxBehavior {

    private Parameter<Long> paramId = Parameter.of("entityId", Long.class, "event.entityId");

    private Parameter<String> paramClazzName = Parameter.of("clazzName", String.class, "event.clazzName");

    @Override
    protected void respond(final AjaxRequestTarget target, final ParameterMap parameterMap) {
        CalEvent calEvent = new CalEvent();
        calEvent.entityId = parameterMap.getValue(paramId);
        calEvent.clazzName = parameterMap.getValue(paramClazzName);
        onEdit(target, calEvent);
    }

    @Override
    protected Parameter<?>[] getParameter() {
        return new Parameter[]{paramId, paramClazzName};
    }

    protected abstract void onEdit(AjaxRequestTarget target, CalEvent calEvent);

    public String getCallbackFunction() {
        String function = String.format("function(event) { %s; return false; }", getCallbackScript());
        return function;
    }
}
