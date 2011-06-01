package de.flower.rmt.ui.common.page.login;

import de.flower.rmt.ui.app.WebSession;
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

    private HomePageResolver homePageResolver = new HomePageResolver();

    public LoginForm(String id) {
        super(id);
        setModel(new CompoundPropertyModel(this));

        add(new RequiredTextField("username"));
        add(new PasswordTextField("password"));

    }

    @Override
    protected void onSubmit() {
        AuthenticatedWebSession session = WebSession.get();
        if(session.signIn(username, password)) {
            setResponsePage(homePageResolver.getHomePage(session));
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