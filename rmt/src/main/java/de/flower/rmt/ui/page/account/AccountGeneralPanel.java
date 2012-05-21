package de.flower.rmt.ui.page.account;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.markup.html.form.EntityForm;
import de.flower.rmt.ui.markup.html.form.field.TextFieldPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.validator.FormComponentBeanValidator;

/**
 * @author flowerrrr
 */
public class AccountGeneralPanel extends BasePanel {

    @SpringBean
    private IUserManager userManager;

    public AccountGeneralPanel(String id, final IModel<User> model) {
        super(id, model);

        EntityForm<User> form = new EntityForm<User>("form", model) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<User> form) {
                User user = form.getModelObject();
                userManager.save(user);
                AjaxEventSender.entityEvent(this, User.class);
            }
        };
        add(form);

        TextFieldPanel fullname = new TextFieldPanel("fullname");
        form.add(fullname);
        fullname.addValidator(new FormComponentBeanValidator(User.Validation.INameUnique.class));

        TextFieldPanel email = new TextFieldPanel("email");
        form.add(email);
        email.addValidator(new FormComponentBeanValidator(User.Validation.IEmailUnique.class));

        TextFieldPanel secondEmail = new TextFieldPanel("secondEmail");
        form.add(secondEmail);

        TextFieldPanel phoneNumber = new TextFieldPanel("phoneNumber");
        form.add(phoneNumber);
    }
}
