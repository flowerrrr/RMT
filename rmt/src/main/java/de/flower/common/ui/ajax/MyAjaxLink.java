package de.flower.common.ui.ajax;

import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;

/**
 * @author oblume
 */
public abstract class MyAjaxLink<T> extends AjaxLink<T> {

    public MyAjaxLink(String id) {
        super(id);
    }

    protected MyAjaxLink(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    protected IAjaxCallDecorator getAjaxCallDecorator() {
        return new PreventDoubleClickAjaxCallDecorator();
    }
}
