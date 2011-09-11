package de.flower.rmt.ui.manager.page.players;

import de.flower.common.ui.FormMode;
import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.form.MyForm;
import de.flower.common.ui.form.ValidatedTextField;
import de.flower.common.validation.unique.Unique;
import de.flower.rmt.model.Users;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.BeanValidator;
import org.wicketstuff.jsr303.ConstraintFilter;
import org.wicketstuff.jsr303.WicketBeanValidator;

/**
 * @author oblume
 */
public class PlayerEditPanel extends BasePanel {

    private FormMode mode;

    private Form<Users> form;

    @SpringBean
    private IUserManager playerManager;

    public PlayerEditPanel(String id) {
        super(id);

        form = new MyForm<Users>("form", new Users());
        add(form);

        ValidatedTextField fullname = new ValidatedTextField("fullname");
        form.add(fullname);
        fullname.add(new WicketBeanValidator(Unique.class, new ConstraintFilter("{de.flower.validation.constraints.unique.message.fullname}")));

        ValidatedTextField email = new ValidatedTextField("email");
        form.add(email);
        // add a class level validator to this property
        email.add(new WicketBeanValidator(Unique.class, new ConstraintFilter("{de.flower.validation.constraints.unique.message.email}")));

        form.add(new MyAjaxSubmitLink("saveButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (!new BeanValidator(form).isValid(form.getModelObject())) {
                    onError(target, form);
                } else {
                    playerManager.save((Users) form.getModelObject());
                    target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(Users.class), AjaxEvent.EntityUpdated(Users.class)));
                    ModalWindow.closeCurrent(target);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
            }
        });
    }

    public void init(IModel<Users> model) {
        if (model == null) {
            model = Model.of(playerManager.newPlayerInstance());
        }
        form.setModel(new CompoundPropertyModel<Users>(model));
        // clear css marker of previous validations

    }
}
