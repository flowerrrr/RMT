package de.flower.rmt.ui.common.form.field;

import org.apache.wicket.markup.html.form.TextArea;

/**
 * @author flowerrrr
 */
public class TextAreaPanel extends AbstractFormFieldPanel {

    public TextAreaPanel(String id) {
        super(id, new TextArea(ID));
    }

}
