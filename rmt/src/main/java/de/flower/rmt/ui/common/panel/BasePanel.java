package de.flower.rmt.ui.common.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class BasePanel<T> extends GenericPanel<T> {

    public BasePanel(String id) {
        this(id, null);
    }

    public BasePanel(String id, IModel<T> model) {
        super(id, model);
        add(new AttributeAppender("class", "panel"));
    }

    /**
     * Should be called by a panel that can be dismissed/closed, like in modal windows or panels that should be hidden
     * when editing is finished.
     * @param target
     */
    protected void onClose(final AjaxRequestTarget target) {

    }
}
