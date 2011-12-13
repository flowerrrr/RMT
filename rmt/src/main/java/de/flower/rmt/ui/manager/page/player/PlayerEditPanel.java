package de.flower.rmt.ui.manager.page.player;

import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.User;
import de.flower.rmt.model.type.UserDto;
import de.flower.rmt.service.IRoleManager;
import de.flower.rmt.service.IUserManager;
import de.flower.rmt.ui.common.form.EntityForm;
import de.flower.rmt.ui.common.form.field.CheckBoxPanel;
import de.flower.rmt.ui.common.form.field.TextFieldPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.model.AbstractEntityModel;
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
    private IUserManager userManager;

    @SpringBean
    private IRoleManager roleManager;

    public PlayerEditPanel(IModel<User> model) {

        UserDtoModel formModel = new UserDtoModel(new UserDto(model.getObject(), roleManager.isManager(model.getObject())));

        EntityForm<UserDto> form = new EntityForm<UserDto>("form", formModel) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<UserDto> form) {
                UserDto userDto = form.getModelObject();
                userManager.save(userDto.getUser(), userDto.getManager());
                target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(User.class), AjaxEvent.EntityUpdated(User.class)));
            }
        };
        add(form);

        TextFieldPanel fullname = new TextFieldPanel("user.fullname");
        form.add(fullname);
        fullname.addValidator(new FormComponentBeanValidator(User.Validation.INameUnique.class));
        TextFieldPanel email = new TextFieldPanel("user.email");
        form.add(email);
        email.addValidator(new FormComponentBeanValidator(User.Validation.IEmailUnique.class));
        CheckBoxPanel manager = new CheckBoxPanel("manager");
        form.add(manager);
    }

    private static class UserDtoModel extends AbstractEntityModel<UserDto> {

        @SpringBean
        private IUserManager userManager;

        @SpringBean
        private IRoleManager roleManager;

        public UserDtoModel(final UserDto userDto) {
            super(userDto);
        }

        @Override
        protected UserDto load(final Long id) {
            User user = userManager.loadById(id);
            boolean manager = roleManager.isManager(user);
            return new UserDto(user, manager);
        }

        @Override
        protected UserDto newInstance() {
            return new UserDto(userManager.newInstance(), false);
        }
    }

}
