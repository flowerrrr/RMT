package de.flower.rmt.ui.manager.page.players;

import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.form.EntityForm;
import de.flower.common.ui.form.ValidatedTextField;
import de.flower.common.validation.unique.Unique;
import de.flower.rmt.model.User;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.common.IEntityEditPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.model.UserModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.BeanValidator;
import org.wicketstuff.jsr303.ComponentBeanValidator;
import org.wicketstuff.jsr303.ConstraintFilter;

/**
 * @author flowerrrr
 */
public class PlayerEditPanel extends BasePanel implements IEntityEditPanel<User> {

    private EntityForm<User> form;

    @SpringBean
    private IUserManager playerManager;

    public PlayerEditPanel(String id) {
        super(id);

        form = new EntityForm<User>("form", new UserModel());
        add(form);

        ValidatedTextField fullname = new ValidatedTextField("fullname");
        form.add(fullname);
        fullname.add(new ComponentBeanValidator(Unique.class, new ConstraintFilter("{de.flower.validation.constraints.unique.message.fullname}")));

        ValidatedTextField email = new ValidatedTextField("email");
        form.add(email);
        // add a class level validator to this property
        email.add(new ComponentBeanValidator(Unique.class, new ConstraintFilter("{de.flower.validation.constraints.unique.message.email}")));

        form.add(new MyAjaxSubmitLink("saveButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (!new BeanValidator(form).isValid(form.getModelObject())) {
                    onError(target, form);
                } else {
                    playerManager.save((User) form.getModelObject());
                    target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(User.class), AjaxEvent.EntityUpdated(User.class)));
                    ModalWindow.closeCurrent(target);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
            }
        });
    }

    @Override
	public void init(IModel<User> model) {
        if (model == null) {
            model = new UserModel();
        }
        form.replaceModel(model);
    }
}
