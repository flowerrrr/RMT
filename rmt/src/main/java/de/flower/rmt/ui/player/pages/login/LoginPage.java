package de.flower.rmt.ui.player.pages.login;

import de.flower.rmt.ui.BasePage;

/**
 * @author oblume
 */
public class LoginPage extends BasePage {

    public LoginPage() {
        add(new LoginForm("loginForm"));
    }
}
