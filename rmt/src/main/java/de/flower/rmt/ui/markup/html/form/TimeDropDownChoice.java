package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.util.Dates;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class TimeDropDownChoice extends DropDownChoice<LocalTime> {

    private final static Logger log = LoggerFactory.getLogger(TimeDropDownChoice.class);

    public TimeDropDownChoice(String id) {
        super(id);
        setOutputMarkupId(true);
        setChoices(getTimeChoices());
        setChoiceRenderer(new IChoiceRenderer<LocalTime>() {
            @Override
            public Object getDisplayValue(LocalTime time) {
                return Dates.formatTimeShort(time);
            }

            @Override
            public String getIdValue(LocalTime object, int index) {
                return "" + index;
            }
        });
    }

    /**
     * In case time value does not match 15-minute interval gaps tweak the value to the nearest time available.
     */
    @Override
    public String getModelValue()
    {
        final LocalTime object = getModelObject();
        if (object != null)
        {
            int index = getChoices().indexOf(object);
            // PATCH BEGIN
            if (index == -1) {
                log.warn("Could not match [{}] to available choices. Returning closest match");
                index = closestMatch(object, getChoices());
            }
            // PATCH END
            return getChoiceRenderer().getIdValue(object, index);
        }
        else
        {
            return "";
        }
    }

    public static int closestMatch(final LocalTime object, List<? extends LocalTime> choices) {
        for (int i = 0; i < choices.size() - 1; i++) {
            LocalTime a = choices.get(i);
            LocalTime b = choices.get(i + 1);
            if (a.isBefore(object) && b.isAfter(object)) {
                if (object.getMillisOfDay() - a.getMillisOfDay() > b.getMillisOfDay() - object.getMillisOfDay()) {
                    return i + 1;
                } else {
                    return i;
                }
            }
        }
        // RMT-732
        // match everything past 23:45 to last entry in choices.
        return choices.size() - 1;
    }

    public static List<LocalTime> getTimeChoices() {
        List<LocalTime> list = new ArrayList<LocalTime>();
        LocalTime start = LocalTime.fromMillisOfDay(0);
        LocalTime end = start.minusMillis(1);
        int interval = 15;
        int millis = start.getMillisOfDay();
        while (millis <= end.getMillisOfDay()) {
            list.add(LocalTime.fromMillisOfDay(millis));
            millis += interval * 60 * 1000;
        }
        return list;
    }


}
