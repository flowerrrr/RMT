package de.flower.common.ui.modal;


import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * When a modal window is opened the focus is set on the first input element.
 */
public class FocusFirstInputBehavior extends Behavior {

    private static final String JS_ID = "focusFirstInputBehavior";


    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {

        /*
         * A JavaScript function is registered to be handled after each Ajax call.
         * This function checks if the focus is already inside the modal panel.
         * If not it sets the focus to the first input element.
         */
        response.renderJavaScript("Wicket.Ajax.registerPostCallHandler(function() {"
                + "if($('*:focus').parents('.container-modal').length == 0) {"
                + "$('.container-modal input[type=text]:first').focus();}});", JS_ID);
    }
}

