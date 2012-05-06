package de.flower.rmt.ui.panel.activityfeed;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.ajax.markup.repeater.AjaxAppendingRefreshingView;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.Activity;
import de.flower.rmt.model.Invitation;
import de.flower.rmt.model.event.Event;
import de.flower.rmt.service.IActivityManager;
import de.flower.rmt.ui.model.ActivityModel;
import de.flower.rmt.ui.panel.activityfeed.renderer.ActivityMessageRenderer;
import de.flower.rmt.util.Dates;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Panel displays activity feed.
 *
 * @author flowerrrr
 */
public class ActivityFeedPanel extends BasePanel {

    @SpringBean
    private IActivityManager activityManager;

    private int currentPage = 0;

    public ActivityFeedPanel() {
        super();
        add(new AjaxEventListener(Invitation.class, Event.class));

        WebMarkupContainer listContainer = new WebMarkupContainer("listContainer");
        final AjaxAppendingRefreshingView<Activity> list = new AjaxAppendingRefreshingView<Activity>("list", getListModel(currentPage)) {
            // EntityListView<Activity> list = new EntityListView<Activity>("list", getListModel()) {
            @Override
            protected void populateItem(final Item<Activity> item) {
                item.add(new Label("date", getDateLabel(item.getModelObject().getDate())));
                String message = renderMessage(item.getModelObject());
                item.add(new Label("message", message).setEscapeModelStrings(false));
                item.setVisible(message != null);
            }

            @Override
            protected Iterator<IModel<Activity>> getItemModels() {
                return transform((IModel<List<Activity>>) getDefaultModel());
            }
        };
        listContainer.add(list);
        add(listContainer);

        add(new AjaxLink.NoIndicatingAjaxLink<Void>("more") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                // load next N items
                Iterator<IModel<Activity>> itemModels = getNextPageItemModels();
                if (itemModels.hasNext()) {
                    list.appendNewItems(target, itemModels);
                } else {
                    // no more items to load
                    setVisible(false);
                    target.add(this);
                }
            }
        });
    }

    private Iterator<IModel<Activity>> transform(final IModel<List<Activity>> model) {
        return new ModelIteratorAdapter<Activity>(model.getObject().iterator()) {

            @Override
            protected IModel<Activity> model(final Activity object) {
                return new ActivityModel(object);
            }
        };
    }

    private String renderMessage(final Activity activity) {
        String message = null;
        try {
            message = ActivityMessageRenderer.toString(activity.getMessage());
        } catch (Exception e) {
            log.error("Could not render [" + activity + "]: " + e.toString(), e);
        }
        return message;
    }

    private IModel<List<Activity>> getListModel(final int page) {
        return new LoadableDetachableModel<List<Activity>>() {
            @Override
            protected List<Activity> load() {
                // whenever implementation of message classes change we risk a deserialization error
                // when loading those messages.
                try {
                    return activityManager.findLastN(page, 10);
                } catch (Exception e) {
                    log.error("Could not load activities: " + e.getMessage(), e);
                    return Collections.emptyList();
                }
            }
        };
    }

    private Iterator<IModel<Activity>> getNextPageItemModels() {
        currentPage++;
        return transform(getListModel(currentPage));
    }

    private String getDateLabel(Date date) {
        return Dates.formatFacebook(date);
    }
}
