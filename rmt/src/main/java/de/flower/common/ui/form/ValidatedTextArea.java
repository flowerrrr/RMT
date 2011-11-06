package de.flower.common.ui.form;

import org.apache.wicket.markup.html.form.TextArea;

/**
 * @author flowerrrr
 */
public class ValidatedTextArea<T> extends ValidatedFormComponent {

    public ValidatedTextArea(String id) {
        super(new TextArea<T>(id));
    }
}
