package de.flower.common.ui.form;

import de.flower.common.model.IEntity;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

/**
 * Form with cancel button
 *
 * @author flowerrrr
 */
public abstract class CancelableEntityForm<T extends IEntity> extends EntityForm<T> {

    public CancelableEntityForm(String id, IModel<T> model) {
        super(id, model);
        add(new Link("cancelButton") {
            @Override
            public void onClick() {
                onCancel(null);
            }
        });
    }

    protected abstract void onCancel(AjaxRequestTarget target);
}
