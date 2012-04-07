package de.flower.rmt.ui.manager.component;

import de.flower.rmt.model.Opponent;
import de.flower.rmt.service.IOpponentManager;
import de.flower.rmt.ui.common.form.field.DropDownChoicePanel;
import de.flower.rmt.ui.common.form.field.FormFieldPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class OpponentDropDownChoicePanel extends DropDownChoicePanel<Opponent> {

    @SpringBean
    private IOpponentManager opponentManager;

    public OpponentDropDownChoicePanel(String id) {
        super(id, new OpponentDropDownChoice(FormFieldPanel.ID));
        setChoices(getOpponentChoices());

    }

    private IModel<List<Opponent>> getOpponentChoices() {
        return new LoadableDetachableModel<List<Opponent>>() {
            @Override
            protected List<Opponent> load() {
                return opponentManager.findAll();
            }
        };
    }
}
