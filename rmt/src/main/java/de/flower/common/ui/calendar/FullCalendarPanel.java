package de.flower.common.ui.calendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.flower.common.ui.panel.BasePanel;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.util.string.StringValue;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Calendar widget based on wonderful http://arshaw.com/fullcalendar/.
 *
 * @author flowerrrr
 */
public abstract class FullCalendarPanel extends BasePanel {

    // TODO (flowerrrr - 23.06.12) let wicket inject url
    private final static String fullCalendarCssUrl = "static/js/fullcalendar-1.5.3/fullcalendar.css";

    private final static String fullCalendarJsUrl = "static/js/fullcalendar-1.5.3/fullcalendar.js";

    private AbstractAjaxBehavior jsonEventSourceBehavior;

    private EventClickCallbackBehavior eventClickCallbackBehavior;

    private SelectCallbackBehavior selectCallbackBehavior;

    /**
     * tracks current date of calendar so that ajax-refresh of this panel stays on previous selected date.
     */
    private DateTime currentDate;

    public FullCalendarPanel() {

        add(jsonEventSourceBehavior = new JSONEventSourceBehavior() {
            @Override
            protected List<CalEvent> loadCalEvents(final DateTime start, final DateTime end) {
                currentDate = getCurrentDate(start, end);
                return FullCalendarPanel.this.loadCalEvents(start, end);
            }
        });

        add(eventClickCallbackBehavior = new EventClickCallbackBehavior() {

            @Override
            protected void onEdit(final AjaxRequestTarget target, final CalEvent calEvent) {
                FullCalendarPanel.this.onEventClick(target, calEvent);
            }
        });

        add(selectCallbackBehavior = new SelectCallbackBehavior() {

            @Override
            protected void onEdit(final AjaxRequestTarget target, final CalEvent calEvent) {
                FullCalendarPanel.this.onEventClick(target, calEvent);
            }
        });
    }

    @Override
    protected String getPanelMarkup() {
        return "";
    }

    /**
     * Need to save currently displayed month.
     * start could be before current month, and end most likely is currentMonth + 1.
     * so, find date in the middle and use its month.
     */
    private DateTime getCurrentDate(DateTime start, DateTime end) {
       return new DateTime((start.getMillis() + end.getMillis()) / 2);
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
        response.renderCSSReference(fullCalendarCssUrl);
        response.renderJavaScriptReference(fullCalendarJsUrl);
        super.renderHead(response);
        String initScript = "$('#" + getMarkupId() + "').fullCalendar(" + getJsonOptions() + ");";
        response.renderOnDomReadyJavaScript(initScript);
    }

    private String getJsonOptions() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String options = gson.toJson(getOptions());

        // replace placeholder with function literal (gson does not allow serializing function literals without quotes)
        options = options.replace("\"_eventClick_\"", eventClickCallbackBehavior.getCallbackFunction());

        options = options.replace("\"_select_\"", selectCallbackBehavior.getCallbackFunction());

        return options;
    }

    private Map<String, Object> getOptions() {
        Map<String, Object> options = new HashMap<>();
        options.put("events", jsonEventSourceBehavior.getCallbackUrl().toString());
        options.put("eventClick", "_eventClick_"); // will be substituted after converting to json
        options.put("selectable", true);
        options.put("select", "_select_");
        options.put("firstDay", 1); // start week on monday
        options.put("weekMode", "liquid");
        options.put("timeFormat", ""); // no time on events
        options.put("monthNames", StringUtils.split(getResourceString("monthNames"), ","));
        options.put("monthNamesShort", StringUtils.split(getResourceString("monthNamesShort"), ","));
        options.put("dayNames", StringUtils.split(getResourceString("dayNames"), ","));
        options.put("dayNamesShort", StringUtils.split(getResourceString("dayNamesShort"), ","));
        if (currentDate != null) {
            options.put("year", currentDate.getYear());
            options.put("month", currentDate.getMonthOfYear() - 1);
        }

        Map<String, Object> buttonText = new HashMap<>();
        buttonText.put("today", getResourceString("today"));

        options.put("buttonText", buttonText);

        return options;
    }

    private String getResourceString(String property) {
        return new ResourceModel(property).wrapOnAssignment(this).getObject();
    }

    protected abstract List<CalEvent> loadCalEvents(final DateTime start, final DateTime end);

    protected abstract void onEventClick(AjaxRequestTarget target, CalEvent calEvent);

    /**
     * Providing json feed of calendar events.
     */
    public abstract static class JSONEventSourceBehavior extends AbstractAjaxBehavior {

        @Override
        public void onRequest() {
            RequestCycle requestCycle = RequestCycle.get();
            StringValue start = requestCycle.getRequest().getRequestParameters().getParameterValue("start");
            StringValue end = requestCycle.getRequest().getRequestParameters().getParameterValue("end");

            List<CalEvent> events = loadCalEvents(new DateTime(start.toLong() * 1000), new DateTime(end.toLong() * 1000));

            Gson gson = new Gson();
            String json = gson.toJson(events);

            requestCycle.scheduleRequestHandlerAfterCurrent(new TextRequestHandler("application/json", "UTF-8", json));
        }

        protected abstract List<CalEvent> loadCalEvents(final DateTime start, final DateTime end);
    }
}
