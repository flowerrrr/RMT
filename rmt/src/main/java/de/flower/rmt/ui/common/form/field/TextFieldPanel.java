package de.flower.rmt.ui.common.form.field;

import org.apache.wicket.markup.html.form.TextField;

/**
 * @author flowerrrr
 */
public class TextFieldPanel extends AbstractFormFieldPanel {

    public TextFieldPanel(String id) {
        super(id, new TextField(ID));
    }

    public TextFieldPanel(String id, TextField c) {
        super(id, c);
    }

}
