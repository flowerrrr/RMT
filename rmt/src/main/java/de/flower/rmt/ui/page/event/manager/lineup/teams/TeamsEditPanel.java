package de.flower.rmt.ui.page.event.manager.lineup.teams;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxEditableLabelExtended;
import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.EventTeam;
import de.flower.rmt.model.db.entity.Lineup;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.IEventTeamManager;
import de.flower.rmt.service.ILineupManager;
import de.flower.rmt.ui.model.LineupModel;
import de.flower.rmt.ui.page.event.manager.lineup.teams.TeamsSecondaryPanel.EventTeamInviteeListPanel;
import de.flower.rmt.ui.panel.RMTBasePanel;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Panel serves manager and player view.
 *
 * @author flowerrrr
 */
public class TeamsEditPanel extends RMTBasePanel {

    private final static Logger log = LoggerFactory.getLogger(TeamsEditPanel.class);

    @SpringBean
    private IEventTeamManager eventTeamManager;

    @SpringBean
    private ILineupManager lineupManager;

    public TeamsEditPanel(String id, final IModel<Event> model) {
        super(id);
        Check.notNull(model);
        add(new AjaxEventListener(EventTeam.class));

        final IModel<List<EventTeam>> listModel = getListModel(model);
        final IModel<Lineup> lineupModel = new LineupModel(model);
        // render existing event teams
        final ListView<EventTeam> teamList = new ListView<EventTeam>("teamList", listModel) {
            @Override
            protected void populateItem(final ListItem<EventTeam> item) {
                // item.add(new Label("name", item.getModelObject().getName()));
                final AjaxEditableLabel<String> editableLabel = new AjaxEditableLabelExtended<String>("name", new PropertyModel<String>(item.getModel(), "name")) {
                    {
                        getEditor().add(AttributeModifier.replace("maxlength", 15));
                        setEnabled(TeamsEditPanel.this.isManagerView());
                    }

                    @Override
                    protected void onSubmit(final AjaxRequestTarget target) {
                        super.onSubmit(target);
                        if (!StringUtils.isBlank(item.getModelObject().getName())) {
                            eventTeamManager.save(item.getModelObject());
                        }
                    }
                };
                item.add(editableLabel);
                item.add(new EventTeamPanel(item.getModel()));
                AjaxLinkWithConfirmation button = new AjaxLinkWithConfirmation("removeTeamButton", new ResourceModel("manager.eventteam.remove.confirm")) {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {
                        eventTeamManager.removeTeam(item.getModelObject());
                        AjaxEventSender.entityEvent(this, EventTeam.class);
                        AjaxEventSender.send(this, EventTeamInviteeListPanel.class);
                    }
                };
                button.setVisible(TeamsEditPanel.this.isManagerView());
                item.add(button);
            }

            @Override
            public boolean isVisible() {
                return isManagerView() || lineupModel.getObject().isPublished();
            }
        };
        add(teamList);

        add(new WebMarkupContainer("noLineup", lineupModel /* pass to component so it gets detached automatically */) {

            @Override
            public boolean isVisible() {
                return !lineupModel.getObject().isPublished() && !isManagerView();
            }
        });

        add(new RankingPanel(model));
    }

    private IModel<List<EventTeam>> getListModel(final IModel<Event> model) {
        return new LoadableDetachableModel<List<EventTeam>>() {
            @Override
            protected List<EventTeam> load() {
                return eventTeamManager.findTeams(model.getObject());
            }
        };
    }

}
