package de.flower.rmt.ui.page.event.manager.lineup;

import de.flower.common.ui.ajax.dragndrop.DraggableDto;
import de.flower.common.ui.ajax.dragndrop.DroppableBehavior;
import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.behavior.AbsolutePositionBehavior;
import de.flower.common.ui.panel.BasePanel;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.Invitation;
import de.flower.rmt.model.db.entity.LineupItem;
import de.flower.rmt.model.db.entity.QLineupItem;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.ILineupManager;
import de.flower.rmt.ui.page.event.manager.lineup.dragndrop.DraggableEntityLabel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author flowerrrr
 */
public class LineupEditPanel extends BasePanel {

    private final static Logger log = LoggerFactory.getLogger(LineupEditPanel.class);

    @SpringBean
    private ILineupManager lineupManager;

    public LineupEditPanel(String id, final IModel<Event> model) {
        super(id);
        Check.notNull(model);

        final WebMarkupContainer grid = new WebMarkupContainer("grid");
        add(grid);

        final WebMarkupContainer itemContainer = new WebMarkupContainer("itemContainer");
        itemContainer.add(new AjaxEventListener(LineupItem.class));
        grid.add(itemContainer);

        // render existing lineup items
        ListView<LineupItem> items = new ListView<LineupItem>("items", getListModel(model)) {
            @Override
            protected void populateItem(final ListItem<LineupItem> item) {
                LineupItem lineupItem = item.getModelObject();

                Invitation invitation = lineupItem.getInvitation();
                DraggableEntityLabel draggablePlayer = new DraggableEntityLabel(invitation.getId(), invitation.getName(), true) {
                    @Override
                    protected void onRemove(final AjaxRequestTarget target, final Long invitationId) {
                        // remove lineupitem
                        lineupManager.removeLineupItem(invitationId);
                        AjaxEventSender.entityEvent(this, LineupItem.class);
                    }
                };
                draggablePlayer.add(new AbsolutePositionBehavior(lineupItem.getTop(), lineupItem.getLeft()));

                item.add(draggablePlayer);
            }
        };
        itemContainer.add(items);

        grid.add(new DroppableBehavior(false) {
            @Override
            protected void onDrop(final AjaxRequestTarget target, final DraggableDto dto) {
                lineupManager.drop(dto);
                AjaxEventSender.entityEvent(grid, LineupItem.class);
            }
        });

    }

    private IModel<List<LineupItem>> getListModel(final IModel<Event> model) {
        return new LoadableDetachableModel<List<LineupItem>>() {
            @Override
            protected List<LineupItem> load() {
                return lineupManager.findLineupItems(model.getObject(), QLineupItem.lineupItem.invitation);
            }
        };
    }
}
