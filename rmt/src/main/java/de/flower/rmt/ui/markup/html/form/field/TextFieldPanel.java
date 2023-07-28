package de.flower.rmt.ui.markup.html.form.field;

import org.apache.wicket.markup.html.form.TextField;


public class TextFieldPanel extends AbstractFormFieldPanel {

    public TextFieldPanel(String id) {
        super(id, new TextField(ID));
    }

    public TextFieldPanel(String id, TextField c) {
        super(id, c);
    }

}
