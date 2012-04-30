package de.flower.rmt.ui.page.venues.manager.map;

import de.flower.common.util.geo.LatLng;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

/**
 * @author flowerrrr
 */
public class VenueMapFormComponent extends FormComponentPanel<LatLng> {

    private LatLng latLng;

    public VenueMapFormComponent(String id, final IModel<LatLng> model, final LatLng defaultPosition) {
        super(id, model);
        setOutputMarkupId(true);

        final IModel<LatLng> mapModel = new AbstractReadOnlyModel<LatLng>() {
            @Override
            public LatLng getObject() {
                return model.getObject() != null ? model.getObject() : defaultPosition;
            }
        };
        latLng = mapModel.getObject();
        add(new VenueMapPanel(mapModel) {

            @Override
            public void onUpdateMarker(final LatLng latLng) {
                VenueMapFormComponent.this.latLng = latLng;
            }
        });
    }

    @Override
    protected void convertInput() {
        setConvertedInput(latLng);
    }

    @Override
    protected void onModelChanged() {
        // necessary when updating model value from outside (like selecting a geocoding result)
        latLng = getModelObject();
    }

    @Override
    public Markup getAssociatedMarkup() {
        String markup = "<wicket:panel><div wicket:id='venueMapPanel' class='venue-map'/></wicket:panel>";
        return Markup.of(markup);
    }
}
