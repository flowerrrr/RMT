package de.flower.common.ui.form;

import org.apache.wicket.markup.html.form.TextField;


/**
 * @author flowerrrr
 */
public class ValidatedTextField<T> extends ValidatedFormComponent {

    public ValidatedTextField(String id) {
        super(new TextField<T>(id));
    }
}
