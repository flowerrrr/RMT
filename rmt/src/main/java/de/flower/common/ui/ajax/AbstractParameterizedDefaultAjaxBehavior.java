package de.flower.common.ui.ajax;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * See http://www.wicket-praxis.de/blog/2010/03/07/wicket-heatmap-ajax-mit-parametern/.
 */
public abstract class AbstractParameterizedDefaultAjaxBehavior extends AbstractDefaultAjaxBehavior {

    @Override
    protected void respond(AjaxRequestTarget target) {
        Request request = RequestCycle.get().getRequest();

        Map<String, Object> map = new HashMap<String, Object>();
        Parameter<?>[] parameter = getParameter();
        for (Parameter<?> p : parameter) {
            String svalue = request.getRequestParameters().getParameterValue(p.getName()).toOptionalString();
            if (svalue != null) {
                Object value = getComponent().getConverter(p.getType()).convertToObject(svalue, getComponent().getLocale());
                map.put(p.getName(), value);
            }
        }

        respond(target, new ParameterMap(map));
    }

    @Override
    public CharSequence getCallbackUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getCallbackUrl());

        Parameter<?>[] parameter = getParameter();
        for (Parameter<?> p : parameter) {
            sb.append("&").append(p.getName()).append("='+").append(p.getJavascript()).append("+'");
        }

        return sb.toString();
    }

    public static class Parameter<T> implements Serializable{

        String _name;

        Class<T> _type;

        String _javascript;

        protected Parameter(String name, Class<T> type, String javascript) {
            _name = name;
            _type = type;
            _javascript = javascript;
        }

        protected String getName() {
            return _name;
        }

        protected Class<T> getType() {
            return _type;
        }

        protected String getJavascript() {
            return _javascript;
        }

        public static <T> Parameter<T> of(String name, Class<T> type, String javascript) {
            return new Parameter<T>(name, type, javascript);
        }

    }

    protected static class ParameterMap {

        Map<String, Object> _map;

        protected ParameterMap(Map<String, Object> map) {
            _map = map;
        }

        public <T> T getValue(Parameter<T> parameter) {
            return (T) _map.get(parameter.getName());
        }
    }

    protected abstract void respond(AjaxRequestTarget target, ParameterMap parameterMap);

    protected abstract Parameter<?>[] getParameter();
}

