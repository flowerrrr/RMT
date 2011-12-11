package de.flower.common.ui.ajax;

import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public abstract class AjaxLink<T> extends org.apache.wicket.ajax.markup.html.AjaxLink<T> {

    public AjaxLink(String id) {
        super(id);
    }

    protected AjaxLink(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    protected IAjaxCallDecorator getAjaxCallDecorator() {
        return new PreventDoubleClickAjaxCallDecorator();
    }
}
