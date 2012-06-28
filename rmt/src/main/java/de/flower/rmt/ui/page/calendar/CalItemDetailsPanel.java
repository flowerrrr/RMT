package de.flower.rmt.ui.page.calendar;

import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.util.Dates;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import java.util.Date;

/**
 * @author flowerrrr
 */
public class CalItemDetailsPanel extends BasePanel<CalItem> {

    public CalItemDetailsPanel(final String id, final IModel<CalItem> model) {
        super(id, new CompoundPropertyModel<CalItem>(model));

        add(new Label("user.fullname"));

        // panel is always created when model-value changes. can access model object in constructor.
        add(new Label("type", new ResourceModel(CalItem.Type.getResourceKey(model.getObject().getType()))));

        add(new Label("summary"));

        add(DateLabel.forDatePattern("startDate", new PropertyModel<Date>(getModel(), "startDateTime.toDate()"), Dates.DATE_LONG));

        add(DateLabel.forDatePattern("startTime", new PropertyModel<Date>(getModel(), "startDateTime.toDate()"), Dates.TIME_SHORT).setVisible(!model.getObject().isAllDay()));

        add(DateLabel.forDatePattern("endDate", new PropertyModel<Date>(getModel(), "endDateTime.toDate()"), Dates.DATE_LONG));

        add(DateLabel.forDatePattern("endTime", new PropertyModel<Date>(getModel(), "endDateTime.toDate()"), Dates.TIME_SHORT).setVisible(!model.getObject().isAllDay()));

    }
}
