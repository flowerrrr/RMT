package de.flower.rmt.ui.player.page.events;

import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

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
        form.add(new TextArea("commentArea", new PropertyModel(formResponse, "comment")));
        form.add(new MyAjaxSubmitLink("respondButton") {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
                ResponseFormPanel.this.onSubmit(formResponse, target);
                onClose(target);
            }
        });
        form.add(new MyAjaxLink("cancelButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                onClose(target);
            }
        });
    }

    protected abstract void onSubmit(FormResponse formResponse, AjaxRequestTarget target);

    // TODO (flowerrrr - 23.10.11) after moving comment field into Response entity this class is rather obsolete.
    public static class FormResponse {

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
