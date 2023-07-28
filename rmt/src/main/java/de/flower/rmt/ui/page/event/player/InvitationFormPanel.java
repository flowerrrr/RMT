package de.flower.rmt.ui.page.event.player;

import de.flower.common.ui.markup.html.form.TextAreaMaxLengthBehavior;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Comment;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.db.type.RSVPStatus;
import de.flower.rmt.ui.markup.html.form.EntityForm;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;


public abstract class InvitationFormPanel extends BasePanel {

    public InvitationFormPanel(String id, final IModel<Invitation> model, final IModel<Boolean> eventClosedModel) {
        super(id, model);

        EntityForm<Invitation> form = new EntityForm<Invitation>("form", model) {

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Invitation> form) {
                InvitationFormPanel.this.onSubmit(form.getModelObject(), target);
                onClose(target);
            }

            @Override
            protected boolean isShowSuccessFeedbackPanel() {
                // don't have to display success message. not necessary on this form.
                return false;
            }

            @Override
            public boolean isVisible() {
                return !eventClosedModel.getObject();
            }
        };
        add(form);

        final RadioGroup group = new RadioGroup("status") {
            @Override
            public boolean isVisible() {
                return !eventClosedModel.getObject();
            }
        };
        form.add(group);
        group.add(new Radio<RSVPStatus>("accepted", Model.of(RSVPStatus.ACCEPTED)));
        group.add(new Radio<RSVPStatus>("unsure", Model.of(RSVPStatus.UNSURE)));
        group.add(new Radio<RSVPStatus>("declined", Model.of(RSVPStatus.DECLINED)));
        group.add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(group)) {
            @Override
            public boolean isVisible() {
                return anyMessage();
            }
        });

        form.add(new TextArea("comment") {
            {
                add(new TextAreaMaxLengthBehavior(Comment.MAXLENGTH));
            }
        });

        add(new Label("invitationClosedMessage", new StringResourceModel("player.event.closed.message", new PropertyModel<User>(model, "event.createdBy"))) {
            @Override
            public boolean isVisible() {
                return eventClosedModel.getObject();
            }
        }.setEscapeModelStrings(false));
    }

    protected abstract void onSubmit(Invitation invitation, AjaxRequestTarget target);
}
