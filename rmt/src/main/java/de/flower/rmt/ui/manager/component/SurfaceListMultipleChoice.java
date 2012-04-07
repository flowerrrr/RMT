package de.flower.rmt.ui.manager.component;

import de.flower.rmt.model.Surface;
import de.flower.rmt.ui.common.renderer.SurfaceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;

import java.util.Arrays;

/**
 * @author flowerrrr
 */
public class SurfaceListMultipleChoice extends ListMultipleChoice<Surface> {

    public SurfaceListMultipleChoice(String id) {
        super(id);
        setChoices(Arrays.asList(Surface.values()));
        setChoiceRenderer(new SurfaceRenderer());
    }
}
