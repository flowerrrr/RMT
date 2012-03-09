package de.flower.rmt.ui.manager.component;

import de.flower.rmt.model.Opponent;
import de.flower.rmt.service.IOpponentManager;
import de.flower.rmt.ui.common.form.field.DropDownChoicePanel;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
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
        super(id);
        setChoices(getOpponentChoices());
        setChoiceRenderer(new IChoiceRenderer<Opponent>() {
            @Override
            public Object getDisplayValue(Opponent opponent) {
                return opponent.getName();
            }

            @Override
            public String getIdValue(Opponent opponent, int index) {
                return opponent.getId().toString();
            }
        });
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
