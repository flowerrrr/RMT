package de.flower.common.ui.ajax.markup.html;

import de.flower.common.ui.ajax.PreventDoubleClickAjaxCallDecorator;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public abstract class AjaxLink<T> extends org.apache.wicket.ajax.markup.html.AjaxLink<T>  {

    public AjaxLink(String id) {
        super(id);
    }

    public AjaxLink(String id, IModel<T> model) {
        super(id, model);
    }

    @Override
    protected IAjaxCallDecorator getAjaxCallDecorator() {
        return new PreventDoubleClickAjaxCallDecorator();
    }

    /**
     * Version of ajax link that prevents ajax-indicator if any is globally defined.
     * @param <T>
     */
    public static abstract class NoIndicatingAjaxLink<T> extends AjaxLink<T> implements IAjaxIndicatorAware {

        protected NoIndicatingAjaxLink(final String id) {
            super(id);
        }

        public NoIndicatingAjaxLink(final String id, final IModel<T> model) {
            super(id, model);
        }

        @Override
        public String getAjaxIndicatorMarkupId() {
            return null;
        }
    }

}
