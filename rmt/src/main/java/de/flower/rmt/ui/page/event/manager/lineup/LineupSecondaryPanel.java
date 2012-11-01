package de.flower.rmt.ui.page.event.manager.lineup;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.panel.BasePanel;
import de.flower.rmt.model.db.entity.Lineup;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.ILineupManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

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
