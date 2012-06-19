package de.flower.common.ui.calendar;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.CalItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.util.string.StringValue;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Calendar widget based on wonderful http://arshaw.com/fullcalendar/.
 *
 * @author flowerrrr
 */
public abstract class FullCalendarPanel extends BasePanel {

    private final static String fullCalendarCssUrl = "js/fullcalendar-1.5.3/fullcalendar.css";

    private final static String fullCalendarJsUrl = "js/fullcalendar-1.5.3/fullcalendar.js";

    private AbstractAjaxBehavior jsonEventSourceBehavior;

    private AbstractDefaultAjaxBehavior eventClickBehavior;

    public FullCalendarPanel() {

        add(jsonEventSourceBehavior = new JSONEventSourceBehavior());

        add(eventClickBehavior = new AbstractDefaultAjaxBehavior() {
            @Override
            protected void respond(final AjaxRequestTarget target) {
                StringValue id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id");
                onEdit(target, id.toLong());
            }
        });
    }

    @Override
    protected String getPanelMarkup() {
        return "";
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        super.renderHead(response);
        response.renderCSSReference(fullCalendarCssUrl);
        response.renderJavaScriptReference(fullCalendarJsUrl);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String options = gson.toJson(getOptions());
        // remove qoutes on function literals (gson does not allow serializing function literals without quotes)
        options = options.replace("\"_eventClick_\"", String.format("function(event) { wicketAjaxGet('%s&id=' + event.id); return false; }", eventClickBehavior.getCallbackUrl()));
        System.out.println(options);
        String initScript = "$('#" + getMarkupId() + "').fullCalendar(" + options + ");";
        response.renderOnDomReadyJavaScript(initScript);
    }

    private Options getOptions() {
        Options o = new Options();
        o.events = jsonEventSourceBehavior.getCallbackUrl().toString();
        o.eventClick = "_eventClick_"; // will be substituted after converting to json
        o.monthNames = StringUtils.split(getResourceString("monthNames"), ",");
        o.monthNamesShort = StringUtils.split(getResourceString("monthNamesShort"), ",");
        o.dayNames = StringUtils.split(getResourceString("dayNames"), ",");
        o.dayNamesShort = StringUtils.split(getResourceString("dayNamesShort"), ",");
        o.buttonText.put("today", getResourceString("today"));
        return o;
    }

    private String getResourceString(String property) {
        return new ResourceModel(property).wrapOnAssignment(this).getObject();
    }

    protected abstract List<CalItem> loadCalItems(final Date start, final Date end);

    protected abstract void onEdit(AjaxRequestTarget target, long id);

    public static class Options {

        public String events;

        public String eventClick;

        public int firstDay = 1; // start week on monday;

        public String timeFormat = ""; // no time on events

        public String[] monthNames;

        public String[] monthNamesShort;

        public String[] dayNames;

        public String[] dayNamesShort;

        public Map<String, String> buttonText = new HashMap<>();
    }

    public class JSONEventSourceBehavior extends AbstractAjaxBehavior {

        @Override
        public void onRequest() {
            RequestCycle requestCycle = RequestCycle.get();
            StringValue start = requestCycle.getRequest().getRequestParameters().getParameterValue("start");
            StringValue end = requestCycle.getRequest().getRequestParameters().getParameterValue("end");

            List<CalItem> list = loadCalItems(new Date(start.toLong()), new Date(end.toLong()));

            Collection<CalEvent> events = Collections2.transform(list, new Function<CalItem, CalEvent>() {
                @Override
                public CalEvent apply(@Nullable final CalItem item) {
                    CalEvent o = new CalEvent();
                    o.id = item.getId();
                    o.title = new ResourceModel(CalItem.Type.getResourceKey(item.getType())).getObject() + ": " + item.getSummary();
                    o.start = item.getStartDateTime().toDate();
                    o.end = item.getEndDateTime().toDate();
                    o.allDay = item.isAllDay();
                    return o;
                }
            });

            Gson gson = new Gson();
            String json = gson.toJson(events);

            requestCycle.scheduleRequestHandlerAfterCurrent(new TextRequestHandler("application/json", "UTF-8", json));
        }
    }

    public static class CalEvent {

        public Long id;

        public String title;

        public Date start;

        public Date end;

        public boolean allDay;
    }
}
