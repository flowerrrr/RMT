package de.flower.rmt.ui.markup.html.form.renderer;

import de.flower.rmt.model.db.type.RSVPStatus;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.ResourceModel;


public class RSVPStatusRenderer implements IChoiceRenderer<RSVPStatus> {

    @Override
    public Object getDisplayValue(final RSVPStatus status) {
        if (status == null) {
            return null;
        } else {
            return new ResourceModel(RSVPStatus.getResourceKey(status)).getObject();
        }
    }

    @Override
    public String getIdValue(final RSVPStatus object, final int index) {
        return "" + index;
    }
}
