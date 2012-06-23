package de.flower.common.ui.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxCallDecorator;

/**
 * Prevents double clicking of ajax links.
 *
 * Taken from Apache Wicket Cookbook, page 165.
 *
 */
public class PreventDoubleClickAjaxCallDecorator implements IAjaxCallDecorator {

    private static final String DATA_WICKET_BLOCKED = "data-wicket-blocked";

    private static final String LATCH = "var locked=this.hasAttribute('" + DATA_WICKET_BLOCKED + "');"
            + "if (locked) { return false; } "
            + "this.setAttribute('" + DATA_WICKET_BLOCKED + "','locked');";

    private static final String RESET = "this.removeAttribute('" + DATA_WICKET_BLOCKED + "');";

    @Override
    public CharSequence decorateScript(final Component component, final CharSequence script) {
        return LATCH + script;
    }

    @Override
    public CharSequence decorateOnSuccessScript(final Component component, final CharSequence script) {
        return RESET + script;
    }

    @Override
    public CharSequence decorateOnFailureScript(final Component component, final CharSequence script) {
        return RESET + script;
    }
}
