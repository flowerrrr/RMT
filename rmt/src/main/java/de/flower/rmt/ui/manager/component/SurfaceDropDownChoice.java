package de.flower.rmt.ui.manager.component;

import de.flower.rmt.model.Surface;
import de.flower.rmt.ui.common.renderer.SurfaceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;

import java.util.Arrays;

/**
 * @author flowerrrr
 */
public class SurfaceDropDownChoice extends DropDownChoice<Surface> {

    public SurfaceDropDownChoice(String id) {
        super(id);
        setChoices(Arrays.asList(Surface.values()));
        setChoiceRenderer(new SurfaceRenderer());
        setNullValid(true);
    }
}
