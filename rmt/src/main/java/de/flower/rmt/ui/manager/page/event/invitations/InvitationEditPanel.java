package de.flower.rmt.ui.manager.page.event.invitations;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.modal.ModalPanel;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.service.IInvitationManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

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
        form.add(new TextArea("comment"));
    }

    @Override
    protected boolean onSubmit(final AjaxRequestTarget target, final Form<Invitation> form) {
        // save invitation and update invitationlistpanel
        invitationManager.save(form.getModelObject());
        AjaxEventSender.entityEvent(this, Invitation.class);

        return true;
    }
}
