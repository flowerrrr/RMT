package de.flower.rmt.ui.page.event.manager.invitees;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.form.AjaxSubmitLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.InvitationManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.validator.constraints.NotBlank;
import org.wicketstuff.jsr303.validator.BeanValidator;

import java.io.Serializable;

/**
 * @author flowerrrr
 */
public class AddGuestPlayerPanel extends BasePanel<Event> {

    @SpringBean
    private InvitationManager invitationManager;

    private FEntity fEntity;

    public AddGuestPlayerPanel(String id, final IModel<Event> model) {
        super(id);

        fEntity = new FEntity();
        final Form<FEntity> form = new Form<FEntity>("form", new CompoundPropertyModel<FEntity>(fEntity));
        add(form);

        form.add(new TextField("guestName"));

        AjaxSubmitLink addButton = new AjaxSubmitLink("addButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (!new BeanValidator(form).isValid(form.getModelObject())) {
                    onError(target, form);
                } else {
                    invitationManager.addGuestPlayer(model.getObject(), ((FEntity) form.getModelObject()).guestName);
                    AjaxEventSender.entityEvent(this, Invitation.class);
                    onClose(target);
                }
            }
        };
        form.add(addButton);
        // make enter submit the form via ajax.
        form.setDefaultButton(addButton);
    }

    @Override
    protected void onDetach() {
        fEntity.guestName = "";
        super.onDetach();
    }

    protected static final class FEntity implements Serializable {

        @NotBlank
        public String guestName;
    }
}
