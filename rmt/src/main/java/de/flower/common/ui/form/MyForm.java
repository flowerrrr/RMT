package de.flower.common.ui.form;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * @author oblume
 */
public class MyForm<T> extends Form<T> {

    public MyForm(String id) {
        super(id);
    }

    public MyForm(String id, IModel<T> iModel) {
        super(id, iModel);
    }
}
