package de.flower.rmt.ui.common.page.login;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * @author oblume
 */
class LoginForm extends Form {

    private String username;

    private String password;

    public LoginForm(String id) {
        super(id);
        setModel(new CompoundPropertyModel(this));

        add(new RequiredTextField("username"));
        add(new PasswordTextField("password"));

    }

}