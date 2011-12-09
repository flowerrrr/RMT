package de.flower.rmt.ui.player.page.account;

import de.flower.rmt.model.User;
import de.flower.rmt.model.type.Password;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.common.form.EntityForm;
import de.flower.rmt.ui.common.form.field.TextFieldPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.FormComponentBeanValidator;

/**
 * @author flowerrrr
 */
public class AccountPasswordPanel extends BasePanel {

    @SpringBean
    private IUserManager userManager;

    public AccountPasswordPanel(String id, final IModel<User> model) {
        super(id, model);

        EntityForm<Password> form = new EntityForm<Password>("form", Model.of(new Password(model.getObject().getId()))) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Password> form) {
                userManager.updatePassword(model.getObject().getId(), form.getModelObject());
                target.add(form);
            }
        };
        add(form);

        TextFieldPanel oldPassword = new TextFieldPanel("oldPassword");
        form.add(oldPassword);
        oldPassword.addValidator(new FormComponentBeanValidator(Password.Validation.IPasswordMatches.class));
        form.add(new TextFieldPanel("newPassword"));
        TextFieldPanel newPasswordRepeat = new TextFieldPanel("newPasswordRepeat");
        form.add(newPasswordRepeat);
        newPasswordRepeat.addValidator(new FormComponentBeanValidator(Password.Validation.IPasswordEquals.class));
    }

}
