package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.service.IVenueManager;
import de.flower.rmt.ui.markup.html.form.field.AbstractFormFieldPanel;
import de.flower.rmt.ui.markup.html.form.field.DropDownChoicePanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author flowerrrr
 */
public class VenueDropDownChoicePanel extends DropDownChoicePanel<Venue> {

    @SpringBean
    private IVenueManager venueManager;

    public VenueDropDownChoicePanel(String id) {
        super(id, new VenueDropDownChoice(AbstractFormFieldPanel.ID));
        setChoices(getVenueChoices());
    }

    private IModel<List<Venue>> getVenueChoices() {
        return new LoadableDetachableModel<List<Venue>>() {
            @Override
            protected List<Venue> load() {
                return venueManager.findAll();
            }
        };
    }
}
