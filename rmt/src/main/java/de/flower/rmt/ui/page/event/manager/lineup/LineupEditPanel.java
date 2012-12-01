package de.flower.rmt.ui.page.event.manager.lineup;

import de.flower.common.ui.ajax.AbstractParameterizedDefaultAjaxBehavior;
import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.panel.BasePanel;
import de.flower.common.ui.util.MarkupIdVisitor;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.LineupItem;
import de.flower.rmt.model.db.entity.QLineupItem;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.model.dto.InvitationDto;
import de.flower.rmt.service.ILineupManager;
import de.flower.rmt.ui.page.event.manager.teams.PlayerPanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
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

                DraggablePlayerPanel draggablePlayer = new DraggablePlayerPanel(lineupItem.getInvitation(), lineupItem, true) {
                    @Override
                    protected void onRemove(final AjaxRequestTarget target, final Long invitationId) {
                        // remove lineupitem
                        lineupManager.removeLineupItem(invitationId);
                        AjaxEventSender.entityEvent(this, LineupItem.class);
                    }
                };
                draggablePlayer.add(AttributeModifier.append("style", "top: " + lineupItem.getTop() + "px; left: " + lineupItem.getLeft() + "px;"));

                item.add(draggablePlayer);
            }
        };
        itemContainer.add(items);

        final AbstractDefaultAjaxBehavior behavior = new DropCallbackBehavior() {
            @Override
            protected void onDrop(final AjaxRequestTarget target, final InvitationDto dto) {
                lineupManager.drop(dto);
                AjaxEventSender.entityEvent(getComponent(), LineupItem.class);
            }
        };
        add(behavior);
        grid.add(AttributeModifier.replace("url", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                return "'" + behavior.getCallbackUrl().toString() + "'";
            }
        }));
    }

    private IModel<List<LineupItem>> getListModel(final IModel<Event> model) {
        return new LoadableDetachableModel<List<LineupItem>>() {
            @Override
            protected List<LineupItem> load() {
                return lineupManager.findLineupItems(model.getObject(), QLineupItem.lineupItem.invitation);
            }
        };
    }

    public abstract static class DropCallbackBehavior extends AbstractParameterizedDefaultAjaxBehavior {

        private final static Logger log = LoggerFactory.getLogger(DropCallbackBehavior.class);

        public Parameter<String> id = Parameter.of("id", String.class, "ui.helper[0].id");

        public Parameter<Long> top = Parameter.of("top", Long.class, "top");

        public Parameter<Long> left = Parameter.of("left", Long.class, "left");

        public Parameter<Long> width = Parameter.of("width", Long.class, "width");

        public Parameter<Long> height = Parameter.of("height", Long.class, "height");

        @Override
        protected void respond(final AjaxRequestTarget target, final ParameterMap parameterMap) {
            MarkupIdVisitor visitor = new MarkupIdVisitor(parameterMap.getValue(id));
            this.getComponent().getPage().visitChildren(visitor);
            Component draggedComponent = visitor.getFoundComponent();
            if (draggedComponent == null) {
                // happens if element is dragged during ajax-refresh of panel
                log.warn("Dropped component not found.");
                return;
            }
            InvitationDto dto = new InvitationDto();
            dto.invitationId = ((PlayerPanel) draggedComponent).getInvitationId();
            dto.top = parameterMap.getValue(top);
            dto.left = parameterMap.getValue(left);
            dto.width = parameterMap.getValue(width);
            dto.height = parameterMap.getValue(height);

            onDrop(target, dto);
        }

        @Override
        protected Parameter<?>[] getParameter() {
            return new Parameter[]{id, top, left, width, height};
        }

        protected abstract void onDrop(AjaxRequestTarget target, InvitationDto dto);
    }
}
