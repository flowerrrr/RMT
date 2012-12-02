package de.flower.rmt.ui.page.event.manager.lineup;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.Lineup;
import de.flower.rmt.model.db.entity.LineupItem;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.ILineupManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class LineupSecondaryPanel extends BasePanel {

    public LineupSecondaryPanel(final IModel<Event> model) {

        // treat subpanels as top level secondary panels to have spacer between them
        setRenderBodyOnly(true);

        add(new PublishPanel(model));

        add(new LineupInviteeListPanel(model));
    }

    public static class LineupInviteeListPanel extends DraggableInviteeListPanel {

        @SpringBean
        private ILineupManager lineupManager;

        // used to filter out those players that are already dragged to the lineup-grid.
        private final IModel<List<Invitation>> lineupItemListModel;

        public LineupInviteeListPanel(final IModel<Event> model) {
            super(model);
            add(new AjaxEventListener(LineupItem.class));

            lineupItemListModel = new LoadableDetachableModel<List<Invitation>>() {
                @Override
                protected List<Invitation> load() {
                    return lineupManager.findInvitationsInLinuep(model.getObject());
                }
            };
        }

        @Override
        protected boolean isDraggablePlayerVisible(final Invitation invitation) {
            return lineupItemListModel.getObject().contains(invitation);
        }

        @Override
        protected void onDetach() {
            super.onDetach();
            if (lineupItemListModel != null) {
                lineupItemListModel.detach();
            }
        }
    }

    public static class PublishPanel extends WebMarkupContainer {

        @SpringBean
        private ILineupManager lineupManager;

        public PublishPanel(final IModel<Event> model) {
            super("publishPanel");
            add(new AjaxEventListener(Lineup.class));
            final IModel<Lineup> lineupModel = new LoadableDetachableModel<Lineup>() {
                @Override
                protected Lineup load() {
                    return lineupManager.findLineup(model.getObject());
                }
            };
            add(new AjaxLink("publishButton") {
                @Override
                public void onClick(final AjaxRequestTarget target) {
                    lineupManager.publishLineup(model.getObject());
                    lineupModel.detach();
                    AjaxEventSender.entityEvent(this, Lineup.class);
                }

                @Override
                public boolean isVisible() {
                    return !lineupModel.getObject().isPublished();
                }
            });
            add(new WebMarkupContainer("publishedMessage") {
                @Override
                public boolean isVisible() {
                    return lineupModel.getObject().isPublished();
                }
            });
        }
    }
}
