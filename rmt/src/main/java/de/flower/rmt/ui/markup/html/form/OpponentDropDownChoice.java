package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.model.Opponent;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

/**
 * @author flowerrrr
 */
public class OpponentDropDownChoice extends DropDownChoice<Opponent> {

    public OpponentDropDownChoice(String id) {
        super(id);
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
        setNullValid(true);
    }

    @Override
    protected String getNullValidKey() {
        return "opponent.nullValid";
    }
}
