package de.flower.rmt.ui.player.pages.login;

import org.apache.wicket.authentication.AuthenticatedWebSession;
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

    @Override
    protected void onSubmit() {
        AuthenticatedWebSession session = AuthenticatedWebSession.get();
        if(session.signIn(username, password)) {
            setDefaultResponsePageIfNecessary();
        } else {
            error(getString("login.failed"));
        }
    }

    private void setDefaultResponsePageIfNecessary() {
        if(!continueToOriginalDestination()) {
            setResponsePage(getApplication().getHomePage());
        }
    }

}