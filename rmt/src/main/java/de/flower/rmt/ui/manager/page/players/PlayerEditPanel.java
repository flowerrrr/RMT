package de.flower.rmt.ui.manager.page.players;

import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.form.CancelableEntityForm;
import de.flower.common.ui.form.EntityForm;
import de.flower.common.validation.groups.IEmailUnique;
import de.flower.common.validation.groups.INameUnique;
import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.common.form.TextFieldPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.FormComponentBeanValidator;

/**
 * @author flowerrrr
 */
public class PlayerEditPanel extends BasePanel {

    @SpringBean
    private IUserManager playerManager;

    public PlayerEditPanel(IModel<User> model) {
        super();

        EntityForm<User> form = new CancelableEntityForm<User>("form", model) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<User> form) {
                playerManager.save(form.getModelObject());
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(User.class), AjaxEvent.EntityUpdated(User.class)));
                onClose(target);
            }

            @Override
            protected void onCancel(AjaxRequestTarget target) {
                onClose(null);
            }
        };
        add(form);

        TextFieldPanel fullname = new TextFieldPanel("fullname");
        form.add(fullname);
        fullname.addValidator(new FormComponentBeanValidator(INameUnique.class));
        TextFieldPanel email = new TextFieldPanel("email");
        form.add(email);
        fullname.addValidator(new FormComponentBeanValidator(IEmailUnique.class));
    }
}
