package de.flower.rmt.ui.common.panel;

import de.flower.common.ui.tooltips.TwipsyBehavior;
import de.flower.rmt.model.RSVPStatus;
import de.flower.rmt.ui.common.renderer.RSVPStatusRenderer;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableChoiceLabel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import java.util.Arrays;

/**
 * @author flowerrrr
 */
public abstract class QuickResponseLabel extends AjaxEditableChoiceLabel<RSVPStatus> {

    private TwipsyBehavior tooltipBehavior;

    public QuickResponseLabel(final String id, final RSVPStatus status) {
        super(id, Model.of(status), Arrays.asList(RSVPStatus.values()), new RSVPStatusRenderer());
    }

    protected IModel<RSVPStatus> getModel() {
        return (IModel<RSVPStatus>) getDefaultModel();
    }

    @Override
    protected void onModelChanged() {
        submitStatus(getModel().getObject());
        // must remove twipsy behavior when user has selected an entry
        removeTooltipBehavior();
    }

    protected abstract void submitStatus(final RSVPStatus status);

    /**
     * Use our info icon to signal user that he has not responded yet.
     *
     * @return
     */
    @Override
    protected String defaultNullLabel() {
        return "<span class=\"info-icon\"/>";
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        if (getModel().getObject() == null) {
            addTooltipBehavior();
        }
    }

    private void addTooltipBehavior() {
        tooltipBehavior = new TwipsyBehavior(new ResourceModel("player.events.tooltip.no.response"));
        getLabel().add(tooltipBehavior);
    }

    private void removeTooltipBehavior() {
        if (tooltipBehavior != null) {
            getLabel().remove(tooltipBehavior);
            tooltipBehavior = null;
        }
    }
}
