package de.flower.rmt.ui.common.form;

import de.flower.common.model.IEntity;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

/**
 * Form with cancel button. By default cancel will trigger history.back client side.
 * In case server side action is required, pass a reference to a link to the form.
 *
 * @author flowerrrr
 */
public abstract class CancelableEntityForm<T extends IEntity> extends EntityForm<T> {

    public CancelableEntityForm(String id, IModel<T> model) {
        this(id, model, null);
    }

    public CancelableEntityForm(String id, IModel<T> model, Link<?> cancelLink) {
        super(id, model);
        if (cancelLink != null) {
            add(cancelLink);
        } else {
            ExternalLink link = new ExternalLink("cancelButton", "#");
            link.add(AttributeModifier.replace("onclick", "window.history.back();return false;"));
            add(link);
        }
    }
}
