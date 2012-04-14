package de.flower.rmt.ui.page.opponents.manager;

import de.flower.rmt.model.Opponent;
import de.flower.rmt.ui.model.OpponentModel;
import de.flower.rmt.ui.page.base.manager.ManagerBasePage;
import de.flower.rmt.ui.page.base.manager.NavigationPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class OpponentEditPage extends ManagerBasePage {

    public OpponentEditPage() {
        this(new OpponentModel());
    }

    public OpponentEditPage(IModel<Opponent> model) {
        setHeading("manager.opponent.edit.heading", null);
        addMainPanel(new OpponentEditPanel(model) {
            @Override
            protected void onClose(AjaxRequestTarget target) {
                setResponsePage(OpponentsPage.class);
            }
        });
        addSecondaryPanel();
    }

    @Override
    public String getActiveTopBarItem() {
        return NavigationPanel.OPPONENTS;
    }

}
