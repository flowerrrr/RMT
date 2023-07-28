package de.flower.rmt.ui.markup.html.form;

import de.flower.rmt.model.db.entity.Venue;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;


public class VenueDropDownChoice extends DropDownChoice<Venue> {

    public VenueDropDownChoice(String id) {
        super(id);
        setChoiceRenderer(new IChoiceRenderer<Venue>() {
            @Override
            public Object getDisplayValue(Venue venue) {
                return venue.getName();
            }

            @Override
            public String getIdValue(Venue venue, int index) {
                return venue.getId().toString();
            }
        });
        setNullValid(true);
    }

    @Override
    protected String getNullValidKey() {
        return "venue.nullValid";
    }
}
