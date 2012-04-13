package de.flower.common.ui.modal;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * This behavior allows the user to close a modal window with the ESC key.
 */
class CloseOnEscBehavior extends AbstractDefaultAjaxBehavior {

    private static final String JS_ID = "closeOnEscBehavior";

    private final ModalWindow modal;

    public CloseOnEscBehavior(final ModalWindow modal) {
        this.modal = modal;
    }

    @Override
    protected void respond(final AjaxRequestTarget target) {
        modal.close(target);
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        // TODO (flowerrrr - 13.04.12) use #renderOnDomReady to avoid RMT-573
        response.renderJavaScript("$(document).ready(function() {\n"
            + "  $(document).bind('keyup', function(evt) {\n"
            + "    if (evt.keyCode == 27) {\n"
            + getCallbackScript() + "\n"
            + "        evt.preventDefault();\n"
            + "    }\n"
            + "  });\n"
            + "});", JS_ID);
    }
}

