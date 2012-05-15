package de.flower.common.ui.ajax.behavior.test;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * Register pre and post call handler with wicket ajax to be able to track ajax call status from selenium tests.
 * Selenium test can then use the 'implicit wait' plugin to wait for ajax calls to finish before proceeding to
 * the next selenium command.
 */
public class SeleniumWaitForAjaxSupportBehavior extends Behavior {

    @Override
    public final void renderHead(final Component component, final IHeaderResponse response) {
        super.renderHead(component, response);
        String javascript = "Wicket.Ajax.registerPreCallHandler(function() {\n"
                + "     // console.log('entering ajax call')\n;"
                + "     Wicket.Ajax.insideAjaxCall = true;\n"
                + "}); \n";
        javascript += "Wicket.Ajax.registerPostCallHandler(function() {\n"
                + "     // console.log('leaving ajax call');\n"
                + "     Wicket.Ajax.insideAjaxCall = false;\n"
                + "}); \n";
        javascript += "Wicket.Ajax.isAjaxBusy = function() { return Wicket.Ajax.insideAjaxCall == true; }\n";
        response.renderJavaScript(javascript, getClass().getName());
    }

}
