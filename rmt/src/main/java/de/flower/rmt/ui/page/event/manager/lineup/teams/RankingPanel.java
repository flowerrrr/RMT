package de.flower.rmt.ui.page.event.manager.lineup.teams;

import de.flower.common.ui.ajax.dragndrop.DraggableDto;
import de.flower.common.ui.ajax.dragndrop.DroppableBehavior;
import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.EventTeam;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.IEventManager;
import de.flower.rmt.service.IEventTeamManager;
import de.flower.rmt.ui.page.event.manager.lineup.dragndrop.DraggableEntityLabel;
import de.flower.rmt.ui.page.event.manager.lineup.dragndrop.EntityLabel;
import de.flower.rmt.ui.panel.RMTBasePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Panel servers manager and player view.
 * @author flowerrrr
 */
public class RankingPanel extends RMTBasePanel<Event> {

    private final static Logger log = LoggerFactory.getLogger(RankingPanel.class);

    @SpringBean
    private IEventTeamManager eventTeamManager;

    @SpringBean
    private IEventManager eventManager;

    public RankingPanel(final IModel<Event> model) {
        super(model);
        Check.notNull(model);
        add(new AjaxEventListener(EventTeam.class));

        final IModel<List<EventTeam>> listModel = getListModel(model);
        // render existing event teams
        ListView<EventTeam> items = new ListView<EventTeam>("items", listModel) {
            @Override
            protected void populateItem(final ListItem<EventTeam> item) {
                EntityLabel entityLabel = new DraggableEntityLabel(item.getModelObject().getId(), item.getModelObject().getName(), false);
                item.add(entityLabel);
                if (RankingPanel.this.isManagerView()) {
                    item.add(new DroppableBehavior(true) {
                        @Override
                        protected void onDrop(final AjaxRequestTarget target, final DraggableDto dto) {
                            eventTeamManager.setRank(dto.entityId, item.getModelObject());
                            AjaxEventSender.entityEvent(RankingPanel.this, EventTeam.class);
                        }
                    });
                }
            }
        };
        add(items);
    }

    @Override
    public boolean isVisible() {
        return getModelObject().getDateTimeEnd().isBefore(DateTime.now());
    }

    private IModel<List<EventTeam>> getListModel(final IModel<Event> model) {
        return new LoadableDetachableModel<List<EventTeam>>() {
            @Override
            protected List<EventTeam> load() {
                return eventTeamManager.findTeamsOrderByRank(model.getObject());
            }
        };
    }
}
