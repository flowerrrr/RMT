package de.flower.rmt.ui.page.event.manager.invitations;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.modal.ModalPanel;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.service.IInvitationManager;
import de.flower.rmt.ui.markup.html.form.field.TextAreaPanel;
import de.flower.rmt.ui.markup.html.panel.FormFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.BeanValidator;

/**
 * Panel for manager to edit players invitation status and give his own comments.
 *
 * @author flowerrrr
 */
public class InvitationEditPanel extends ModalPanel<Invitation> {

    @SpringBean
    private IInvitationManager invitationManager;

    public InvitationEditPanel(final IModel<Invitation> model) {
        super(model);

        setHeading("manager.invitations.editpanel.heading");

        Form<Invitation> form = new Form<Invitation>("form", new CompoundPropertyModel<Invitation>(model));
        addForm(form);
        form.add(new FormFeedbackPanel(form));

        final RadioGroup group = new RadioGroup("status");
        form.add(group);
        group.add(new Radio<RSVPStatus>("accepted", Model.of(RSVPStatus.ACCEPTED)));
        group.add(new Radio<RSVPStatus>("declined", Model.of(RSVPStatus.DECLINED)));
        group.add(new Radio<RSVPStatus>("unsure", Model.of(RSVPStatus.UNSURE)));
        group.add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(group)) {
            @Override
            public boolean isVisible() {
                return anyMessage();
            }
        });
        form.add(new TextAreaPanel("comment"));
        form.add(new TextAreaPanel("managerComment"));
    }

    @Override
    protected boolean onSubmit(final AjaxRequestTarget target, final Form<Invitation> form) {
        if (!new BeanValidator(form).isValid(form.getModelObject())) {
            target.add(form);
            return false;
        } else {
            // save invitation and update invitationlistpanel
            Invitation invitation = form.getModelObject();
            invitationManager.save(invitation);
            AjaxEventSender.entityEvent(this, Invitation.class);
            return true;
        }
    }
}
