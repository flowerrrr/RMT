package de.flower.rmt.ui.player.page.account;

import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.service.type.Password;
import de.flower.rmt.ui.common.form.EntityForm;
import de.flower.rmt.ui.common.form.field.TextFieldPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class AccountPasswordPanel extends BasePanel {

    @SpringBean
    private IUserManager userManager;

    public AccountPasswordPanel(final IModel<User> model) {
        super(model);

        EntityForm<Password> form = new EntityForm<Password>("form", Model.of(new Password(model.getObject().getId()))) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Password> form) {
                userManager.updatePassword(model.getObject().getId(), form.getModelObject());
            }
        };
        add(form);

        form.add(new TextFieldPanel("oldPassword"));
        form.add(new TextFieldPanel("newPassword"));
        form.add(new TextFieldPanel("newPasswordRepeat"));
    }

}
