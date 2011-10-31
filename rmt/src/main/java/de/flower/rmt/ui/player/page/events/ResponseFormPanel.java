package de.flower.rmt.ui.player.page.events;

import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.jsr303.BeanValidator;
import org.wicketstuff.jsr303.PropertyValidation;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author flowerrrr
 */
public abstract class ResponseFormPanel extends BasePanel {

    public ResponseFormPanel(final String id) {
        super(id);

        final FormResponse formResponse = new FormResponse();

        Form<?> form = new Form("form");
        add(form);
        final RadioGroup group = new RadioGroup("responseStatusGroup", new PropertyModel(formResponse, "status"));
        form.add(group);
        group.add(new Radio("accepted", Model.of(RSVPStatus.ACCEPTED)));
        group.add(new Radio("declined", Model.of(RSVPStatus.DECLINED)));
        group.add(new Radio("unsure", Model.of(RSVPStatus.UNSURE)));
        form.add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(group)));
        form.add(new TextArea("commentArea", new PropertyModel(formResponse, "comment")));
        form.add(new MyAjaxSubmitLink("respondButton") {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
                if (!new BeanValidator(form).isValid(formResponse)) {
                    onError(target, form);
                } else {
                    ResponseFormPanel.this.onSubmit(formResponse, target);
                    onClose(target);
                }
            }
        });
        form.add(new MyAjaxLink("cancelButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                onClose(target);
            }
        });
        form.add(new PropertyValidation());
    }

    protected abstract void onSubmit(FormResponse formResponse, AjaxRequestTarget target);

    // TODO (flowerrrr - 23.10.11) after moving comment field into Response entity this class is rather obsolete.
    // also, it's redundant to have the constraints declared here.
    public static class FormResponse implements Serializable {

        @NotNull(message = "{validation.rsvpstatus.notnull}")
        private RSVPStatus status;

        private String comment;

        public RSVPStatus getStatus() {
            return status;
        }

        public void setStatus(final RSVPStatus status) {
            this.status = status;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(final String comment) {
            this.comment = comment;
        }
    }
}
