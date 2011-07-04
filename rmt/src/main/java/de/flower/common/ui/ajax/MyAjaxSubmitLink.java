package de.flower.common.ui.ajax;

import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;

/**
 * @author oblume
 */
public abstract class MyAjaxSubmitLink extends AjaxSubmitLink {

    public MyAjaxSubmitLink(String id) {
        super(id);
    }

    public MyAjaxSubmitLink(String id, Form<?> form) {
        super(id, form);
    }

    @Override
    protected IAjaxCallDecorator getAjaxCallDecorator() {
        return new PreventDoubleClickAjaxCallDecorator();
    }

}
