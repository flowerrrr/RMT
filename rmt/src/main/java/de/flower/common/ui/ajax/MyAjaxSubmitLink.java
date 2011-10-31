package de.flower.common.ui.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;

/**
 * @author flowerrrr
 */
public abstract class MyAjaxSubmitLink extends AjaxSubmitLink {

    public MyAjaxSubmitLink(String id) {
        super(id);
    }

    public MyAjaxSubmitLink(String id, Form<?> form) {
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
