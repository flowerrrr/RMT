package de.flower.rmt.ui.page.calendar.player;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
import de.flower.common.ui.panel.BasePanel;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.service.ICalendarManager;
import de.flower.rmt.ui.markup.html.form.DatePicker;
import de.flower.rmt.ui.markup.html.form.EntityForm;
import de.flower.rmt.ui.markup.html.form.TimeDropDownChoice;
import de.flower.rmt.ui.markup.html.form.renderer.CalItemTypeRenderer;
import de.flower.rmt.util.Dates;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Arrays;

/**
 * @author flowerrrr
 */
public class CalItemEditPanel extends BasePanel<CalItemDto> {

    @SpringBean
    private ICalendarManager calendarManager;

    private FormComponent startDateTime;

    private FormComponent endDateTime;

    /**
     * Not correct to use readonly direct instead of a model. but who cares. panel is always created on request.
     */
    public CalItemEditPanel(final String id, final IModel<CalItemDto> model) {
        super(id, model);
        Check.notNull(model);

        add(new AjaxEventListener(CalItem.class));

        add(new Label("heading", new StringResourceModel("calendar.editpanel.new.${new}.heading", model)));

        final EntityForm<CalItemDto> form = new EntityForm<CalItemDto>("form", model) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<CalItemDto> form) {
                calendarManager.save(form.getModelObject());
                AjaxEventSender.entityEvent(this, CalItem.class);
                onClose(target);
            }
        };
        add(form);

        form.add(new CalItemTypeDropDownChoice("type"));

        form.add(new TextField("summary"));

        form.add(new AjaxCheckBox("allDay") {

            @Override
            protected void onUpdate(final AjaxRequestTarget target) {
                target.add(startDateTime);
                target.add(endDateTime);
            }
        });

        form.add(new DatePicker("startDate", Dates.DATE_MEDIUM));

        form.add(startDateTime = new TimeDropDownChoice("startTime") {
            @Override
            public boolean isVisible() {
                return !form.getModelObject().isAllDay();
            }
        });
        startDateTime.setOutputMarkupPlaceholderTag(true);

        form.add(new DatePicker("endDate", Dates.DATE_MEDIUM));

        form.add(endDateTime = new TimeDropDownChoice("endTime") {
            @Override
            public boolean isVisible() {
                return !form.getModelObject().isAllDay();
            }
        });
        endDateTime.setOutputMarkupPlaceholderTag(true);

        form.add(new CheckBox("autoDecline"));

        form.add(new AjaxLink.NoIndicatingAjaxLink("cancelButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                onClose(target);
            }
        });

        form.add(new AjaxLinkWithConfirmation("deleteButton", new ResourceModel("calendar.editpanel.delete.confirm")) {
            @Override
            public void onClick(AjaxRequestTarget target) {
                calendarManager.delete(model.getObject().getId());
                AjaxEventSender.entityEvent(this, CalItem.class);
                onClose(target);
            }

            @Override
            public boolean isVisible() {
                return !model.getObject().isNew();
            }
        });
    }

    public static class CalItemTypeDropDownChoice extends DropDownChoice<CalItem.Type> {

        public CalItemTypeDropDownChoice(String id) {
            super(id);
            setChoices(Arrays.asList(CalItem.Type.values()));
            setChoiceRenderer(new CalItemTypeRenderer());
        }
    }
}
