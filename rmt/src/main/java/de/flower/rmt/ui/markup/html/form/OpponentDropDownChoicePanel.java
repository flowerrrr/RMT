package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.model.db.entity.Opponent;
import de.flower.rmt.service.OpponentManager;
import de.flower.rmt.ui.markup.html.form.field.AbstractFormFieldPanel;
import de.flower.rmt.ui.markup.html.form.field.DropDownChoicePanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;


public class OpponentDropDownChoicePanel extends DropDownChoicePanel<Opponent> {

    @SpringBean
    private OpponentManager opponentManager;

    public OpponentDropDownChoicePanel(String id) {
        super(id, new OpponentDropDownChoice(AbstractFormFieldPanel.ID));
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
