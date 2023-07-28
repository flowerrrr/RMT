package de.flower.rmt.ui.page.account;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.dto.Password;
import de.flower.rmt.service.UserManager;
import de.flower.rmt.ui.markup.html.form.EntityForm;
import de.flower.rmt.ui.markup.html.form.field.PasswordTextFieldPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.validator.FormComponentBeanValidator;

/**
 * @author flowerrrr
 */
public class AccountPasswordPanel extends BasePanel<User> {

    @SpringBean
    private UserManager userManager;

    public AccountPasswordPanel(String id, final IModel<User> model) {
        super(id, model);

        EntityForm<Password> form = new EntityForm<Password>("form", new Password(model.getObject().getId())) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Password> form) {
                userManager.updatePassword(model.getObject().getId(), form.getModelObject());
            }
        };
        add(form);

        PasswordTextFieldPanel oldPassword = new PasswordTextFieldPanel("oldPassword");
        form.add(oldPassword);
        oldPassword.addValidator(new FormComponentBeanValidator(Password.Validation.IPasswordMatches.class));
        form.add(new PasswordTextFieldPanel("newPassword"));
        PasswordTextFieldPanel newPasswordRepeat = new PasswordTextFieldPanel("newPasswordRepeat");
        form.add(newPasswordRepeat);
        newPasswordRepeat.addValidator(new FormComponentBeanValidator(Password.Validation.IPasswordEquals.class));
    }

}
