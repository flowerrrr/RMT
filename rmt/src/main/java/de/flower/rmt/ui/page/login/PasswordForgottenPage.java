package de.flower.rmt.ui.page.login;

import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.service.UserManager;
import de.flower.rmt.ui.markup.html.panel.SimpleFeedbackPanel;
import de.flower.rmt.ui.page.base.AbstractBasePage;
import de.flower.rmt.ui.page.base.AnonymousNavigationPanel;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class PasswordForgottenPage extends AbstractBasePage {

    @SpringBean
    private UserManager userManager;

    private static String[] links = new String[]{
            "http://www.buerlecithin.de/",
            "http://www.dr-poehlmann.de/poehlmann/taiginseng/",
            // "http://www.gedaechtnistraining.net/",
            // "http://www.oekotest.de/cgi/index.cgi?artnr=38460;bernr=06;co=",
            // "http://www.lizzynet.de/wws/32371748.php",
            "http://keepass.info/",
    };

    private String email;

    public PasswordForgottenPage() {

        add(new AnonymousNavigationPanel());

        // a bit of a hack, this form.
        Form form = new Form("form", new CompoundPropertyModel(this));
        form.setOutputMarkupId(true);
        add(form);
        form.add(new SimpleFeedbackPanel(null));

        form.add(new TextField("email").setRequired(true));
        form.add(new ExternalLink("emailForgottenLink", getLinkModel()));

        AjaxSubmitLink submitButton;
        form.add(submitButton = new AjaxSubmitLink("submitButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                User user = userManager.findByUsername(email);
                if (user == null) {
                    // unknown email address
                    warn("Unbekannte E-Mail-Adresse.");
                } else {
                    userManager.resetPassword(user, true);
                    // generate feedback message
                    info("E-Mail mit neuem Passwort wurde versendet.");
                }
                target.add(form);
            }
        });

        // enable form submit by hitting RETURN
        form.setDefaultButton(submitButton);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    private IModel<String> getLinkModel() {
        return new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return links[RandomUtils.nextInt(links.length)];
            }
        };
    }
}
