package de.flower.rmt.ui.markup.html.form.field;

import org.apache.wicket.markup.html.form.TextArea;


public class TextAreaPanel extends AbstractFormFieldPanel {

    public TextAreaPanel(String id) {
        super(id, new TextArea(ID));
    }

}
