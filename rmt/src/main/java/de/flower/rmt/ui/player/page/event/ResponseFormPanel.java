package de.flower.rmt.ui.player.page.event;

import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.Response;
import de.flower.rmt.ui.common.form.EntityForm;
import de.flower.rmt.ui.common.panel.BasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author flowerrrr
 */
public abstract class ResponseFormPanel extends BasePanel {

    private EntityForm<Response> form;

    public ResponseFormPanel(final IModel<Response> model) {
        form = new EntityForm<Response>("form", model) {

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Response> form) {
                ResponseFormPanel.this.onSubmit(form.getModelObject(), target);
                onClose();
            }
        };
        add(form);
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

    protected abstract void onSubmit(Response response, AjaxRequestTarget target);

 }
