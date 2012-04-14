package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.model.Uniform;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

/**
 * @author flowerrrr
 */
public class UniformDropDownChoice extends DropDownChoice<Uniform> {

    public UniformDropDownChoice(String id) {
        super(id);
        setChoiceRenderer(new IChoiceRenderer<Uniform>() {
            @Override
            public Object getDisplayValue(Uniform uniform) {
                return uniform.getName();
            }

            @Override
            public String getIdValue(Uniform uniform, int index) {
                return uniform.getId().toString();
            }
        });
        setNullValid(true);
    }

    @Override
    protected String getNullValidKey() {
        return "uniform.nullValid";
    }

}
