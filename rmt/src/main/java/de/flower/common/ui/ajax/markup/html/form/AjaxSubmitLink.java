package de.flower.common.ui.ajax.markup.html.form;

import de.flower.common.ui.ajax.PreventDoubleClickAjaxCallDecorator;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.markup.html.form.Form;

/**
 * @author flowerrrr
 */
public abstract class AjaxSubmitLink extends org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink {

    public AjaxSubmitLink(String id) {
        super(id);
    }

    public AjaxSubmitLink(String id, Form<?> form) {
        super(id, form);
    }

    @Override
    protected final IAjaxCallDecorator getAjaxCallDecorator() {
        return new PreventDoubleClickAjaxCallDecorator();
    }

    @Override
    protected final void onError(AjaxRequestTarget target, Form<?> form) {
        target.add(form);
    }

}
