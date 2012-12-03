package de.flower.rmt.ui.page.event.manager.teams;

import de.flower.common.ui.ajax.event.AjaxEventListener;
import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.ajax.markup.html.AjaxLink;
import de.flower.common.ui.ajax.markup.html.AjaxLinkWithConfirmation;
import de.flower.common.ui.panel.BasePanel;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.EventTeam;
import de.flower.rmt.model.db.entity.event.Event;
import de.flower.rmt.service.IEventTeamManager;
import de.flower.rmt.ui.page.event.manager.teams.TeamsSecondaryPanel.EventTeamInviteeListPanel;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
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
 * @author flowerrrr
 */
public class TeamsEditPanel extends BasePanel {

    private final static Logger log = LoggerFactory.getLogger(TeamsEditPanel.class);

    @SpringBean
    private IEventTeamManager eventTeamManager;

    public TeamsEditPanel(String id, final IModel<Event> model) {
        super(id);
        Check.notNull(model);
        add(new AjaxEventListener(EventTeam.class));

        IModel<List<EventTeam>> listModel = getListModel(model);
        // render existing event teams
        ListView<EventTeam> teamList = new ListView<EventTeam>("teamList", listModel) {
            @Override
            protected void populateItem(final ListItem<EventTeam> item) {
                // item.add(new Label("name", item.getModelObject().getName()));
                AjaxEditableLabel<String> editableLabel = new AjaxEditableLabel<String>("name", new PropertyModel<String>(item.getModel(), "name")) {
                    {
                        getEditor().add(AttributeModifier.replace("maxlength", 15));
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
                item.add(new AjaxLinkWithConfirmation("removeTeamButton", new ResourceModel("manager.eventteam.remove.confirm")) {
                    @Override
                    public void onClick(final AjaxRequestTarget target) {
                        eventTeamManager.removeTeam(item.getModelObject());
                        AjaxEventSender.entityEvent(this, EventTeam.class);
                        AjaxEventSender.send(this, EventTeamInviteeListPanel.class);
                    }
                });
            }
        };
        add(teamList);

        add(new AjaxLink("addTeamButton") {
            @Override
            public void onClick(final AjaxRequestTarget target) {
                eventTeamManager.addTeam(model.getObject());
                AjaxEventSender.entityEvent(this, EventTeam.class);
            }
        });
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
