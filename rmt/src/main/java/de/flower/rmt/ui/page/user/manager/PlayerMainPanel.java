package de.flower.rmt.ui.page.user.manager;

import de.flower.common.ui.ajax.markup.html.tab.AbstractAjaxTabbedPanel;
import de.flower.common.util.Check;
import de.flower.rmt.model.db.entity.User;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import java.util.List;

/**
 * @author flowerrrr
 */
public class PlayerMainPanel extends AbstractAjaxTabbedPanel<User> {

    public static final int TEAM_SETTINGS_PANEL_INDEX = 1;

    public PlayerMainPanel(final IModel<User> model) {
        super(Check.notNull(model));
    }

    @Override
    protected void addTabs(final List<ITab> tabs) {
        final IModel<User> model = Check.notNull(getModel());
        tabs.add(new AbstractTab(new ResourceModel("manager.player.edit.general")) {
            @Override
            public Panel getPanel(String panelId) {
                return new PlayerGeneralPanel(panelId, model);
            }
        });
        tabs.add(new AbstractTab(new ResourceModel("manager.player.edit.team")) {
            @Override
            public Panel getPanel(String panelId) {
                return new PlayerTeamsPanel(panelId, model);
            }
        });
    }
}
