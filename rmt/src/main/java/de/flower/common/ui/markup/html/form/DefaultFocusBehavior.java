package de.flower.common.ui.markup.html.form;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * @see http://www.nabble.com/Default-Focus-Behavior--td15934889.html
 */
public class DefaultFocusBehavior extends Behavior {

    @Override
    public void bind(Component component) {
        component.setOutputMarkupId(true);
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        super.renderHead(component, response);
        response.renderOnDomReadyJavaScript("document.getElementById('" + component.getMarkupId() + "').focus();");
    }

}