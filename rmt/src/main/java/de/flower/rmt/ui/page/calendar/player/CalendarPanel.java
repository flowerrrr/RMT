package de.flower.rmt.ui.page.calendar.player;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.CalItem;
import de.flower.rmt.model.db.entity.User;
import de.flower.rmt.model.dto.CalItemDto;
import de.flower.rmt.service.ICalendarManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author flowerrrr
 */
public abstract class CalendarPanel extends BasePanel<User> {

    @SpringBean
    private ICalendarManager calendarManager;

    public CalendarPanel(final IModel<User> model) {
        super(model);

        add(new AjaxEventListener(CalItem.class));

        IModel<List<CalItem>> listModel = getListModel(model);

        ListView<CalItem> list = new ListView<CalItem>("list", listModel) {
            @Override
            protected void populateItem(final ListItem<CalItem> item) {
                String s = item.getModelObject().toString();
                item.add(new AjaxLink("editButton") {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {
                        CalItemDto dto = CalItemDto.fromEntity(item.getModelObject());
                        onEdit(target, dto);
                    }
                })      ;
                item.add(new Label("item", s));
            }
        };
        add(list);

        add(new AjaxLink("addButton") {

            @Override
            public void onClick(final AjaxRequestTarget target) {
                DateTime dt = new DateTime().withTime(8, 0, 0, 0);
                CalItemDto dto = new CalItemDto();
                dto.setAllDay(true);
                dto.setAutoDecline(true);
                dto.setStartDateTime(dt);
                dto.setEndDateTime(dt.plusHours(12));
                onEdit(target, dto);
            }
        });
    }

    protected abstract void onEdit(AjaxRequestTarget target, CalItemDto calItemDto);

    public IModel<List<CalItem>> getListModel(final IModel<User> userModel) {
        return new LoadableDetachableModel<List<CalItem>>() {
            @Override
            protected List<CalItem> load() {
                return calendarManager.findAllByUser(userModel.getObject());
            }
        };
    }
}
