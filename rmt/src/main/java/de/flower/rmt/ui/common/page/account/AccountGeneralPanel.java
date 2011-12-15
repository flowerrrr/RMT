package de.flower.rmt.ui.common.page.account;

import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.common.form.EntityForm;
import de.flower.rmt.ui.common.form.field.TextFieldPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.FormComponentBeanValidator;

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
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityUpdated(User.class)));
            }
        };
        add(form);

        TextFieldPanel fullname = new TextFieldPanel("fullname");
        form.add(fullname);
        fullname.addValidator(new FormComponentBeanValidator(User.Validation.INameUnique.class));
        TextFieldPanel email = new TextFieldPanel("email");
        form.add(email);
        email.addValidator(new FormComponentBeanValidator(User.Validation.IEmailUnique.class));
    }
}
