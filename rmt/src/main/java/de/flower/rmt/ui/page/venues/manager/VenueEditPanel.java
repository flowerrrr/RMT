package de.flower.rmt.ui.page.venues.manager;

import de.flower.common.ui.ajax.event.AjaxEventSender;
import de.flower.common.ui.panel.BasePanel;
import de.flower.common.util.geo.LatLng;
import de.flower.rmt.model.db.entity.Venue;
import de.flower.rmt.security.ISecurityService;
import de.flower.rmt.service.IVenueManager;
import de.flower.rmt.service.geocoding.GeocodingResult;
import de.flower.rmt.ui.markup.html.form.CancelableEntityForm;
import de.flower.rmt.ui.markup.html.form.EntityForm;
import de.flower.rmt.ui.markup.html.form.field.TextAreaPanel;
import de.flower.rmt.ui.markup.html.form.field.TextFieldPanel;
import de.flower.rmt.ui.page.venues.manager.geocode.GeocodePanel;
import de.flower.rmt.ui.page.venues.manager.map.VenueMapFormComponent;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author flowerrrr
 */
public class VenueEditPanel extends BasePanel {

    @SpringBean
    private IVenueManager venueManager;

    @SpringBean
    private ISecurityService securityService;

    public VenueEditPanel(final IModel<Venue> model) {
        super(model);

        final EntityForm<Venue> form = new CancelableEntityForm<Venue>("form", model) {

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<Venue> form) {
                venueManager.save(form.getModelObject());
                AjaxEventSender.entityEvent(this, Venue.class);
                onClose(target);
            }
        };
        add(form);

        form.add(new TextFieldPanel("name"));
        final TextAreaPanel address;
        form.add(address = new AddressTextAreaPanel("address") {
            @Override
            protected boolean isInstantValidationEnabled() {
                // collides with clicking on the geocode button.
                return false;
            }
        });
        final VenueMapFormComponent latLng;
        form.add(latLng = new VenueMapFormComponent("latLng", new PropertyModel<LatLng>(model, "latLng"), securityService.getUser().getClub().getLatLng()));

        form.add(new GeocodePanel() {
            @Override
            protected String getAddress() {
                return address.getFormComponent().getValue();
            }

            @Override
            protected void onSelect(final AjaxRequestTarget target, final GeocodingResult result) {
                // update marker in venue map and in address field
                address.getFormComponent().setModelObject(result.getAddress());
                latLng.setModelObject(result.getLatLng());
                target.add(address);
                target.add(latLng);
            }

            /**
             * Search button is placed outside the geocode panel.
             */
            @Override
            protected MarkupContainer getGeocodeButtonParent() {
                return address;
            }
        }.setVisible(false).setOutputMarkupPlaceholderTag(true));

    }
}
