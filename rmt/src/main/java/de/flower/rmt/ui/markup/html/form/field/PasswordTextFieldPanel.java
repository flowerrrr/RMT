package de.flower.rmt.ui.markup.html.form.field;

import org.apache.wicket.markup.html.form.PasswordTextField;

/**
 * @author flowerrrr
 */
public class PasswordTextFieldPanel extends AbstractFormFieldPanel {

    public PasswordTextFieldPanel(String id) {
        super(id, new PasswordTextField(ID) {
            {
                setRequired(false);
                setResetPassword(false);
            }
        });
    }
}

