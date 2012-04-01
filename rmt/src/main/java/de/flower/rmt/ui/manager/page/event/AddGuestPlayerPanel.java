package de.flower.rmt.ui.manager.page.event;

import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.validator.constraints.NotBlank;
import org.wicketstuff.jsr303.BeanValidator;

/**
 * @author flowerrrr
 */
public class AddGuestPlayerPanel extends BasePanel<Event> {

    @SpringBean
    private IInvitationManager invitationManager;

    private FEntity fEntity;

    public AddGuestPlayerPanel(String id, final IModel<Event> model) {
        super(id);

        fEntity = new FEntity();
        final Form<FEntity> form = new Form<FEntity>("form", new CompoundPropertyModel(fEntity));
        add(form);

        form.add(new TextField("guestName"));

        // add and cancel buttons

        form.add(new AjaxSubmitLink("addButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (!new BeanValidator(form).isValid(form.getModelObject())) {
                    onError(target, form);
                } else {
                    invitationManager.addGuestPlayer(model.getObject(), ((FEntity) form.getModelObject()).guestName);
                    target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(Invitation.class)));
                    onClose(target);
                }
            }
        });
    }

    @Override
    protected void onDetach() {
        fEntity.guestName = "";
    }

    protected static final class FEntity {

        @NotBlank
        public String guestName;
    }
}
