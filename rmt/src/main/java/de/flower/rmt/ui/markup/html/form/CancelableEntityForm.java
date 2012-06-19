package de.flower.rmt.ui.markup.html.form;

import de.flower.common.ui.markup.html.link.HistoryBackLink;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;

/**
 * Form with cancel button. By default cancel will trigger history.back client side.
 * In case server side action is required, pass a reference to a link to the form.
 *
 * @author flowerrrr
 */
// TODO (flowerrrr - 09.03.12) move code into entityForm and let client disable cancel link per parameter.
public abstract class CancelableEntityForm<T> extends EntityForm<T> {

    public CancelableEntityForm(String id, IModel<T> model) {
        this(id, model, null);
    }

    public CancelableEntityForm(String id, IModel<T> model, AbstractLink cancelLink) {
        super(id, model);
        if (cancelLink != null) {
            add(cancelLink);
        } else {
            add(new HistoryBackLink("cancelButton"));
        }
    }
}
