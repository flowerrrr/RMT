package de.flower.rmt.ui.manager.page.events;

import de.flower.common.ui.ajax.MyAjaxSubmitLink;
import de.flower.common.ui.ajax.updatebehavior.AjaxRespondListener;
import de.flower.common.ui.ajax.updatebehavior.events.AjaxEvent;
import de.flower.common.ui.form.MyForm;
import de.flower.common.ui.form.TimeSelect;
import de.flower.common.ui.form.ValidatedTextField;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.model.event.EventType;
import de.flower.rmt.model.event.Match;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.ui.common.panel.BasePanel;
import de.flower.rmt.ui.manager.component.VenueSelect;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.jsr303.BeanValidator;

/**
 * @author oblume
 */
public class EventEditPanel extends BasePanel {

    private Form<Event> form;

    @SpringBean
    private IEventManager eventManager;

    public EventEditPanel(String id, IModel<Event> model) {
        super(id, model);

        add(new PreCreateEventEditPanel("selectEventType") {
            @Override
            public void onSelect(EventType eventType, AjaxRequestTarget target) {
                Event event = eventManager.newInstance(eventType);
                EventEditPanel.this.setDefaultModelObject(event);
                form.setModel(new CompoundPropertyModel<Event>(event));
                target.add(this, form);
            }

            @Override
            public boolean isVisible() {
                //  only display at beginning of new event dialog
                return EventEditPanel.this.getDefaultModelObject() == null;
            }
        }.setOutputMarkupId(true));

        form = new MyForm<Event>("form", (model.getObject() == null) ? new Match() : model.getObject()) {
            @Override
            public boolean isVisible() {
                return EventEditPanel.this.getDefaultModelObject() != null;
            }
        };
        form.setOutputMarkupPlaceholderTag(true);
        add(form);

        DateTextField dateField = DateTextField.forDateStyle("date", "S-");
        // dateField.add(new DatePicker());
        form.add(dateField);
        form.add(new FeedbackPanel("feedback_date", new ComponentFeedbackMessageFilter(dateField)));

        TimeSelect timeField = new TimeSelect("time");
        form.add(timeField);

        VenueSelect venueSelect = new VenueSelect("venue");
        form.add(venueSelect);

        // form.add(surface label)

        form.add(new ValidatedTextField("summary"));

        form.add(new TextArea("comment"));

        // form.add(participants)


        form.add(new MyAjaxSubmitLink("saveButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (!new BeanValidator(form).isValid(form.getModelObject())) {
                    onError(target, form);
                } else {
                    eventManager.save((Event) form.getModelObject());
                    target.registerRespondListener(new AjaxRespondListener(AjaxEvent.EntityCreated(Event.class), AjaxEvent.EntityUpdated(Event.class)));
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
            }
        });
    }


}
