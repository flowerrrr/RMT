package de.flower.rmt.ui.panel.activityfeed;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.rmt.model.Activity;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IActivityManager;
import de.flower.rmt.ui.panel.BasePanel;
import de.flower.rmt.ui.panel.activityfeed.renderer.ActivityMessageRenderer;
import de.flower.rmt.util.Dates;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Panel displays activity feed.
 *
 * @author flowerrrr
 */
public class ActivityFeedPanel extends BasePanel {

    @SpringBean
    private IActivityManager activityManager;

    public ActivityFeedPanel() {
        super();
        add(new AjaxEventListener(Invitation.class, Event.class));

        EntityListView<Activity> list = new EntityListView<Activity>("list", getListModel()) {
            @Override
            protected void populateItem(final ListItem<Activity> item) {
                item.add(new Label("date", getDateLabel(item.getModelObject().getDate())));
                item.add(new Label("message", renderMessage(item.getModelObject())).setEscapeModelStrings(false));
            }
        };
        add(list);
    }

    private String renderMessage(final Activity activity) {
        String message;
        try {
            message = ActivityMessageRenderer.toString(activity.getMessage()) ;
        }   catch (Exception e) {
            log.error("Could not render [" + activity + "]: " + e.toString(), e);
            message = activity.getMessage().toString();
        }
        return message;
    }

    private IModel<List<Activity>> getListModel() {
        return new LoadableDetachableModel<List<Activity>>() {
            @Override
            protected List<Activity> load() {
                // whenever implementation of message classes change we risk a deserialization error
                // when loading those messages.
                try {
                    return activityManager.findLastN(10);
                } catch (Exception e) {
                    log.error("Could not load activities: " + e.getMessage(), e);
                    return Collections.emptyList();
                }
            }
        };
    }

    private String getDateLabel(Date date) {
        return Dates.formatFacebook(date);
    }
}
