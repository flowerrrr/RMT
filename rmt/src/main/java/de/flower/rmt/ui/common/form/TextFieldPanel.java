package de.flower.rmt.ui.common.form;

import org.apache.wicket.markup.html.form.TextField;
import org.wicketstuff.jsr303.FormComponentBeanValidator;

/**
 * @author flowerrrr
 */
public class TextFieldPanel extends FormFieldPanel {

    public TextFieldPanel(String id) {
        super(id, new TextField("input"));
    }

}
