package de.flower.common.ui.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxCallDecorator;

/**
 * @author oblume
 */
public class PreventDoubleClickAjaxCallDecorator implements IAjaxCallDecorator {

    @Override
    public CharSequence decorateScript(Component component, CharSequence script) {
        return "this.disabled=true;" + script;
    }

    @Override
    public CharSequence decorateOnSuccessScript(Component component, CharSequence script) {
        return "this.disabled=false;";
    }

    @Override
    public CharSequence decorateOnFailureScript(Component component, CharSequence script) {
        return "this.disabled=false;";
    }
}
