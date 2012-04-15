package de.flower.rmt.ui.panel;

import de.flower.common.ui.markup.html.list.EntityListView;
import de.flower.rmt.model.Activity;
import de.flower.rmt.service.IActivityManager;
import de.flower.rmt.util.Dates;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

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

        EntityListView<Activity> list = new EntityListView<Activity>("list", getListModel()) {
            @Override
            protected void populateItem(final ListItem<Activity> item) {
                item.add(new Label("date", getDateLabel(item.getModelObject().getDate())));
                item.add(new Label("message", item.getModelObject().toString()));
            }
        };
    }

    private IModel<List<Activity>> getListModel() {
        return null;
    }

    private String getDateLabel(Date date) {
        return Dates.formatFacebook(date);
    }
}
