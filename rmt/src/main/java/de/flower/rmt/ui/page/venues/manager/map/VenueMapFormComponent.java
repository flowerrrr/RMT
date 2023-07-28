package de.flower.rmt.ui.page.venues.manager.map;

import de.flower.common.util.geo.LatLng;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;


public class VenueMapFormComponent extends FormComponentPanel<LatLng> {

    private IModel<LatLng> mapModel;

    public VenueMapFormComponent(String id, final IModel<LatLng> model, final LatLng defaultPosition) {
        super(id, model);
        setOutputMarkupId(true);

        mapModel = new IModel<LatLng>() {

            private LatLng latLng;

            @Override
            public LatLng getObject() {
                if (latLng == null) {
                    return model.getObject() != null ? model.getObject() : defaultPosition;
                } else {
                    return latLng;
                }
            }

            @Override
            public void setObject(final LatLng object) {
                latLng = object;
            }

            @Override
            public void detach() {
            }
        };
        add(new VenueMapPanel(mapModel));
    }

    @Override
    protected void convertInput() {
        setConvertedInput(mapModel.getObject());
    }

    @Override
    protected void onModelChanged() {
        // necessary when updating model value from outside (like selecting a geocoding result)
        mapModel.setObject(getModelObject());
    }

    @Override
    public Markup getAssociatedMarkup() {
        String markup = "<wicket:panel><div wicket:id='venueMapPanel' class='venue-map'/></wicket:panel>";
        return Markup.of(markup);
    }
}
