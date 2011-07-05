package de.flower.rmt.ui.manager.page.players;

import de.flower.common.ui.FormMode;
import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.Event;
import de.flower.common.ui.form.MyForm;
import de.flower.common.ui.form.ValidatedTextField;
import de.flower.rmt.model.Users;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.app.RMTSession;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.BeanValidator;

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

        ValidatedTextField name;
        form.add(name = new ValidatedTextField("firstname"));
        form.add(new ValidatedTextField("lastname"));
        form.add(new ValidatedTextField("email"));

        form.add(new MyAjaxSubmitLink("saveButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (!new BeanValidator(form).isValid(form.getModelObject())) {
                    onError(target, form);
                } else {
                    playerManager.save((Users) form.getModelObject());
                    target.registerRespondListener(new AjaxRespondListener(Event.EntityCreated(Users.class), Event.EntityUpdated(Users.class)));
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
            Users user = RMTSession.get().getUser();
            model = Model.of(new Users(user.getClub()));
        }
        form.setModel(new CompoundPropertyModel<Users>(model));
        // clear css marker of previous validations

    }
}
