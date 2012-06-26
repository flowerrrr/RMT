package de.flower.rmt.ui.markup.html.calendar;

import de.flower.common.ui.ajax.AbstractParameterizedDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.util.Date;

/**
 * @author flowerrrr
 */
public abstract class SelectCallbackBehavior extends AbstractParameterizedDefaultAjaxBehavior {

    public Parameter<Long> paramStart = Parameter.of("start", Long.class, "start.getTime()");

    public Parameter<Long> paramEnd = Parameter.of("end", Long.class, "end.getTime()");

    public Parameter<Boolean> paramAllDay = Parameter.of("allDay", Boolean.class, "allDay");

    @Override
    protected void respond(final AjaxRequestTarget target, final ParameterMap parameterMap) {
        CalEvent calEvent = new CalEvent();
        calEvent.start = new Date(parameterMap.getValue(paramStart));
        calEvent.end = new Date(parameterMap.getValue(paramEnd));
        calEvent.allDay = parameterMap.getValue(paramAllDay);
        onEdit(target, calEvent);
    }

    @Override
    protected Parameter<?>[] getParameter() {
        return new Parameter[]{paramStart, paramEnd, paramAllDay};
    }

    protected abstract void onEdit(AjaxRequestTarget target, CalEvent calEvent);

    public String getCallbackFunction() {
        String function = String.format("function(start, end, allDay) { %s; return false; }", getCallbackScript());
        return function;
    }
}
