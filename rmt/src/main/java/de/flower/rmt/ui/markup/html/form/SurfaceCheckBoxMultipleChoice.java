package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.model.db.type.Surface;
import de.flower.rmt.ui.markup.html.form.renderer.SurfaceRenderer;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;

import java.util.Arrays;


public class SurfaceCheckBoxMultipleChoice extends CheckBoxMultipleChoice<Surface> {

    public SurfaceCheckBoxMultipleChoice(String id) {
        super(id);
        setChoices(Arrays.asList(Surface.values()));
        setChoiceRenderer(new SurfaceRenderer());
    }
}
