package de.flower.rmt.ui.common.form.field;

import org.apache.wicket.datetime.markup.html.form.DateTextField;

/**
 * @author flowerrrr
 */
public class DateFieldPanel extends FormFieldPanel {

    public DateFieldPanel(String id) {
        super(id, DateTextField.forDateStyle(ID, "S-"));
        // dateField.add(new DatePicker());
    }
}
