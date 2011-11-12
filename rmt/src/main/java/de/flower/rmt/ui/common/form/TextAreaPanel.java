package de.flower.rmt.ui.common.form;

import org.apache.wicket.markup.html.form.TextArea;

/**
 * @author flowerrrr
 */
public class TextAreaPanel extends FormFieldPanel {

    public TextAreaPanel(String id) {
        super(id, new TextArea(ID));
    }

}
