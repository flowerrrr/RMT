package de.flower.rmt.ui.player.page.events;

import de.flower.common.ui.ajax.MyAjaxLink;
import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.common.ui.form.EntityForm;
import de.flower.common.util.Check;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.model.Response;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.ui.common.IEntityEditPanel;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.model.ResponseModel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jsr303.BeanValidator;

/**
 * @author flowerrrr
 */
public abstract class ResponseFormPanel extends BasePanel implements IEntityEditPanel<Response> {

    private EntityForm<Response> form;

    public ResponseFormPanel(final String id, final IModel<Event> eventModel) {
        super(id);

        form = new EntityForm("form", new ResponseModel(eventModel.getObject()));
        add(form);
        final RadioGroup group = new RadioGroup("status");
        form.add(group);
        group.add(new Radio("accepted", Model.of(RSVPStatus.ACCEPTED)));
        group.add(new Radio("declined", Model.of(RSVPStatus.DECLINED)));
        group.add(new Radio("unsure", Model.of(RSVPStatus.UNSURE)));
        group.add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(group)));
        form.add(new TextArea("comment"));
        form.add(new MyAjaxSubmitLink("respondButton") {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
                Response response = (Response) form.getModelObject();
                if (!new BeanValidator(form).isValid(response)) {
                    onError(target, form);
                } else {
                    ResponseFormPanel.this.onSubmit(response, target);
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
    }

    @Override
	public void init(IModel<Response> model) {
        Check.notNull(model);
        form.replaceModel(model);
    }


    protected abstract void onSubmit(Response response, AjaxRequestTarget target);

 }
